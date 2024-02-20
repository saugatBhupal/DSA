package com.tiktok.tiktok.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiktok.tiktok.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    
    @Query("SELECT DISTINCT p FROM Post p " +
           "JOIN p.tags t " +
           "JOIN t.recentTags rt " +
           "WHERE rt.user.id = :userId")
    List<Post> findPostsByRecentTags(@Param("userId") Long userId);
}
