package sedgewick.coursera.week6.bonus_2_intractability;

import edu.princeton.cs.algs4.Graph;

// https://en.wikipedia.org/wiki/Hamiltonian_path
public class HamiltonPath {
    // vertices on current path
    private final boolean[] marked;
    // number of Hamilton paths
    private int count;

    public HamiltonPath(Graph G) {
        count = 0;
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            dfs(G, v, 1);
        }
    }

    private void dfs(Graph G, int v, int depth) {
        marked[v] = true;
        if (depth == G.V()) {
            ++count;
        }
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, depth + 1);
            }
        }
        marked[v] = false;
    }

    public int count() {
        return count;
    }
}
