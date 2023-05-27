package com.example.springsercuitydemo.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 操作字符串（key,value）
    public void setString(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 操作hash表
    public void setHash(String key, String filedKey, String value) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, filedKey, value);
    }

    public String getHash(String key, String filedKey) {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        return (String) hashOperations.get(key, filedKey);
    }

    // 操作list列表
    public long setList(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public List<String> getList(String key) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }
}
