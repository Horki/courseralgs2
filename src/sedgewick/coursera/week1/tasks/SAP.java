package sedgewick.coursera.week1.tasks;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;

    private class Result {
        private final int length;
        private final int ancestor;

        public Result(int l, int a) {
            length = l;
            ancestor = a;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (null == G) {
            throw new IllegalArgumentException("Invalid constructor");
        }
        this.G = G;
    }

    private Result calculate(int v, int w) {
        int len = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        for (int x = 0; x < G.V(); ++x) {
            if (bfsV.hasPathTo(x) && bfsW.hasPathTo(x)) {
                int prev_len = len;
                len = Math.min(len, bfsV.distTo(x) + bfsW.distTo(x));
                if (prev_len != len) {
                    ancestor = x;
                }
            }
        }
        return len == Integer.MAX_VALUE ? new Result(-1, -1) : new Result(len, ancestor);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        Result res = calculate(v, w);
        return res.length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        Result res = calculate(v, w);
        return res.ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (null == v || null == w) {
            throw new IllegalArgumentException("length error");
        }
        validateVertex(v);
        validateVertex(w);
        int shortest = -1;
        for (int vv : v) {
            for (int ww : w) {
                int current = length(vv, ww);
                shortest = Math.min(shortest, current);
            }
        }
        return shortest;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (null == v || null == w) {
            throw new IllegalArgumentException("ancestor error");
        }
        validateVertex(v);
        validateVertex(w);
        int shortest = -1;
        for (int vv : v) {
            for (int ww : w) {
                int current = ancestor(vv, ww);
                shortest = Math.min(shortest, current);
            }
        }
        return shortest;
    }

    private void validateVertex(int x) {
        if (x < 0 || x >= G.V()) {
            throw new IllegalArgumentException("Out of bonds");
        }
    }

    private void validateVertex(Iterable<Integer> v) {
        for (Object i : v) {
            if (null == i) {
                throw new IllegalArgumentException("Iter null");
            }
            validateVertex((int) i);
        }
    }

    // do unit testing of this class
    // SAP digraph1.txt
    // SAP digraph25.txt
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
        {
            try {
                Stack<Integer> s = new Stack<>();
                s.push(1);
                s.push(2);
                Stack<Integer> s2 = new Stack<>();
                s.push(1);
                s.push(2);
                s.push(null);
                s.push(3);

                int length = sap.length(s, s2);
                int ancestor = sap.ancestor(s, s2);
                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
                StdOut.println(length == 4);
                StdOut.println(ancestor == 1);
            } catch (IllegalArgumentException e) {
                StdOut.println("good ex");
            } catch (NullPointerException e) {
                StdOut.println("bad ex");
            }
        }
        {
            int v = 9;
            int w = 12;
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.println(length == 3);
            StdOut.println(ancestor == 5);
        }
        {
            int v = 7;
            int w = 2;
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.println(length == 4);
            StdOut.println(ancestor == 0);
        }
        {
            int v = 1;
            int w = 6;
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.println(length == -1);
            StdOut.println(ancestor == -1);
        }
    }
}
