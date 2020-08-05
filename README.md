# rest-api-example for otel

my setup

```shell script
wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar
mvn clean package && \
java \
  -javaagent:`pwd`/opentelemetry-javaagent-all.jar \
  -Dotel.propagators=ottracer \
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
  -Dotel.propagators=ottracer \
  -jar target/app.jar
```
invoke a non opentracing client with
```shell script
curl localhost:8080/controller-no-opentracing
```
and observe the ot-tracer headers in netcat.

From what I've seen, the shim is entering the inject method but the context it is trying to inject is empty.
There will be some errors about the default otel exporter not responding, but the exporter is not necessary to see the lack of propagation headers.
