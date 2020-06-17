package sedgewick.coursera.week2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.Queue;
import sedgewick.coursera.week2.abstracts.MinimumSpanningTree;

public class KruskalMST extends MinimumSpanningTree {
    private final Queue<Edge> mst;
    private double weight;

    // Worst case: E log E
    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        weight = .0;
        // Build priority queue
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            // log E
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            // log*V
            if (uf.find(v) != uf.find(w)) {
                // log*V
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
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
        MinimumSpanningTree mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }

}
