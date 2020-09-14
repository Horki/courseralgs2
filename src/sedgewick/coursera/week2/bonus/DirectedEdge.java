package sedgewick.coursera.week2.bonus;

import edu.princeton.cs.algs4.StdOut;

public class DirectedEdge implements Comparable<DirectedEdge> {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
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

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    // compare this edge to that edge
    public int compareTo(DirectedEdge that) {
        return Double.compare(weight, that.weight);
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(1, 2, 3.14);
        StdOut.println(e);
        StdOut.println(e.from() == 1);
        StdOut.println(e.weight() == 3.14);
        StdOut.println(e.to() == 2);
    }
}
