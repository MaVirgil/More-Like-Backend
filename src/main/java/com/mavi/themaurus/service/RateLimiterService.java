package com.mavi.themaurus.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    @Value("${BUCKET_CAPACITY}")
    private int BUCKET_CAPACITY;

    @Value("${BUCKET_REFILL_AMOUNT}")
    private int BUCKET_REFILL_AMOUNT;

    @Value("${BUCKET_REFILL_TIME_MINUTES}")
    private int BUCKET_REFILL_TIME_MINUTES;

    public Bucket resolveBucket(String ip) {
        return bucketCache.computeIfAbsent(ip, this::createBucket);
    }

    private Bucket createBucket(String ip) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(BUCKET_CAPACITY)
                .refillGreedy(BUCKET_REFILL_AMOUNT, Duration.ofMinutes(BUCKET_REFILL_TIME_MINUTES))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
