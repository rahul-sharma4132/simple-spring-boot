package com.chitly.StreamingCacheWarmer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class StreamingCacheWarmerTest {
    @Autowired
    KafkaTemplate<String, String> producer;

    @MockBean(name = "urlMappingCacheRepository")
    URLMappingCacheRepository cacheRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void itPassesTheValueBack() throws JsonProcessingException, InterruptedException {
        URLMapping mapping = new URLMapping("some-key", "some-url");
        when(cacheRepository.save(any())).thenReturn(null);

        // set up consumer
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamingCacheWarmer cacheWarmer = new StreamingCacheWarmer(cacheRepository, countDownLatch);

        // put in a message
        String serialized = objectMapper.writeValueAsString(mapping);
        producer.send(new ProducerRecord<>("new-url-mapping", mapping.getKey(), serialized));

        // wait for listener to grab it
        boolean countedDown = countDownLatch.await(5, TimeUnit.SECONDS);

        // validate it got consumed by checking the latch and the cacheRepo
        assert countedDown;
        verify(cacheRepository, times(1)).save(eq(mapping));
    }
}
