package com.onstagram.follow.controller;

import com.onstagram.follow.domain.FollowDto;
import com.onstagram.follow.entity.FollowEntity;
import com.onstagram.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("follow")
public class FollowController {

    private final FollowService followService;


    @PostMapping("/create/{followerId}/to/{followingId}")
    public HttpStatus createFollow(@PathVariable Long followerId, @PathVariable Long followingId) {

        try {
            followService.createFollow(followerId, followingId);
            return HttpStatus.OK; // 성공(200)
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST; // 실패(400)
        }
    }


    @DeleteMapping("/delete/{followId}")
    public HttpStatus deleteFollow(@PathVariable Long followId) {

        try {
            followService.deleteFollow(followId);
            return HttpStatus.OK; // 성공(200)
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST; // 실패(400)
        }
    }
    @GetMapping("/followers/{userId}")
    public List<FollowEntity> getFollowers(@PathVariable Long userId) {
        return followService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public List<FollowEntity> getFollowing(@PathVariable Long userId) {
        return followService.getFollowing(userId);
    }
}
