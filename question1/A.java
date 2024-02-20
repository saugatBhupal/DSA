package question1;

/*
    You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with
    one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating
    each venue with a certain theme varies.
    The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example,
    costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of
    decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering
    to the adjacency constraint.
    For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One
    possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of
    1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of
    3 + 2 = 5.
    Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while
    satisfying the adjacency constraint.
    Please note that the costs are positive integers.
    Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7
    Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 +
    6 + 1 = 7.
 */

/*
 * Solution : The first dimension of the array is iterated through. This is the first event. The minimum cost of booking venuess
 * is calculated. We periodically check if the previous venue is the same as the minimum cost for the current venue. We check the 
 * previous venue, and add the minimum cost to total cost.
 * 
 */
public class A {
    /* Function to find theme with minimum cost for a venue*/
    static int findMinimum(int cost[], int previousTheme) {
        int minCost = Integer.MAX_VALUE;//Setting a large value for minimum cost
        int minIndex = 0;// Initializng the minimum cost theme index to 0;

        for(int i = 0; i < cost.length; i ++){// iterating
            /* if the current minimum cost theme is greater than the current ith theme cost*/
            if(cost[i] < minCost){
                /* if the current minimum value index is not equal to the previous minimum cost index */
                if(i != previousTheme){
                    minIndex = i; // setting min index and min cost to the current minimum
                    minCost = cost[i];
                }
            }
        }
        return minIndex;
    }
    /* Function to calculate minimum cost */
    static int calculateCost(int venues[][]){
        int previousTheme = Integer.MIN_VALUE;
        int cost = 0;

        /* minimum cost index of each venue is calculated and the cost at the index is added to the total cost. 
        The previous theme index is also updated */
        for(int i = 0; i < venues.length; i++){
            int currentTheme = findMinimum(venues[i], previousTheme);
            cost += venues[i][currentTheme];
            previousTheme = currentTheme;
        }

        return cost;
    }
    public static void main(String[] args) {
        int[][] arr =  {{1, 5, 3}, {2, 9, 4}};
        System.out.println(calculateCost(arr));
    }
}