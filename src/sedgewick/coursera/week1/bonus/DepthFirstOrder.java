package sedgewick.coursera.week1.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class DepthFirstOrder {
    private final boolean[] marked;
    private final Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G) {
        reversePost = new Stack<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }

    // tinyDG2.txt
    public static void main(String[] args) {
        // taken from algs4 sample
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfs = new DepthFirstOrder(G);

        StdOut.print("Reverse postorder: ");
        for (int v : dfs.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
