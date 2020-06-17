package sedgewick.coursera.week2.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Alg4AcyclicSP {
    // tinyEWDAG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        edu.princeton.cs.algs4.EdgeWeightedDigraph G = new edu.princeton.cs.algs4.EdgeWeightedDigraph(in);
        int source = 5;
        edu.princeton.cs.algs4.AcyclicSP sp = new edu.princeton.cs.algs4.AcyclicSP(G, source);
        for (int v = 0; v < G.V(); ++v) {
            if (source != v && sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f): ", source, v, sp.distTo(v));
                for (edu.princeton.cs.algs4.DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + " ");
                }
                StdOut.println();
            }
        }
    }

}
