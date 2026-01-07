package com.example.tempconverter;

import static spark.Spark.*;

public class TemperatureConverter {
    public static void main(String[] args) {
        port(4567);

        // Home page: form
        get("/", (req, res) -> {
            res.type("text/html");
            return "<!doctype html>" +
                    "<html><head><meta charset=\"utf-8\"><title>Temperature Converter</title></head><body>" +
                    "<h1>Temperature Converter</h1>" +
                    "<form method='post' action='/convert'>" +
                    "  <input type='number' step='any' name='value' placeholder='Value' required />" +
                    "  <select name='unit'>" +
                    "    <option value='C'>Celsius</option>" +
                    "    <option value='F'>Fahrenheit</option>" +
                    "  </select>" +
                    "  <button type='submit'>Convert</button>" +
                    "</form>" +
                    "<p>Or use the API endpoint: <code>/api/convert?value=100&unit=C</code></p>" +
                    "</body></html>";
        });

        // Form submission: HTML result
        post("/convert", (req, res) -> {
            res.type("text/html");
            String valueStr = req.queryParams("value");
            String unit = req.queryParams("unit");
            double value;
            try {
                value = Double.parseDouble(valueStr);
            } catch (Exception e) {
                res.status(400);
                return "<p>Invalid value. <a href='/'>Back</a></p>";
            }

            double out;
            String outUnit;
            if ("C".equalsIgnoreCase(unit)) {
                out = value * 9.0 / 5.0 + 32.0;
                outUnit = "Fahrenheit";
            } else {
                out = (value - 32.0) * 5.0 / 9.0;
                outUnit = "Celsius";
            }

            return "<!doctype html><html><body>" +
                    "<h1>Result</h1>" +
                    "<p>Input: " + value + " " + ("C".equalsIgnoreCase(unit) ? "Celsius" : "Fahrenheit") + "</p>" +
                    "<p>Converted: " + out + " " + outUnit + "</p>" +
                    "<p><a href='/'>Convert another</a></p>" +
                    "</body></html>";
        });

        // Simple JSON API
        get("/api/convert", (req, res) -> {
            res.type("application/json");
            String valueStr = req.queryParams("value");
            String unit = req.queryParams("unit");
            if (valueStr == null || unit == null) {
                res.status(400);
                return "{\"error\":\"Missing 'value' or 'unit' query parameter\"}";
            }
            double value;
            try {
                value = Double.parseDouble(valueStr);
            } catch (Exception e) {
                res.status(400);
                return "{\"error\":\"Invalid numeric value\"}";
            }

            double out;
            String outUnit;
            if ("C".equalsIgnoreCase(unit)) {
                out = value * 9.0 / 5.0 + 32.0;
                outUnit = "F";
            } else if ("F".equalsIgnoreCase(unit)) {
                out = (value - 32.0) * 5.0 / 9.0;
                outUnit = "C";
            } else {
                res.status(400);
                return "{\"error\":\"unit must be 'C' or 'F'\"}";
            }

            return String.format(
                    "{\"input\":{\"value\":%s,\"unit\":\"%s\"},\"output\":{\"value\":%s,\"unit\":\"%s\"}}",
                    value, unit.toUpperCase(), out, outUnit
            );
        });
    }
}
