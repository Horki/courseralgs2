package sedgewick.coursera.week3;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FlowNetwork {
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;

    // Create an empty flow network with V vertices
    public FlowNetwork(int V) {
        this.V = V;
        E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; ++v) {
            adj[v] = new Bag<>();
        }
    }

    // Construct flow network input stream
    public FlowNetwork(In in) {
        this(in.readInt());
        int edges = in.readInt();
        if (edges < 0) {
            throw new IllegalArgumentException("number of edges must be nonnegative");
        }
        for (int i = 0; i < edges; ++i) {
            int v = in.readInt();
            int w = in.readInt();
            validateVertex(v);
            validateVertex(w);
            double capacity = in.readDouble();
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    // add flow edge e to this network
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        // TODO: make search O(1)|O(log n) later
        boolean exists = false;
//        for (FlowEdge item : adj(v)) {
//            if (item.to() == w) {
//                exists = true;
//                break;
//            }
//        }
//        if (!exists) {
        // Add forward edge
        adj[v].add(e);
        // Add backward edge
        adj[w].add(e);
        ++E;
//        }
    }

    // Forward and backward edges incident to V
    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    // All edges in this flow network
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> b = new Bag<>();
        for (int v = 0; v < V; ++v) {
            for (FlowEdge e : adj(v)) {
                if (e.to() > v) {
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


    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(V + " " + E + "\n");
        for (int v = 0; v < V; ++v) {
            buff.append(v + ": ");
            for (FlowEdge e : adj(v)) {
                buff.append(e + "  ");
            }
            buff.append("\n");
        }
        return buff.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);
    }
}
