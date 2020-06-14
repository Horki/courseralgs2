package sedgewick.coursera.week1.abstracts;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Stack;

public abstract class Adj {
    protected final int V;
    protected int E;
    protected Bag<Integer>[] adj;

    public Adj(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be positive");
        }
        this.V = V;
        E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; ++v) {
            adj[v] = new Bag<>();
        }
    }

    public Adj(In in) {
        if (null == in) {
            throw new IllegalArgumentException("Input is not valid!");
        }
        try {
            V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; ++v) {
                adj[v] = new Bag<>();
            }
            int edges = in.readInt();
            StdOut.println("Vortex: " + V + ", Edges: " + edges);
            if (edges < 0) {
                throw new IllegalArgumentException("Number of edges must be positive");
            }
            for (int e = 0; e < edges; ++e) {
                int v = in.readInt();
                int w = in.readInt();
                StdOut.println("v = " + v + ", w = " + w);
                // validate
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid arg exception.", e);
        }
    }


    // Deep copy example
    public Adj(Adj G) {
        V = G.V();
        E = G.E();
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; ++v) {
            adj[v] = new Bag<>();
        }
        // do a copy
        for (int v = 0; v < V; ++v) {
            // Use stack to reverse adjacency list
            Stack<Integer> reverse = new Stack<>();
            for (int w : G.adj(v)) {
                reverse.push(w);
            }
            // Fill adjacency list
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    protected void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("Vertex: " + v + " is out off range");
        }
    }


    // vertices adjacent to v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // number of vertices
    public int V() {
        return V;
    }

    // number of edges
    public int E() {
        return E;
    }

    // add an edge v-w
    public abstract void addEdge(int v, int w);

    public abstract String toString();
}
