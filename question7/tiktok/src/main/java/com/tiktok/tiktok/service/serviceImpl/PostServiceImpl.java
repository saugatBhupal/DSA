package com.tiktok.tiktok.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.Post;
import com.tiktok.tiktok.entity.RecentTags;
import com.tiktok.tiktok.repository.PostRepository;
import com.tiktok.tiktok.service.PostService;
import com.tiktok.tiktok.service.RecentTagsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository repository;

    @Override
    public Post save(Post post) {
       return(repository.save(post));
    }

    @Override
    public List<Post> getRecommendation(Long userID) {
        return repository.findPostsByRecentTags(userID);

    }

    @Override
    public Post fetchPost(Long id) {
        return(repository.findById(id).get());
    }
}
