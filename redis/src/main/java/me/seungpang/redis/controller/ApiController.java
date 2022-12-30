package me.seungpang.redis.controller;

import java.util.List;
import me.seungpang.redis.dto.UserProfile;
import me.seungpang.redis.service.RankingService;
import me.seungpang.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RankingService rankingService;

    @GetMapping("/users/{userId}/profile")
    public UserProfile getUserProfile(@PathVariable(value = "userId") String userId) {
        return userService.getUserProfile(userId);
    }

    @PostMapping("/users/{userId}/rank")
    public Boolean setScore(@RequestParam String userId,
                            @RequestParam int score
    ) {
        return rankingService.setUserScore(userId, score);
    }

    @GetMapping("/users/{userId}/rank")
    public Long getUserRank(@PathVariable String userId) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/users/topRanks")
    public List<String> getTopRanks() {
        return rankingService.getTopRank(3);
    }
}
