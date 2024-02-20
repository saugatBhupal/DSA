package question1;

/*
    You are the captain of a spaceship and you have been assigned a mission to explore a distant galaxy. Your
    spaceship is equipped with a set of engines, where each engine represented by a block. Each engine requires a
    specific amount of time to be built and can only be built by one engineer.
    Your task is to determine the minimum time needed to build all the engines using the available engineers. The
    engineers can either work on building an engine or split into two engineers, with each engineer sharing the
    workload equally. Both decisions incur a time cost.
    The time cost of splitting one engineer into two engineers is given as an integer split. Note that if two engineers
    split at the same time, they split in parallel so the cost would be split.
    Your goal is to calculate the minimum time needed to build all the engines, considering the time cost of splitting
    engineers.
    Input: engines= [1,2,3]
    Split cost (k)=1
    Output: 4
 */
/*
 * Solution : We use a priority queue to keep track of the times. The least time block elemetns are accessed first.
 * This is done so that we can find the maximum time between any two blocks in sequence. It takes a longer time 
 * to free up worker with the larger time. Thus maximum of two blocks is taken as reference and added witht the cost of splitting.
 * The new value is appended to the priority queue and the process is repeated.
 */
import java.util.PriorityQueue;

public class B {
    
    // Calculates the minimum time needed to build all engines using available engineers.
    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int x : engines) {
            q.offer(x);
        }
        while (q.size() > 1) {
            // Combine two engines with the most time, add splitCost, and insert back into the priority queue.
            q.offer(splitCost + Integer.max(q.poll(), q.poll()));
        }
        // Finally the remaining element in the priority queue is the minimum cost.
        return q.poll();
    }
    public static void main(String[] args) {
        int[] engines = {1,2,3};
        int splitCost = 1;

        int result = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time needed to build all engines: " + result);
    }
}
