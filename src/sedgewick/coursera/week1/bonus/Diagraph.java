package sedgewick.coursera.week1.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Adj;

public class Diagraph extends Adj {

    // create an empty digraph with V vertices
    public Diagraph(int V) {
        super(V);
    }

    // create a digraph from input stream
    public Diagraph(In in) {
        super(in);
    }

    // reverse of this digraph
    public Diagraph reverse() {
        Diagraph reverse = new Diagraph(V);
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
        buffer.append(V + " vertices, " + E + " edges \n");
//        for (int v = 0; v < V; ++v) {
//            buffer.append(v + ": ");
//            for (int w : adj(v)) {
//                buffer.append(w + ", ");
//            }
//            buffer.append("\n");
//        }
        for (int v = 0; v < V(); ++v) {
            for (int w : adj(v)) {
                buffer.append(v + "->" + w + "\n");
            }
        }
        return buffer.toString();
    }

    // tinyDG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        Diagraph G = new Diagraph(in);
        StdOut.println(G);
        Diagraph reverse = G.reverse();
        StdOut.println("Reversed:");
        StdOut.println(reverse);
    }
}
