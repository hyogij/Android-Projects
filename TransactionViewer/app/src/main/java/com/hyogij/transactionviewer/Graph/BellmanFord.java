package com.hyogij.transactionviewer.Graph;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class to implement Bellman-Ford's single source shortest path algorithm.
 */
public class BellmanFord {
    private static final String CLASS_NAME = BellmanFord.class
            .getCanonicalName();

    // The main function that finds shortest distances from src to all other
    // vertices using Bellman-Ford algorithm. The function also detects negative
    // weight cycle
    public static double BellmanFordAlgorithm(WeightedDigraph graph, int src,
                                              int dest) {
        if (src == dest) {
            // Return default value
            return 1.0;
        }

        int V = graph.getVertexNum();
        int E = graph.getEdgeNum();
        double distances[] = new double[V];
        Edge predecessor[] = new Edge[V];

        // Initialize distances from src to all other vertices as INFINITE
        for (int i = 0; i < V; ++i) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[src] = 0;

        double weight = Integer.MIN_VALUE;

        // Relax all edges |V| - 1 times. A simple shortest path from src to
        // any other vertex can have at-most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                Edge edge = graph.getEdges(j);
                int u = edge.getSrc();
                int v = edge.getDest();
                double logScaleweight = edge.getLogScaleWeight();
                if (distances[u] != Integer.MAX_VALUE
                        && distances[u] + logScaleweight < distances[v]) {
                    distances[v] = distances[u] + logScaleweight;
                    predecessor[v] = graph.getEdges(j);
                }
            }

            double currentWeight = printPath(distances, predecessor, src,
                    dest, V);
            // Set the maximum weight value from src to dest
            if (currentWeight > weight) {
                weight = currentWeight;
            }
        }

        // Check for negative-weight cycles. The above step guarantees
        // shortest distances if graph doesn't contain negative weight cycle. If
        // we get a shorter path, then there is a cycle.
        boolean hasNegativeWeightCycle = false;
        for (int j = 0; j < E; ++j) {
            Edge edge = graph.getEdges(j);
            int u = edge.getSrc();
            int v = edge.getDest();
            double logScaleweight = edge.getLogScaleWeight();
            if (distances[u] != Integer.MAX_VALUE
                    && distances[u] + logScaleweight < distances[v]) {
                hasNegativeWeightCycle = true;
            }
        }

        if (hasNegativeWeightCycle) {
            Log.d(CLASS_NAME, "Graph contains negative weight cycle");
        }

        return weight;
    }

    // A utility function used to print the distance matrix
    public static void printDistanceMatrix(double dist[], Edge predecessor[],
                                           int V) {
        Log.d(CLASS_NAME, "Vertex\tDistance\tSource");
        for (int i = 0; i < V; ++i) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            if (predecessor[i] != null) {
                Log.d(CLASS_NAME, i + "\t" + formatter.format(dist[i]) + "\t"
                        + predecessor[i].toString());
            } else {
                Log.d(CLASS_NAME, i + "\t" + formatter.format(dist[i]) + "\t-");
            }
        }
    }

    // A utility function used to print the solution
    public static double printPath(double dist[], Edge predecessor[], int src,
                                   int dest, int V) {
        Edge edge = predecessor[dest];

        // There is no path from src to desc
        if (edge == null) {
            return 0.0;
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        double value = 1.0;
        while (edge != null && edge.getDest() != src && path.size() < V) {
            path.add(edge.getDest());
            value *= edge.getWeight();
            edge = predecessor[edge.getSrc()];
        }

        if (edge != null) {
            path.add(edge.getDest());
        } else {
            path.add(src);
        }

        // Reverses path
        Collections.reverse(path);
        return value;
    }
}
