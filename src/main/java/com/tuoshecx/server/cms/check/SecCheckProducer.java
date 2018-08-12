package com.tuoshecx.server.cms.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产文字内容检查
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class SecCheckProducer {
    private final SecCheckOperator operator;

    @Autowired
    public SecCheckProducer(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper){
        this.operator = new SecCheckOperator(redisTemplate, objectMapper);
    }

    public void product(String id, String type){
        SecCheck t = new SecCheck();
        t.setId(id);
        t.setType(type);

        this.operator.push(t);
    }
}
