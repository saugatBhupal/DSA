package com.tiktok.tiktok.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "follows")
@Data
public class Follow {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ownerID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;  

    @ManyToOne
    @JoinColumn(name = "followerID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User follower;  

}
