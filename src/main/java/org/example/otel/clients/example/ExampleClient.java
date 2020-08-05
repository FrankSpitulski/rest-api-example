package org.example.otel.clients.example;

import io.opentracing.contrib.okhttp3.TracingCallFactory;
import io.opentracing.util.GlobalTracer;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Service
public class ExampleClient {

    private final ExampleService opentracingService;
    private final ExampleService autoInstrumentedService;

    public ExampleClient() {
        this.opentracingService = new Retrofit.Builder()
                .callFactory(new TracingCallFactory(
                        new OkHttpClient(),
                        GlobalTracer.get()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://localhost:8081")
                .build()
                .create(ExampleService.class);
        this.autoInstrumentedService = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://localhost:8081")
                .build()
                .create(ExampleService.class);
    }

    @SneakyThrows
    public String getShim() {
        return opentracingService.get().execute().body();
    }

    @SneakyThrows
    public String getAuto() {
        return autoInstrumentedService.get().execute().body();
    }
}
