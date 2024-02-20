package com.tiktok.tiktok.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.RecentTags;
import com.tiktok.tiktok.entity.User;
import com.tiktok.tiktok.repository.RecentTagsRepository;
import com.tiktok.tiktok.service.CacheService;
import com.tiktok.tiktok.service.RecentTagsService;
import com.tiktok.tiktok.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecentTagsServiceImpl implements RecentTagsService{
    private final RecentTagsRepository repository;
    private final UserService userService;
    
    @Override
    public RecentTags add(RecentTags recentTags) {
        return repository.save(recentTags);
    }

    @Override
    public List<RecentTags> get(Long userId) {
        return repository.findByUser(userService.findById(userId));
    }

    @Override
    public void remove(RecentTags recentTags){
        repository.delete(recentTags);
    }
    
}
