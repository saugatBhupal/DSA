package com.tiktok.tiktok.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tiktok.tiktok.entity.RecentTags;
import com.tiktok.tiktok.entity.User;

public interface RecentTagsRepository extends JpaRepository<RecentTags,Long>{

    List<RecentTags> findByUser(User user);
    
}
