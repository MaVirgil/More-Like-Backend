package com.mavi.themaurus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThemaurusBackendApplication implements CommandLineRunner {

    @Value("${BUCKET_CAPACITY}")
    private int BUCKET_CAPACTIY;
    @Value("${BUCKET_REFILL_AMOUNT}")
    private int BUCKET_REFILL_AMOUNT;

    @Value("${BUCKET_REFILL_TIME_MINUTES}")
    private int BUCKET_REFILL_TIME_MINUTES;

    @Value("${USE_WEB_SEARCH}")
    private boolean USE_WEB_SEARCH;

    @Value("${REASONING_EFFORT}")
    private int REASONING_EFFORT;

    private static Logger LOG = LoggerFactory
            .getLogger(SpringApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting application...");
        SpringApplication.run(ThemaurusBackendApplication.class, args);
        LOG.info("Startup finished");
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.info(
            """
            Application running with config:
            
            RATE LIMITING:
            BUCKET_CAPACITY: {}
            BUCKET_REFILL_AMOUNT: {}
            BUCKET_REFILL_TIME_MINUTES: {}
            
            LLM CONFIG:
            USE_WEB_SEARCH: {}
            REASONING_EFFORT: {}
            """,
            BUCKET_CAPACTIY,
            BUCKET_REFILL_AMOUNT,
            BUCKET_REFILL_TIME_MINUTES,
            USE_WEB_SEARCH,
            REASONING_EFFORT
        );
    }
}
