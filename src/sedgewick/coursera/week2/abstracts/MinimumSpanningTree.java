package sedgewick.coursera.week2.abstracts;

import sedgewick.coursera.week2.Edge;

public abstract class MinimumSpanningTree {
//    public MinimumSpanningTree(EdgeWeightedGraph G);

    public abstract Iterable<Edge> edges();

    public abstract double weight();
}
