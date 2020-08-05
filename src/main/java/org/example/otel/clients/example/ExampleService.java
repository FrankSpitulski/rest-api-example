package org.example.otel.clients.example;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ExampleService {

    @GET("/")
    Call<String> get();

}
