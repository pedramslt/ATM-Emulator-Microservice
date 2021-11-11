package com.atm.bankservice.redis;

import com.atm.bankservice.service.redis.RedisService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisConTests {

    @Autowired
    private RedisService<RedisTestDto> redisService;

    @BeforeEach
    public void setUp() {
        RedisTestDto redisTestDto = new RedisTestDto("key", "value");
        redisService.save(redisTestDto.getKey(), redisTestDto);
    }

    @Test
    void redisGet() {
        String key = "key";
        RedisTestDto redisTestDto = redisService.findByKey(key, RedisTestDto.class);
        Assert.notNull(redisTestDto);
        assertEquals(key, redisTestDto.getKey());
    }

}
