package com.tuoshecx.server.cms.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 内容检查数据操作
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class SecCheckOperator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecCheckOperator.class);
    private static final String SEC_CHECK_KEY = "com:tuoshecx:sec:check";

    private final BoundListOperations<String, String> operations;
    private final ObjectMapper objectMapper;

    SecCheckOperator(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.operations = redisTemplate.boundListOps(SEC_CHECK_KEY);
        this.objectMapper = objectMapper;
    }

    public void push(SecCheck t){
        toJson(t).ifPresent(operations::leftPush);
    }

    public Optional<SecCheck> pop(){
        String t = operations.rightPop(30, TimeUnit.SECONDS);
        return t == null? Optional.empty(): parse(t);
    }

    private Optional<String> toJson(SecCheck check){
        try{
            return Optional.of(objectMapper.writeValueAsString(check));
        }catch (Exception e){
            LOGGER.error("To json fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<SecCheck> parse(String v){
        try{
            return Optional.of(objectMapper.readValue(v, SecCheck.class));
        }catch (Exception e){
            LOGGER.error("Parse secCheck fail, error is {}", e.getMessage());
            return Optional.empty();
        }
    }
}
