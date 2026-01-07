# Java Temperature Converter (Spark Java)

This is a minimal Java web application that runs on localhost and lets you convert temperatures between Celsius and Fahrenheit.

How to build and run:

```bash
# build
mvn package

# run the generated fat jar
java -jar target/temp-converter-0.1.0.jar
```

Open your browser at: http://localhost:4567

Endpoints:
- GET /          -> HTML form
- POST /convert  -> form submission result
- GET /api/convert?value=<number>&unit=C|F  -> JSON response

Example:
- http://localhost:4567/api/convert?value=100&unit=C

Notes:
- The app uses Spark Java and listens on port 4567 by default.
- Requires Java 11+ and Maven.
