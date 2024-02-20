package question2;

import java.util.HashSet;
/*
    You are given an integer n representing the total number of individuals. Each individual is identified by a unique
    ID from 0 to n-1. The individuals have a unique secret that they can share with others.
    The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
    with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
    a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
    You need to determine the set of individuals who will eventually know the secret after all the possible secret-
    sharing intervals have occurred.
    Example:
    Input: n = 5, intervals = [(0, 2), (1, 3), (2, 4)], firstPerson = 0
    Output: [0, 1, 2, 3, 4]
 */
/*
 * Solution : We iterate through the array and collec unique individuals adn add them to an array.
 * The listof indviduals in the array are the listeners that have head the secrets in the given interval.
 */
public class B {
    static HashSet<Integer> countIndividuals(int[][] arr){
        //Hashset to ensure there are no duplicate individuals who have heard the secret.
        HashSet<Integer> listeners = new HashSet<>();
        int start = 0;//The user that shares the secret
        int end = 1;// The user that listens to the secret
        for(int i = 0; i < arr.length; i++){
                // adding both the sharer and the listener to the array.
                listeners.add(arr[i][start]);
                listeners.add(arr[i][end]);
        }
        return listeners;
    }
    public static void main(String[] args) {
        int[][] arr = {{0,2},{1,3},{2,4}};
        System.out.println(countIndividuals(arr));
    }
}
