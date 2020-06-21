package sedgewick.coursera.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class FordFulkerson {
    // true if s->v path in residual network
    private boolean[] marked;
    // last edge on s->v path
    private FlowEdge[] edgeTo;
    // value of flow
    private double value;

    public FordFulkerson(FlowNetwork G, int s, int t) {
        value = .0;
        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }
            value += bottle;
        }
    }

    // Finding a shortest augmenting path (cf. breadth-first search); BFS
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty() && !marked[t]) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    // save last edge on path w
                    edgeTo[w] = e;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
        // Is "t" reachable from "s" in residual network?
        return marked[t];
    }

    public double value() {
        return value;
    }

    // Is v reachable from s in residual network?
    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        FlowNetwork G = new FlowNetwork(new In(args[0]));
        int s = 0;
        int t = G.V() - 1;

        FordFulkerson maxFlow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < t; ++v) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0) {
                    StdOut.println(" " + e);
                }
            }
        }
        StdOut.println("Max flow value = " + maxFlow.value());
    }
}
