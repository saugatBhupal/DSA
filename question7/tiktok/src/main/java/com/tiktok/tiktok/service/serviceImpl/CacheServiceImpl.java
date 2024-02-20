package com.tiktok.tiktok.service.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.RecentTags;
import com.tiktok.tiktok.service.CacheService;
import com.tiktok.tiktok.service.RecentTagsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CacheServiceImpl implements CacheService{
    private final RecentTagsService recentTagsService;

    /*
     * This function updates a cache memory that is in the database. It works on the principle of LFU.
     * Everytime the user likes a new post, its tag is sent to the cacheService to update the cache memory.
     * Thi sis done to make sure that topics relevant to a user do not have to be calculated every single time.
     */
    @Override
    public RecentTags update(RecentTags recentTags) {
        List<RecentTags> allTags = recentTagsService.get(recentTags.getUser().getId());//get all the tags of the user

        if (allTags.size() < 10) {// if the size is less than 10 then we can continue to push to cache without popping
            recentTagsService.add(recentTags);
        } else {
        HashMap<String, RecentTags> map = new HashMap<>();//creating a hashmap of recentTag with tagname
        /* If the tag already exists in the cache then theres no need to push an object from cache rather we can increase the frequence of the tag */
        for (RecentTags tag : allTags) {
            String tagName = tag.getRecentActivity().getTagName();
            if(map.containsKey(tagName)){
                RecentTags existing = map.get(tagName);
                existing.getRecentActivity().setInteractions(existing.getRecentActivity().getInteractions() + 1);
                map.replace(tagName, existing);
            }
            else{
                map.put(tagName, tag);// if the tag is new to the cache we will push to the cache
            }
        }
        //now the cache will overflow as there are more than 10 elements. So we check the least frequent tag and remove it from the cache
        String minCountTag = null;
        int minCount = Integer.MAX_VALUE;
        for (Map.Entry<String, RecentTags> entry : map.entrySet()) {
            if (entry.getValue().getRecentActivity().getInteractions() < minCount) {
                minCount = entry.getValue().getRecentActivity().getInteractions();
                minCountTag = entry.getKey();
            }
        }
        recentTagsService.remove(map.get(minCountTag));
        recentTagsService.add(recentTags);
        }

    return recentTags;
}

    
}
