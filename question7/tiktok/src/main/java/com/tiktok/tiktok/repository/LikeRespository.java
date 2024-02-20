package com.tiktok.tiktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiktok.tiktok.entity.Like;

@Repository
public interface LikeRespository extends JpaRepository<Like,Long>{
    
}
