package com.tiktok.tiktok.service;

import java.util.List;

import com.tiktok.tiktok.entity.Post;

public interface PostService {
    Post fetchPost(Long id);
    Post save(Post post);
    List<Post> getRecommendation(Long userID);
} 
