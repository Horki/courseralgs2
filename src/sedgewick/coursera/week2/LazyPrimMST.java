package sedgewick.coursera.week2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week2.abstracts.MinimumSpanningTree;

public class LazyPrimMST extends MinimumSpanningTree {
    // MST vertices
    private final boolean[] marked;
    // MST edges
    private final Queue<Edge> mst;
    // Crossing (and ineligible) edges
    private final MinPQ<Edge> pq;

    private double weight;

    public LazyPrimMST(EdgeWeightedGraph G) {
        weight = .0;
        pq = new MinPQ<>();
        marked = new boolean[G.V()];
        mst = new Queue<>();
        // assumes G is connected
        visit(G, 0);
        while (!pq.isEmpty()) {
            // log E
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }
    }

    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    // Mark v and add to pq all edges from v to unmarked vertices.
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) {
                // log E
                pq.insert(e);
            }
        }
    }

    @Override
    public double weight() {
        return weight;
    }

    // tinyEWG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MinimumSpanningTree mst = new LazyPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }

}
