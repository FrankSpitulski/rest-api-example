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
  -Dotel.integration.okhttp.enabled=false \
  -jar target/app.jar
```

start up netcat to view the http request headers
```shell script
nc -l 8081
```

invoke with
```shell script
curl localhost:8080/controller
```

I would expect to see 
```text
ot-tracer-traceid: a51c6a1def41567762ee2e986a491ae6
ot-tracer-spanid: 6926e628f64df328
ot-tracer-sampled: true
```
in netcat but there are no ot-tracer headers.

Run the program again without the okhttp disabled.
```shell script
java \
  -javaagent:`pwd`/opentelemetry-javaagent-all.jar \
  -Dotel.exporter.jar=`pwd`/lightstep-opentelemetry-auto-exporter-0.6.0.jar \
  -Dotel.propagators=ottracer \
  -Dotel.exporter.lightstep.config.file=`pwd`/lightstep.config \
  -jar target/app.jar
```
invoke a non opentracing client with
```shell script
curl localhost:8080/controller-no-opentracing
```
and observe the ot-tracer headers in netcat.

From what I've seen, the shim is entering the inject method but the context it is trying to inject is empty.
