package me.seungpang.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/setCrew")
    public String setCrew(@RequestParam String name) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("crew", name);

        return "saved";
    }

    @GetMapping("/getCrew")
    public String getCrew() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String crewName = ops.get("crew");

        return crewName;
    }
}
