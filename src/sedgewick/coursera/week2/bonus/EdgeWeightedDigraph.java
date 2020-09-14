package sedgewick.coursera.week2.bonus;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

// Almost "Same" as in Graph
public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private final Bag<DirectedEdge>[] adj;
    private final int[] indegree;

    // create an empty graph with V vertices
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        indegree = new int[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges in a Digraph must be non negative");
        }
        for (int i = 0; i < E; ++i) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = 0.01 * StdRandom.uniform(100);
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }


    // create a graph from input stream
    public EdgeWeightedDigraph(In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("Number of edges must be non negative");
            }
            indegree = new int[V];
            adj = (Bag<DirectedEdge>[]) new Bag[V];
            for (int v = 0; v < V; ++v) {
                adj[v] = new Bag<>();
            }

            for (int i = 0; i < E; ++i) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                addEdge(new DirectedEdge(v, w, weight));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }

    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        E = G.E();
        for (int v = 0; v < G.V(); ++v) {
            indegree[v] = G.indegree(v);
        }
        for (int v = 0; v < G.V(); ++v) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<>();
            for (DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                adj[v].add(e);
            }
        }
    }

    public Digraph toDigraph() {
        Digraph digraph = new Digraph(V);
        for (int v = 0; v < V; ++v) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<>();
            for (DirectedEdge e : adj(v)) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                digraph.addEdge(e.from(), e.to());
            }
        }
        return digraph;
    }

    // add weighted edge e to this graph
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        // TODO: make search O(1)|O(log n) later
        boolean exists = false;
        for (DirectedEdge item : adj(v)) {
            if (item.to() == w) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            adj[v].add(e);
            indegree[w]++;
            ++E;
        }
    }

    // edges incident to v
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }


    // all edges in this graph
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> b = new Bag<>();
        for (int v = 0; v < V; ++v) {
            for (DirectedEdge e : adj(v)) {
                b.add(e);
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
            for (DirectedEdge e : adj(v)) {
                buff.append(e).append("  ");
            }
            buff.append("\n");
        }
        return buff.toString();
    }

    // tinyEWD.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println(G);
    }
}
