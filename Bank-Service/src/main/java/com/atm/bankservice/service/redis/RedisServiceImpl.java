package com.atm.bankservice.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl<T> implements RedisService<T> {

    @Autowired
    private RedisDao<T> redisRepository;

    @Override
    public T findByKey(String key, Class<T> tClass) {
        return redisRepository.findByKey(key, tClass);

    }

    @Override
    public List<T> findListByKey(String key, Class<T> tClass) {
        return redisRepository.findListByKey(key, tClass);
    }

    @Override
    public String findByKey(String key) {
        return redisRepository.findByKey(key);

    }

    @Override
    public void save(String key, Object obj) {
        redisRepository.save(key, obj);
    }

    @Override
    public void save(String key, Object obj, long timeout, TimeUnit timeUnit) {
        redisRepository.save(key, obj, timeout, timeUnit);
    }

    @Override
    public void delete(String key) {
        redisRepository.delete(key);
    }


}