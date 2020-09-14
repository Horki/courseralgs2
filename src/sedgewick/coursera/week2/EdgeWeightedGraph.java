package sedgewick.coursera.week2;


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

// Almost "Same" as in Graph
public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private final Bag<Edge>[] adj;

    // create an empty graph with V vertices
    public EdgeWeightedGraph(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new Bag<>();
        }
    }

    // create a graph from input stream
    public EdgeWeightedGraph(In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in a Digraph must be non negative");
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("Number of edges must be nonnegative");
            }

            adj = (Bag<Edge>[]) new Bag[V];
            for (int v = 0; v < V; ++v) {
                adj[v] = new Bag<>();
            }

            for (int i = 0; i < E; ++i) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                addEdge(new Edge(v, w, weight));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }

    // add weighted edge e to this graph
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        // TODO: make search O(1)|O(log n) later
        boolean exists = false;
        for (Edge item : adj(v)) {
            if (item.either() == w) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            adj[v].add(e);
            adj[w].add(e);
            ++E;
        }
    }

    // edges incident to v
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    // all edges in this graph
    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<>();
        for (int v = 0; v < V; ++v) {
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    b.add(e);
                }
            }
        }
        return b;
    }

    // number of vertices
    public int V() {
        return V;
    }

    // number of edges
    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // string representation
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(V).append(" ").append(E).append("\n");
        for (int v = 0; v < V; ++v) {
            buff.append(v).append(": ");
            for (Edge e : adj(v)) {
                buff.append(e).append("  ");
            }
            buff.append("\n");
        }
        return buff.toString();
    }

    // tinyEWG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        StdOut.println(G);
    }
}
