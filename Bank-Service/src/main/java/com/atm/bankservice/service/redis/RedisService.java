package com.atm.bankservice.service.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService<T> {

    T findByKey(String key, Class<T> tClass);

    List<T> findListByKey(String key, Class<T> tClass);

    String findByKey(String key);

    void save(String key, Object obj);

    void save(String key, Object obj, long timeout, TimeUnit timeUnit);

    void delete(String key);

}
