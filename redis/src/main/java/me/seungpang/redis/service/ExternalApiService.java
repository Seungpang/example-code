package me.seungpang.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public String getUserName(String userId) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        System.out.println("getUserName call");
        if (userId.equals("A")) {
            return "seungpang";
        }
        if (userId.equals("B")) {
            return "seungrae";
        }
        return "";
    }

    @Cacheable(cacheNames = "userAgeCache", key = "#userId")
    public int getUserAge(String userId) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        System.out.println("getUserAge call");
        if (userId.equals("A")) {
            return 20;
        }
        if (userId.equals("B")) {
            return 25;
        }
        return 0;
    }
}
