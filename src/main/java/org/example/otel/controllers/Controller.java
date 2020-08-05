package org.example.otel.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.otel.clients.example.ExampleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    @Autowired
    private ExampleClient exampleClient;

    @GetMapping("/controller")
    public String controller() {
        log.info("controller");
        return exampleClient.getShim();
    }

    @GetMapping("/controller-no-opentracing")
    public String controllerNoOpenTracing() {
        log.info("controller no opentracing");
        return exampleClient.getAuto();
    }
}
