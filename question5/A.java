package question5;

import java.util.Arrays;
import java.util.Random;

/*
 * Implement ant colony algorithm solving travelling a salesman problem.
 */
class A {
    private int numAnts;
    private int numCities;
    private double[][] pheromones;
    private double[][] distances;
    private double[][] visibility;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private Random random;

    // Constructor to initialize ant colony parameters and data structures
    public A(int numAnts, double[][] distances, double alpha, double beta, double evaporationRate) {
        this.numAnts = numAnts;
        this.numCities = distances.length;
        this.pheromones = new double[numCities][numCities];
        this.distances = distances;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.random = new Random();

        initializePheromones();
        calculateVisibility();
    }

    // Initialize pheromone levels on edges between cities
    private void initializePheromones() {
        for (int i = 0; i < numCities; i++) {
            Arrays.fill(pheromones[i], 1.0);
        }
    }

    // Calculate visibility values based on distances between cities
    private void calculateVisibility() {
        visibility = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j) {
                    visibility[i][j] = 1.0 / distances[i][j];
                }
            }
        }
    }

    // Main method to solve the TSP using ant colony optimization
    public int[] solve(int maxIterations) {
        int[] bestTour = null;
        double bestTourLength = Double.POSITIVE_INFINITY;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[][] antTours = generateAntTours();
            updatePheromones(antTours);

            double[] tourLengths = calculateTourLengths(antTours);

            // Find the best tour among all ants
            for (int antIndex = 0; antIndex < numAnts; antIndex++) {
                if (tourLengths[antIndex] < bestTourLength) {
                    bestTourLength = tourLengths[antIndex];
                    bestTour = antTours[antIndex].clone();
                }
            }

            evaporatePheromones();
        }

        return bestTour;
    }

    // Generate tours for all ants
    private int[][] generateAntTours() {
        int[][] antTours = new int[numAnts][numCities];

        for (int antIndex = 0; antIndex < numAnts; antIndex++) {
            int startCity = random.nextInt(numCities);
            boolean[] visited = new boolean[numCities];
            visited[startCity] = true;
            antTours[antIndex][0] = startCity;

            for (int step = 1; step < numCities; step++) {
                int currentCity = antTours[antIndex][step - 1];
                int nextCity = selectNextCity(currentCity, visited);
                antTours[antIndex][step] = nextCity;
                visited[nextCity] = true;
            }
        }

        return antTours;
    }

    // Select the next city for an ant based on probabilities
    private int selectNextCity(int currentCity, boolean[] visited) {
        double[] probabilities = calculateProbabilities(currentCity, visited);
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (randomValue <= cumulativeProbability) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Calculate the probabilities for an ant to move to each city
    private double[] calculateProbabilities(int currentCity, boolean[] visited) {
        double[] probabilities = new double[numCities];
        double total = 0.0;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromones[currentCity][i], alpha) * Math.pow(visibility[currentCity][i], beta);
                total += probabilities[i];
            }
        }

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[i] /= total;
            }
        }

        return probabilities;
    }

    // Update pheromone levels based on ant tours
    private void updatePheromones(int[][] antTours) {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }

        for (int antIndex = 0; antIndex < numAnts; antIndex++) {
            double pheromoneDelta = 1.0 / calculateTourLength(antTours[antIndex]);

            for (int i = 0; i < numCities - 1; i++) {
                int city1 = antTours[antIndex][i];
                int city2 = antTours[antIndex][i + 1];
                pheromones[city1][city2] += pheromoneDelta;
                pheromones[city2][city1] += pheromoneDelta;
            }
        }
    }

    // Calculate tour lengths for all ants
    private double[] calculateTourLengths(int[][] antTours) {
        double[] tourLengths = new double[numAnts];

        for (int antIndex = 0; antIndex < numAnts; antIndex++) {
            tourLengths[antIndex] = calculateTourLength(antTours[antIndex]);
        }

        return tourLengths;
    }

    // Calculate the length of a tour
    private double calculateTourLength(int[] tour) {
        double length = 0.0;

        for (int i = 0; i < numCities - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }
        length += distances[tour[numCities - 1]][tour[0]];

        return length;
    }

    // Evaporate pheromones to simulate decay over time
    private void evaporatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    // Main method to test the Ant Colony Optimization algorithm
    public static void main(String[] args) {
        double[][] distances = {
            {0, 2, 9, 10},
            {2, 0, 6, 4},
            {9, 6, 0, 8},
            {10, 4, 8, 0}
        };

        int numAnts = 5;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        int maxIterations = 100;

        A antColony = new A(numAnts, distances, alpha, beta, evaporationRate);
        int[] bestTour = antColony.solve(maxIterations);

        System.out.println("Best Tour: " + Arrays.toString(bestTour));
        System.out.println("Best Tour Length: " + antColony.calculateTourLength(bestTour));
    }
}
