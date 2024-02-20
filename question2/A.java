package question2;

/*
    You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The
    production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain
    number of dresses or is empty.
    For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass
    one dress from each selected sewing machine to its adjacent sewing machine simultaneously.
    Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to
    determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number
    of dresses, return -1.
    Input: [1,0,5]
    Output: 2
 */
/*
 * Solution : We try to calculate the equalizing number of clothes and number of machines by dividing the total number of clothes.
 * Once we hve the equlizng number of clothes we find the diference between the equalizing number and current number of clothes.
 * The sum of the differences is the total moves required to equalize for each machine.
 * However, since one change affects 2 machines, the required moves is half of the total difference.
 */

public class A {
    public static int minMovesToEqualizeDresses(int[] sewingMachines) {
        int totalDresses = 0;
        int numMachines = sewingMachines.length;

        // Calculate the total number of dresses among all sewing machines.
        for (int dresses : sewingMachines) {
            totalDresses += dresses;
        }

        // If the total number of dresses is not divisible evenly among machines, equalization is not possible.
        if (totalDresses % numMachines != 0) {
            return -1;
        }

        // Calculate the target number of dresses each machine should have for equalization.
        int targetDresses = totalDresses / numMachines;
        int balance = 0;

        // Calculate the balance by finding the absolute difference between each machine's dresses and the target.
        for (int dresses : sewingMachines) {
            balance += Math.abs(dresses - targetDresses);
            System.out.println(balance);
        }
        // Return the half of the balance, as each move impacts two machines (one loses, and the other gains).
        return balance/2;
    }

    public static void main(String[] args) {
        int[] sewingMachines = {1, 0, 5};
        int result = minMovesToEqualizeDresses(sewingMachines);
        System.out.println(result);
    }
}
