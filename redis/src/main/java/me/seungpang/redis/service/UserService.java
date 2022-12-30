package me.seungpang.redis.service;

import java.util.concurrent.TimeUnit;
import me.seungpang.redis.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {
        String userName = null;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cacheName = ops.get("nameKey:" + userId);
        if (cacheName != null) {
            userName = cacheName;
        } else {
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
        }
        //String userName = externalApiService.getUserName(userId);
        int userAge = externalApiService.getUserAge(userId);
        return new UserProfile(userName, userAge);
    }
}
