package com.tiktok.tiktok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiktok.tiktok.entity.User;
import java.util.Optional;


@Repository
public interface UserRespository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
