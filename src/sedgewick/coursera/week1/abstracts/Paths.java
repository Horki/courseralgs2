package sedgewick.coursera.week1.abstracts;

import edu.princeton.cs.algs4.Stack;
import sedgewick.coursera.week1.GraphAPI;

public abstract class Paths {
    protected boolean[] marked;
    protected int[] edgeTo;
    protected int count;
    protected final int s;

    public Paths(GraphAPI G, int source) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(source);
        count = 0;
        s = source;
    }

    // is there a path from "s" to "v"
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    // Path from "s" to "v"; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;

    }

    protected void validateVertex(int v) {
        if (v < 0 || v >= marked.length) {
            throw new IllegalArgumentException("Vertex is out of bounds");
        }
    }

    public int getCount() {
        return count;
    }
}
