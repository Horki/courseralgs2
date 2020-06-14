package sedgewick.coursera.week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Adj;

public class GraphAPI extends Adj {

    public GraphAPI(int Vertex) {
        super(Vertex);
    }

    // Deep copy example
    public GraphAPI(Adj G) {
        super(G);
    }

    public GraphAPI(In in) {
        super(in);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(V + " vertices, " + E + " edges \n");
        for (int v = 0; v < V; ++v) {
            res.append(v + ": ");
            for (int w : adj(v)) {
                res.append(w + ", ");
            }
            res.append("\n");
        }
        return res.toString();
    }

    // Compute the degree of v
    public static int degree(GraphAPI G, int v) {
        int degree = 0;
        for (int ignored : G.adj(v)) {
            ++degree;
        }
        return degree;
    }

    // Compute the degree of v
    public int degree(int v) {
        int degree = 0;
        for (int ignored : adj(v)) {
            ++degree;
        }
        return degree;
    }


    // Compute maximum degree
    public static int maxDegree(GraphAPI G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    // Compute maximum degree
    public int maxDegree() {
        int max = 0;
        for (int v = 0; v < V; v++) {
            if (degree(v) > max) {
                max = degree(v);
            }
        }
        return max;
    }

    // undirected edge
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        boolean exists = false;
        // TODO: make search O(1)|O(log n) later
        for (int item : adj(v)) {
            if (item == w) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            adj[v].add(w);
            adj[w].add(v);
            ++E;
        }
    }

    // Compute Average Degree
    public static double averageDegree(GraphAPI G) {
        return 2.0 * G.E() / G.V();
    }

    // Compute Average Degree
    public double averageDegree() {
        return 2.0 * E / V;
    }

    public static int numberOfSelfLoops(GraphAPI G) {
        int count = 0;
        for (int v = 0; v < G.V(); ++v) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    ++count;
                }
            }
        }
        return count / 2;
        // each edge counted twice
    }

    public int numberOfSelfLoops() {
        int count = 0;
        for (int v = 0; v < V; ++v) {
            for (int w : adj(v)) {
                if (v == w) {
                    ++count;
                }
            }
        }
        return count / 2;
        // each edge counted twice
    }

    // Unit testing
    // Sample https://algs4.cs.princeton.edu/41graph/tinyG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        GraphAPI G = new GraphAPI(in);
        StdOut.println(G);
        double averageDegree = G.averageDegree();
        int maxDegree = G.maxDegree();
        int numSelfLoops = G.numberOfSelfLoops();
        StdOut.println("average = " + averageDegree + ", max = " + maxDegree + ", self loops: " + numSelfLoops);
        StdOut.println(averageDegree == 2.0);
        StdOut.println(maxDegree == 4);
        StdOut.println(numSelfLoops == 0);
        for (int v = 0; v < G.V(); ++v) {
            for (int w : G.adj(v)) {
                StdOut.println(v + "-" + w);
            }
        }
        GraphAPI Gcopy = new GraphAPI(G);
        StdOut.println(Gcopy.E() == G.E());
        StdOut.println(Gcopy.V() == G.V());
        StdOut.println(numSelfLoops == Gcopy.numberOfSelfLoops());
    }
}
