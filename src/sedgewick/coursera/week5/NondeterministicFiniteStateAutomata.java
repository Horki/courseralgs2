package sedgewick.coursera.week5;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class NondeterministicFiniteStateAutomata {
    // match transitions
    private final char[] re;
    // epsilon transition digraph
    private final Digraph G;
    // number of states
    private final int M;

    public NondeterministicFiniteStateAutomata(String regexp) {
        M = regexp.length();
        re = regexp.toCharArray();
        G = buildEpsilonTransitionDigraph();
    }

    public boolean recognizes(String text) {
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); ++v) {
            if (dfs.marked(v)) {
                pc.add(v);
            }
        }

        for (int i = 0; i < text.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc) {
                if (v == M) {
                    continue;
                }
                if ((re[v] == text.charAt(i)) || re[v] == '.') {
                    match.add(v + 1);
                }
            }
            dfs = new DirectedDFS(G, match);
            pc = new Bag<>();
            for (int v = 0; v < G.V(); v++) {
                if (dfs.marked(v)) {
                    pc.add(v);
                }
            }
        }

        for (int v : pc) {
            if (v == M) {
                return true;
            }
        }
        return false;

    }

    public Digraph buildEpsilonTransitionDigraph() {
        Digraph G = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<>();
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }
            if (i < M - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i + 1);
            }
        }
        return G;
    }

    public static void main(String[] args) {
        String regexp = "( " + args[0] + " )";
        String txt = args[1];
        NondeterministicFiniteStateAutomata nfa = new NondeterministicFiniteStateAutomata(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
