package com.tiktok.tiktok.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tiktok.tiktok.entity.Follow;
import com.tiktok.tiktok.entity.User;
import com.tiktok.tiktok.repository.FollowRepository;
import com.tiktok.tiktok.service.FollowService;
import com.tiktok.tiktok.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService{
    private final FollowRepository respository;
    private final UserService userService;

    /*This function returns a hashmap of followers and following users of a given userID.
     * It works by filtering through a simple query. First all records are retrieved from the follow table where the user exists.
     * Then we filter each record from the recieved records of follows table to check whether the user is in the owner, or follower
     * in the table. Based on this relation, a List of followers and followings are generated and returned.
      */
    @Override
    public HashMap<String, List<Long>> fetchFollowerAndFollowingIDs(Long userID) {
        List<Follow> follows = respository.findByOwnerOrFollower(userService.findById(userID), userService.findById(userID));
        List<Long> followers = follows.stream()
                                    .filter(follow -> follow.getOwner().getId().equals(userID))
                                    .map(Follow :: getOwner)
                                    .map(User :: getId)
                                    .collect(Collectors.toList());
        List<Long> following = follows.stream()
                                    .filter(follow -> follow.getFollower().getId().equals(userID))
                                    .map(Follow :: getFollower)
                                    .map(User :: getId)
                                    .collect(Collectors.toList());      
        HashMap<String,List<Long>> followMap = new HashMap<>();
        followMap.put("followers", followers);
        System.out.println(followers);
        followMap.put("following", following);                   
        return(followMap);   
    }

    /*This function checks and returns if a relation between the owner and follower exists. If the relation exists, the relation is returned.
     * This is done mainly to ensure duplication doesnt occur as a single relation can be maintained only once.
     */
    private Optional<Follow> getExistingRecord(Long owner, Long follower ){
        return(respository.findExistingRecord(owner, follower));
    }

    /*
     * This function saves the follow record in the database. If a record exists, that record is deleted. Else the new record is saved.
     */
    @Override
    public Follow save(Follow follow) {
        Optional<Follow> existingRecord = getExistingRecord(follow.getOwner().getId(), follow.getFollower().getId());
        if(existingRecord.isPresent()){
            respository.delete(existingRecord.get());
            return(new Follow());
        }
        follow.setFollower(userService.findById(follow.getFollower().getId()));
        follow.setOwner(userService.findById(follow.getOwner().getId()));
        return(respository.save(follow));
    }

    /*
     * This function returns a list of recommended users based on mutual friends.
     * It works on a graph where nodes are users and edges and follow records. If two users follow each other,
     * a bidirectional relation is maintained. Each bidirectional relation is given a score of 1.
     * The goal of the algorithm is to travel upto level 2 from level 0 to find user with the most connections with the current user, that is not
     * a friend of the user. To do this a modified version of breadth-first search is used.
     */
    @Override
    public List<User> getRecommendation(Long userID) {
        HashMap<Long, Integer> score = new HashMap<>();// hashmap to store score
        Queue<User> queue = new LinkedList<>();//Queue to add nodes for bfs.
        User root = userService.findById(userID);// retreiveing the current user. 
        queue.add(root);// adding current user to the queue
        while(!queue.isEmpty()){// the loop must continue as long as there are nodes in the graph.
            User x = queue.remove();
            System.out.print(x.getId() + "->");
            for(User i : respository.findOwnersByFollower(x.getId())){// for each connected node to the current parent
                System.out.print(i.getId() + " ");
                if(i == root) continue;//No action to be taken if the node is root because no user can follow their own account.
                if(i != root && getExistingRecord(i.getId(), root.getId()).isEmpty()){// if the current node is not root and there is no pre existing connection with root, we will add the score;
                    if(score.containsKey(i.getId())){//if the node already has a score in the hashmap we will increase the score
                        int newScore = score.get(i.getId()) + 1;
                        score.put(i.getId(), newScore);
                    }
                    else{
                        score.put(i.getId(), 1);// else we will initialize the node with a score of 0
                    }
                }
                else{
                    if(x == root && getExistingRecord(root.getId(), i.getId()).isPresent()){// only adding the node to queue if the parent is root to make sure infinite traversal doesnt occur.
                        queue.add(i);
                    }
                }
                System.out.println();
            }
        }
        System.out.println(score);
        ArrayList<Map.Entry<Long,Integer>> entryList = new ArrayList<>(score.entrySet());// converting the list into and entry list to sort in descending order.
        Collections.sort(entryList,Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()));
        return(entryList.stream()
                            .map(Map.Entry<Long,Integer> :: getKey)
                            .map(userService :: findById)
                            .toList()//changing from entry list to list and returning.
        );
    }
    
}
