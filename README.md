# rest-api-example for otel

my setup

```shell script
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar
# wget your-exporter, I use a custom lightstep one
mvn clean package
java \
  -javaagent:`pwd`/opentelemetry-javaagent-all.jar \
  -Dota.exporter.jar=your-exporter \
  -jar target/app.jar
```

invoke a 401 with
```shell script
curl localhost:8080/controller
```
invoke a 200 with
```shell script
curl -u user:password localhost:8080/controller
```
at the moment of writing the 401 will have a root span with an incorrect operation name while the 200 has the operation name `/controller`.
