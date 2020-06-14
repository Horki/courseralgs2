package sedgewick.coursera.week1.bonus;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Adj;

public class DirectedBFS {
    private boolean[] marked;

    public DirectedBFS(Adj G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int s : sources) {
            bfs(G, s);
        }
    }

    private void bfs(Adj G, int s) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                    marked[w] = true;
                }
            }
        }
    }

    public boolean visited(int v) {
        return marked[v];
    }

    // tinyDG2.txt
    public static void main(String[] args) {
        Adj G = new Digraph(new In(args[0]));
        Bag<Integer> sources = new Bag<>();
        for (int i = 1; i < args.length; ++i) {
            sources.add(Integer.parseInt(args[i]));
        }
        DirectedBFS reachable = new DirectedBFS(G, sources);
        for (int v = 0; v < G.V(); ++v) {
            if (reachable.visited(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }

}
