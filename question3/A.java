package question3;

import java.util.ArrayList;
import java.util.Collections;
/*
    You are developing a student score tracking system that keeps track of scores from different assignments. The
    ScoreTracker class will be used to calculate the median score from the stream of assignment scores. The class
    should have the following methods:
    • ScoreTracker() initializes a new ScoreTracker object.
    • void addScore(double score) adds a new assignment score to the data stream.
    • double getMedianScore() returns the median of all the assignment scores in the data stream. If the number
    of scores is even, the median should be the average of the two middle scores.
 */
class ScoreTracker{
    /*
     * Solution : Allow users to keep adding scores. The scores are saved in an arraylist and sorted. We calculate median 
     * if the number of subjects is even. Else we calculate the mean.
     */
    ArrayList<Double> scores;

    public ScoreTracker(){
        scores = new ArrayList<>();
    }

    void addScore(Double score){
        //Adding the score and sorting it.
        scores.add(score);
        Collections.sort(scores);
    }

    double getMedianScore(){
        //Checking if the number of scores added is even
        if(scores.size() % 2 == 0){
            int median = scores.size()/2;// If even we return the median.
            return((double)(scores.get(median) + scores.get(median - 1))/2);
        }
        else{
            return scores.get(scores.size()/2);// if odd, we return the mean.
        }
    }
}

public class A {
    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println(median1);
        scoreTracker.addScore(81.2); 
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); 
        System.out.println(median2);
    }
}
