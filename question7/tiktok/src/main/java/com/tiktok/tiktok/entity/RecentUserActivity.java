package com.tiktok.tiktok.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "userActivity")
public class RecentUserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int interactions;

    private String tagName;

    @OneToOne
    @JoinColumn(name = "tag_id")
    private RecentTags tag;

    public RecentUserActivity(String tagName){
        this.tagName = tagName;
    }
}
