package org.example.hazelcast.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hazelcast.core.HazelcastInstance;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * with near cache enabled the test will fail. it hangs on {@link HazelcastInstance#getMap(String)}.
 */
@SpringBootTest(properties = {"nearcache.enabled=true", "logCacheInvocations=true"})
class ExampleServiceTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    void passthrough() {
        IntStream.range(0, 50).boxed()
                .parallel()
                .map(i -> exampleService.passthrough("OK"))
                .forEach(actual -> assertEquals("OK", actual));
    }
}
