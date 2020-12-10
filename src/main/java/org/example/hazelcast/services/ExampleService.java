package org.example.hazelcast.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExampleService {

    @Cacheable(cacheNames = "service_cache")
    public String passthrough(String input) {
        log.info("service");
        return input;
    }

}
