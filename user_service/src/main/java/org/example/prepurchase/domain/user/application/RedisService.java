package org.example.prepurchase.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 키-벨류 설정
    public void setValues(String token, String username){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(username, token, Duration.ofMinutes(60));
    }

    // 키값으로 벨류 가져오기
    public String getValues(String username){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(username);
    }

    // 키-벨류 삭제
    public void delValues(String username) {
        redisTemplate.delete(username);
    }
}
