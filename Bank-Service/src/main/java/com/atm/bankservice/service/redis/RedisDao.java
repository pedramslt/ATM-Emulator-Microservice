package com.atm.bankservice.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    public void save(String key, Object obj) {
        com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(key, json);
    }

    public void save(String key, Object obj, long timeout, TimeUnit timeUnit) {
        com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(key, json, timeout, timeUnit);
        //redisTemplate.opsForValue().append(key , json);
    }

    public List<T> findListByKey(String key, Class<T> tClass) {
        Object valueOfRedisKey = null;
        try {
            valueOfRedisKey = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> ss = new ArrayList<>();
        if (valueOfRedisKey != null) {
            try {
                ss = objectMapper.readValue((String) valueOfRedisKey, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        return ss;
    }

    public T findByKey(String key, Class<T> tClass) {
        Object valueOfRedisKey = null;
        try {
            valueOfRedisKey = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
        }
        ObjectMapper objectMapper = new ObjectMapper();
        T ss = null;
        if (valueOfRedisKey != null) {
            try {
                ss = objectMapper.readValue((String) valueOfRedisKey, tClass);
                return ss;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        return ss;
    }

    public String findByKey(String key) {
        String valueOfRedisKey = null;
        try {
            valueOfRedisKey = redisTemplate.opsForValue().get(key).toString();
        } catch (Exception e) {
            return null;
        }


        return valueOfRedisKey;
    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}
