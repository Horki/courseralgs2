package sedgewick.coursera.week1.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Adj;

public class Digraph extends Adj {

    // create an empty digraph with V vertices
    public Digraph(int V) {
        super(V);
    }

    // create a digraph from input stream
    public Digraph(In in) {
        super(in);
    }

    // reverse of this digraph
    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int v = 0; v < V; ++v) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    // string representation, same as in Graph
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(V).append(" vertices, ").append(E).append(" edges \n");
//        for (int v = 0; v < V; ++v) {
//            buffer.append(v + ": ");
//            for (int w : adj(v)) {
//                buffer.append(w + ", ");
//            }
//            buffer.append("\n");
//        }
        for (int v = 0; v < V(); ++v) {
            for (int w : adj(v)) {
                buffer.append(v).append("->").append(w).append("\n");
            }
        }
        return buffer.toString();
    }

    // directed edge
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
            ++E;
        }
    }

    // tinyDG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G);
        Digraph reverse = G.reverse();
        StdOut.println("Reversed:");
        StdOut.println(reverse);
    }
}
