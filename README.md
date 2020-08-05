# rest-api-example for otel

my setup

```shell script
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar
# wget your-exporter
wget https://github.com/lightstep/opentelemetry-exporter-java/releases/download/0.6.0/lightstep-opentelemetry-auto-exporter-0.6.0.jar
mvn clean package && \
java \
  -javaagent:`pwd`/opentelemetry-javaagent-all.jar \
  -Dotel.exporter.jar=`pwd`/lightstep-opentelemetry-auto-exporter-0.6.0.jar \
  -Dotel.propagators=ottracer \
  -Dotel.exporter.lightstep.config.file=`pwd`/lightstep.config \
  -jar target/app.jar
```

invoke a 401 with
```shell script
curl -v 'http://localhost:8080/..%3b'
```

view the trace. note that the http.status_code tag is 500 while the response code is 401.
![](examples/401-500.png)
