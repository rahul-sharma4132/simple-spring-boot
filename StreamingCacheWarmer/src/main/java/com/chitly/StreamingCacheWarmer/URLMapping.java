package com.chitly.StreamingCacheWarmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Jacksonized @SuperBuilder
@RedisHash(value = "URLMapping", timeToLive = 7 * 24 * 60 * 60 * 1000)
public class URLMapping implements Serializable {
    @Id
    private String key;

    private String URL;
}
