package com.chitly.StreamingCacheWarmer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class StreamingCacheWarmer {

    private final URLMappingCacheRepository cacheRepository;
    private CountDownLatch countDownLatch = null;

    @Autowired
    public StreamingCacheWarmer(URLMappingCacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    /**
     * This one is used for testing.
     *
     * @param countDownLatch latch is counted down for each message consumed
     */
    StreamingCacheWarmer(URLMappingCacheRepository cacheRepository, CountDownLatch countDownLatch) {
        this(cacheRepository);
        this.countDownLatch = countDownLatch;
    }

    @KafkaListener(id = "cache-warmer", topics = "new-url-mapping")
    public void listen(URLMapping mapping) {
        cacheRepository.save(mapping);

        // for testing
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
