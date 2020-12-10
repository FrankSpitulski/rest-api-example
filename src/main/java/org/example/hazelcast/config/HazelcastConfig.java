package org.example.hazelcast.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class HazelcastConfig {

    @Bean
    Config hazelCastConfig(@Value("${nearcache.enabled:true}") boolean nearCacheEnabled) {
        Config config = new Config();
        if (nearCacheEnabled) {
            config.getMapConfig("default").setNearCacheConfig(new NearCacheConfig());
        }
        return config;
    }


    @Bean
    HazelcastCacheManager cacheManager(
            HazelcastInstance existingHazelcastInstance,
            @Value("${logCacheInvocations:true}") boolean logCacheInvocations) {
        if (logCacheInvocations) {
            return new CustomHazelcastCacheManager(existingHazelcastInstance);
        }
        return new HazelcastCacheManager(existingHazelcastInstance);
    }

    @Slf4j
    public static class CustomHazelcastCacheManager extends HazelcastCacheManager {

        public CustomHazelcastCacheManager(HazelcastInstance existingHazelcastInstance) {
            super(existingHazelcastInstance);
        }

        @Override
        public Cache getCache(String name) {
            log.info("get cache from {} for {}", Thread.currentThread().getName(), name);
            Cache cache = super.getCache(name);
            log.info("got cache from {} for {}", Thread.currentThread().getName(), name);
            return cache;
        }
    }
}
