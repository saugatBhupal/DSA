package com.tiktok.tiktok.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.Like;
import com.tiktok.tiktok.entity.RecentTags;
import com.tiktok.tiktok.entity.RecentUserActivity;
import com.tiktok.tiktok.repository.LikeRespository;
import com.tiktok.tiktok.service.CacheService;
import com.tiktok.tiktok.service.LikeService;
import com.tiktok.tiktok.service.PostService;
import com.tiktok.tiktok.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRespository repository;
    private final PostService postService;
    private final CacheService cacheService;
    private final UserService userService;

    @Override
    public List<Like> fetchAll() {
        return(repository.findAll());
    }

    @Override
    public Like fetchLikeById(Long likeID) {
        return(repository.findById(likeID).get());
    }

    @Override
    public void delete(Like like) throws Exception {
        try{
            repository.delete(like);
        }
        catch(Exception e){
            System.out.println(e);
            throw new Exception("cannot delete");
        }
        
    }

    @Override
    public Like update(Like like) {
        cacheService.update(new RecentTags(Long.valueOf("0"),userService.findById((like.getUser().getId())), new RecentUserActivity(postService.fetchPost(like.getPost().getId()).getTag())));
        return(repository.save(like));
    }
}