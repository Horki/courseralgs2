package sedgewick.coursera.week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Paths;

public class DFS extends Paths {

    public DFS(GraphAPI G, int s) {
        super(G, s);
        dfs(G, s);
    }

    private void dfs(GraphAPI g, int v) {
        ++count;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    // run with tinyG.txt 0
    public static void main(String[] args) {
        // 0 1 0 1
        // 0 1 0 0
        // 0 0 0 1
        // 0 1 0 0

        //  0  1  2  3
        //  4  5  6  7
        //  8  9 10 11
        // 12 13 14 15
        //  0 -  4
        //  4 -  8
        //  8 - 12
        //  8 -  9
        //  9 - 10
        // 10 -  6
        //  6 -  2
        //  6 -  7
        // 10 - 14
        // 14 - 15
        // Solution: 0 - 4 - 8 - 9 - 10 - 14 - 15
        StdOut.println("Depth-first search");
        GraphAPI G = new GraphAPI(new In(args[0]));
//        int s = Integer.parseInt(args[1]);
        int s = 0;
        Paths search = new DFS(G, s);
        StdOut.println("DFS count " + search.getCount());
        for (int v = 0; v < G.V(); v++) {
            StdOut.print(s + " to " + v + ": ");
            if (search.hasPathTo(v)) {
                for (int x : search.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    } else {
                        StdOut.print("-" + x);
                    }
                }
            }
            StdOut.println();
        }
        if (search.getCount() != G.V()) {
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}
