package com.tiktok.tiktok.service;

import java.util.HashMap;
import java.util.List;

import com.tiktok.tiktok.entity.Follow;
import com.tiktok.tiktok.entity.User;

public interface FollowService {
    HashMap<String, List<Long>> fetchFollowerAndFollowingIDs(Long userID);
    Follow save(Follow followRequestDto);
    List<User> getRecommendation(Long userID);
}