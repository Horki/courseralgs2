package sedgewick.coursera.week1;

import edu.princeton.cs.algs4.*;

public class ConnectedComponents {
    private boolean marked[];
    private int[] id;
    private int count;

    // Find connected components in G
    public ConnectedComponents(GraphAPI G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!marked[v]) {
                dfs(G, v);
                ++count;
            }
        }
    }

    // are v and w connected?
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    // number of connected components
    public int count() {
        return count;
    }

    // component identifier for v
    int id(int v) {
        validateVertex(v);
        return id[v];
    }

    private void dfs(GraphAPI G, int v) {
        validateVertex(v);
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    private void validateVertex(int x) {
        if (x < 0 || x >= marked.length) {
            throw new IllegalArgumentException("[CC]: Out of bounds!");
        }
    }

    // tinyG.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        GraphAPI G = new GraphAPI(in);
        ConnectedComponents cc = new ConnectedComponents(G);

        // number of connected components
        int m = cc.count();
        StdOut.println(m + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; ++i) {
            components[i] = new Queue<>();
        }
        for (int v = 0; v < G.V(); ++v) {
            components[cc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; ++i) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

    }
}
