package com.tiktok.tiktok.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Follow> followers;

    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private List<Follow> following;

    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Like> likes;


}
