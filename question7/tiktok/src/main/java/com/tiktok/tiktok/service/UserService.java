package com.tiktok.tiktok.service;

import com.tiktok.tiktok.entity.User;

public interface UserService {
    
    User authenticate(User user);
    User save(User user);
    User findById(Long id);
}
