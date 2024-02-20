package question3;

import java.util.*;

// Class representing an edge in the graph
class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

// Priority Queue implementation using a minimum heap
class PriorityQueueMinHeap {
    private List<Edge> heap;

    public PriorityQueueMinHeap() {
        heap = new ArrayList<>();
    }

    // Insert an edge into the priority queue
    public void insert(Edge edge) {
        heap.add(edge);
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (heap.get(currentIndex).weight < heap.get(parentIndex).weight) {
                Collections.swap(heap, currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Extract the edge with the minimum weight from the priority queue
    public Edge extractMin() {
        if (heap.isEmpty()) {
            return null;
        }

        Edge minEdge = heap.get(0);
        Edge lastEdge = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastEdge);
            heapify(0);
        }

        return minEdge;
    }

    // Heapify the priority queue to maintain the heap property
    private void heapify(int currentIndex) {
        int leftChildIndex = 2 * currentIndex + 1;
        int rightChildIndex = 2 * currentIndex + 2;
        int smallestIndex = currentIndex;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).weight < heap.get(smallestIndex).weight) {
            smallestIndex = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).weight < heap.get(smallestIndex).weight) {
            smallestIndex = rightChildIndex;
        }

        if (smallestIndex != currentIndex) {
            Collections.swap(heap, currentIndex, smallestIndex);
            heapify(smallestIndex);
        }
    }

    // Check if the priority queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}

// Implementation of Kruskal's Algorithm
public class KruskalAlgorithm {

    // Find the parent of a vertex in a disjoint-set
    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]);
        }
        return parent[vertex];
    }

    // Union operation to merge two disjoint sets
    private void union(int[] parent, int x, int y) {
        int xParent = find(parent, x);
        int yParent = find(parent, y);
        parent[xParent] = yParent;
    }

    // Kruskal's Algorithm to find the Minimum Spanning Tree
    public List<Edge> kruskalMST(int vertices, List<Edge> edges) {
        List<Edge> result = new ArrayList<>();
        PriorityQueueMinHeap priorityQueue = new PriorityQueueMinHeap();

        // Sort edges based on their weights
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Initialize disjoint-set for tracking connected components
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }

        // Process edges in sorted order
        for (Edge edge : edges) {
            int sourceParent = find(parent, edge.source);
            int destinationParent = find(parent, edge.destination);

            // Check if including the edge forms a cycle
            if (sourceParent != destinationParent) {
                result.add(edge);
                union(parent, sourceParent, destinationParent);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        KruskalAlgorithm kruskal = new KruskalAlgorithm();

        // Example graph with edges and weights
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 2));
        edges.add(new Edge(0, 2, 3));
        edges.add(new Edge(1, 2, 1));
        edges.add(new Edge(1, 3, 4));
        edges.add(new Edge(2, 3, 5));

        // Find and print the Minimum Spanning Tree
        List<Edge> mst = kruskal.kruskalMST(4, edges);

        System.out.println("Minimum Spanning Tree (Kruskal's Algorithm):");
        for (Edge edge : mst) {
            System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
        }
    }
}
