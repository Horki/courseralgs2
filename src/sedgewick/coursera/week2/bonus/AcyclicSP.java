package sedgewick.coursera.week2.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import sedgewick.coursera.week2.bonus.abstracts.SingleSourceShortestPath;

public class AcyclicSP extends SingleSourceShortestPath {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph G, int source) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;
        Digraph digraph = G.toDigraph(); // dirty fix
        Topological top = new Topological(digraph);
        for (int v : top.order()) {
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    @Override
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // tinyEWDAG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        for (int source = 0; source < G.V(); ++source) {
            StdOut.println("Acyclic Single source shortest path for source: " + source);
            SingleSourceShortestPath sp = new AcyclicSP(G, source);
            for (int v = 0; v < G.V(); ++v) {
                if (source != v && sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%.2f): ", source, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + " ");
                    }
                    StdOut.println();
                }
            }
        }
    }

}
