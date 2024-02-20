package com.tiktok.tiktok.service;

import java.util.List;

import com.tiktok.tiktok.entity.Like;

public interface LikeService {  
    List<Like> fetchAll();
    Like fetchLikeById(Long likeID);
    void delete(Like like) throws Exception;
    Like update(Like like);
} 