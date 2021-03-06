package sedgewick.coursera.week2;

import edu.princeton.cs.algs4.StdOut;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("v is Negative");
        }
        if (w < 0) {
            throw new IllegalArgumentException("w is Negative");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    // either endpoint
    public int either() {
        return v;
    }

    // the endpoint that's not v
    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Missing other");
    }

    // compare this edge to that edge
    public int compareTo(Edge that) {
        return Double.compare(weight, that.weight);
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(1, 2, 3.14);
        StdOut.println(e);
        StdOut.println(e.either() == 1);
        StdOut.println(e.weight() == 3.14);
        StdOut.println(e.other(1) == 2);
        StdOut.println(e.other(2) == 1);
    }
}
