package sedgewick.coursera.week2.bonus.abstracts;

import sedgewick.coursera.week2.bonus.DirectedEdge;

public abstract class SingleSourceShortestPath {
    // length of shortest path from s to v
    public abstract double distTo(int v);

    // shortest path from s to v
    public abstract Iterable<DirectedEdge> pathTo(int v);

    // is there a path from s to v?
    public abstract boolean hasPathTo(int v);
}
