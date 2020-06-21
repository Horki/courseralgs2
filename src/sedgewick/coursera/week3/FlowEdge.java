package sedgewick.coursera.week3;

import edu.princeton.cs.algs4.StdOut;

public class FlowEdge {
    // Edge source
    private final int v;
    // Edge target
    private final int w;
    // Capacity
    private final double capacity;
    // Flow
    private double flow;

    // Create a flow edge v->w
    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        flow = 0;
    }

    // vertex this edge points from
    public int from() {
        return v;
    }

    // vertex this edge points to
    public int to() {
        return w;
    }

    // capacity of this edge
    public double capacity() {
        return capacity;
    }

    // flow in this edge
    public double flow() {
        return flow;
    }

    // other endpint
    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new RuntimeException("Illegal endpoint");
    }

    // residual capacity toward v
    public double residualCapacityTo(int vertex) {
        // Backward Edge
        if (vertex == v) return flow;
        // Forward Edge
        if (vertex == w) return capacity - flow;
        throw new IllegalArgumentException("Inconsistent edge" + vertex);
    }

    // add delta flow toward v
    public void addResidualFlowTo(int vertex, double delta) {
        // Backward Edge
        if (vertex == v) flow -= delta;
        // Forward Edge
        if (vertex == w) flow += delta;
        // TODO: fix later
//        throw new IllegalArgumentException("Invalid endpoint: " + vertex);
    }

    public String toString() {
        return String.format("%d->%d %.2f %.2f", v, w, capacity, flow);
    }

    public static void main(String[] args) {
        FlowEdge flowEdge = new FlowEdge(1, 2, 3.2);
        StdOut.println(flowEdge);
        StdOut.println(flowEdge.from() == 1);
        StdOut.println(flowEdge.to() == 2);
        StdOut.println(flowEdge.capacity() == 3.2);
        StdOut.println(flowEdge.flow() == 0);
        StdOut.println(flowEdge.other(1) == 2);
        StdOut.println(flowEdge.other(2) == 1);
        StdOut.println(flowEdge.residualCapacityTo(1) == .0);
        StdOut.println(flowEdge.residualCapacityTo(2) == 3.2);
    }
}
