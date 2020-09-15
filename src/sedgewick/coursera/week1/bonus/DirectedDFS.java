package sedgewick.coursera.week1.bonus;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Adj;

public class DirectedDFS {
    private final boolean[] marked;

    public DirectedDFS(Adj G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int s : sources) {
            dfs(G, s);
        }
    }

    private void dfs(Adj G, int s) {
        marked[s] = true;
        for (int w : G.adj(s)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean visited(int v) {
        return marked[v];
    }

    // tinyDG.txt
    public static void main(String[] args) {
        Adj G = new Digraph(new In(args[0]));
        Bag<Integer> sources = new Bag<>();
        for (int i = 1; i < args.length; ++i) {
            sources.add(Integer.parseInt(args[i]));
        }
        DirectedDFS reachable = new DirectedDFS(G, sources);
        for (int v = 0; v < G.V(); ++v) {
            if (reachable.visited(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }

}
