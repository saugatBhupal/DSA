package com.tiktok.tiktok.service;

import java.util.List;

import com.tiktok.tiktok.entity.RecentTags;

public interface RecentTagsService {
    RecentTags add(RecentTags recentTags);
    void remove(RecentTags recentTags);
    List<RecentTags> get(Long userId);
}
