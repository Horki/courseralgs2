package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SuffixArray;

public class KWIC {
    // ./KWIC tale.txt 15
    public static void main(String[] args) {
        In in = new In(args[0]);
//        int context = Integer.parseInt(args[1]);
        int context = 15;
        String text = in.readAll().replaceAll("\\s+", " ");
        int N = text.length();
        SuffixArray sa = new SuffixArray(text);
        StdOut.print("insert text> ");
        while (StdIn.hasNextLine()) {
            StdOut.print("insert text> ");
            String q = StdIn.readLine();
            for (int i = sa.rank(q); i < N && sa.select(i).startsWith(q); ++i) {
                int from = Math.min(0, sa.index(i) - context);
                int to = Math.min(N - 1, from + q.length() + (2 * context));
                StdOut.println(text.substring(from, to));
            }
            StdOut.println();
        }
    }
}
