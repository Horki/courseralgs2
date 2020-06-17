package sedgewick.coursera.week2;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week2.abstracts.MinimumSpanningTree;

import java.util.Arrays;

public class EagerPrimMST extends MinimumSpanningTree {
    // shortest edge from tree vertex
    private Edge[] edgeTo;
    // distTo[w] = edgeTo[w].weight()
    private final double[] distTo;
    // true if v on tree
    private final boolean[] marked;
    // Crossing (and ineligible) edges
    private final IndexMinPQ<Double> pq;

    private double weight;
    private final Bag<Edge> mst;

    public EagerPrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<>(G.V());
        distTo[0] = .0;
        pq.insert(0, .0);
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());
        }
        weight = Arrays.stream(distTo).sum();
        mst = new Bag<>();
        for (int v = 1; v < edgeTo.length; ++v) {
            mst.add(edgeTo[v]);
        }
    }

    // Mark v and add to pq all edges from v to unmarked vertices.
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) {
                continue;
            }
            // v-w is ineligible.
            // Edge e is new best connection from tree to w.
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }

    // tinyEWG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MinimumSpanningTree mst = new EagerPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }
}
