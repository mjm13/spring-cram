package pathPlanning;

import java.util.Arrays;

public class DijkstraAlgorithm {
    private static final int NO_PARENT = -1;

    public static void dijkstra(int[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the shortest distance from startVertex to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will be true if vertex i is included / in shortest path tree
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as INFINITE and added[] as false
        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        Arrays.fill(added, false);

        // Distance of source vertex from itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest path tree
        int[] parents = new int[nVertices];
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all vertices
        for (int i = 1; i < nVertices; i++) {
            // Pick the minimum distance vertex from the set of vertices not yet processed
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as processed
            added[nearestVertex] = true;

            // Update dist value of the adjacent vertices of the picked vertex
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents);
    }

    // Function to print the shortest path from source to vertex
    // using the parents array
    private static void printSolution(int startVertex, int[] distances, int[] parents) {
        System.out.println("Vertex\t Distance\tPath");

        for (int vertexIndex = 0; vertexIndex < distances.length; vertexIndex++) {
            if (vertexIndex != startVertex) {
                System.out.print("\n" + startVertex + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
        }
    }

    // Function to print the path from source to the currentVertex
    // using the parents array
    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
            {0, 9, 0, 0, 0, 14, 15, 0},
            {9, 0, 24, 0, 0, 0, 0, 10},
            {0, 24, 0, 6, 2, 18, 0, 0},
            {0, 0, 6, 0, 11, 0, 0, 0},
            {0, 0, 2, 11, 0, 30, 0, 0},
            {14, 0, 18, 0, 30, 0, 5, 9},
            {15, 0, 0, 0, 0, 5, 0, 16},
            {0, 10, 0, 0, 0, 9, 16, 0}
        };
        dijkstra(adjacencyMatrix, 0);
    }
}
