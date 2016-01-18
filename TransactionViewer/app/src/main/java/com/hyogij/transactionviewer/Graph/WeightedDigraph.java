package com.hyogij.transactionviewer.Graph;

/**
 * A class to represent a connected, directed and weighted graph
 */
public class WeightedDigraph {
    private int vertexNum = 0;
    private int edgeNum = 0;
    private Edge edges[] = null;
    private int count = 0;

    // Creates a graph with V vertices and E edges
    public WeightedDigraph(int vertexNum, int edgeNum) {
        this.vertexNum = vertexNum;
        this.edgeNum = edgeNum;
        edges = new Edge[edgeNum];
    }

    public void addEdge(int src, int dest, double weight) {
        if (count < edgeNum) {
            edges[count++] = new Edge(src, dest, weight);
        }
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public Edge getEdges(int idx) {
        return edges[idx];
    }
}
