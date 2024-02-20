package com.tiktok.tiktok.service;

import com.tiktok.tiktok.entity.RecentTags;
import java.util.List;

public interface CacheService {
    RecentTags update(RecentTags recentTags);
}
