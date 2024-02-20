package com.tiktok.tiktok.service.serviceImpl;

import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.User;
import com.tiktok.tiktok.repository.UserRespository;
import com.tiktok.tiktok.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository respository;

    @Override
    public User authenticate(User user) {
        User u = respository.findByUsername(user.getUsername()).get();
            if(user.getPassword().equals(u.getPassword())){
                return u;
            }
        return null;
    }

    @Override
    public User save(User user) {
        return(respository.save(user));
    }

    @Override
    public User findById(Long id) {
        return respository.findById(id).get();
    }
    
}
