package com.tiktok.tiktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tiktok.tiktok.entity.Follow;
import com.tiktok.tiktok.entity.User;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long>{ 
    
    @Query(value = "select * from follow where follower_id = ?1 or owner_id = ?1", nativeQuery = true)
    List<Follow> findFollow(Long userID);

    @Query(value = "select * from follow where owner_id = ? and follower_id = ?", nativeQuery = true)
    Optional<Follow> findExistingRecord(Long ownerID, Long followerID);

    List<Follow> findByOwnerOrFollower(User owner, User Follower);
    
    @Query("SELECT f.owner FROM Follow f WHERE f.follower.id = :followerId")
    List<User> findOwnersByFollower(@Param("followerId") Long followerId);
}
