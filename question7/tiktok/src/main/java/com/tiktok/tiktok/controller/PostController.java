package com.tiktok.tiktok.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tiktok.tiktok.entity.Post;
import com.tiktok.tiktok.service.PostService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
public class PostController {   
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        System.out.println(post);
        return ResponseEntity.ok(postService.save(post));
    }
    
    @GetMapping("/post/u/{id}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long userID) {
        return ResponseEntity.ok(postService.getRecommendation(userID));
    }
    
    
    

}
