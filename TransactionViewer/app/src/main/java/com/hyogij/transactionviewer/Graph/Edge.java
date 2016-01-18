package com.hyogij.transactionviewer.Graph;

/**
 * A class to represent a weighted edge in graph
 */
class Edge {
	private int src = 0;
	private int dest = 0;
	private double weight = 0.0;

	public Edge(int src, int dest, double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}

	public int getSrc() {
		return src;
	}

	public int getDest() {
		return dest;
	}

	public double getWeight() {
		return weight;
	}

	// Reduce to shortest path problem by taking logs
	public double getLogScaleWeight() {
		return -Math.log(weight);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(src);
		sb.append("-");
		sb.append(dest);
		sb.append(":");
		sb.append(weight);
		return sb.toString();
	}
};
