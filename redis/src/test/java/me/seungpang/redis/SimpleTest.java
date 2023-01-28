package me.seungpang.redis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SimpleTest {

    @Autowired
    private RedisTemplate redisTemplate;

    String dummyValue = "seungpang";

    @Test
    void setValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);
            ops.set(key, dummyValue);
        }
    }

    @Test
    void getValues() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);
            String value = ops.get(key);

            assertThat(value).isEqualTo(dummyValue);
        }
    }
}
