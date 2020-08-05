package org.example.otel.config;

import io.opentelemetry.opentracingshim.TraceShim;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTracingShim {

    @Bean
    public Tracer shim() {
        final Tracer tracerShim = TraceShim.createTracerShim();
        GlobalTracer.registerIfAbsent(tracerShim);
        return tracerShim;
    }
}
