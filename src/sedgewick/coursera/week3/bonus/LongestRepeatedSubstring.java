package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SuffixArray;

public class LongestRepeatedSubstring {
    // mobydick.txt
    public static void main(String[] args) {
        StdOut.println("Longest Repeated Substring in Moby Dick!");
        String text = StdIn.readAll();
        int N = text.length();
        SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        StdOut.println("parsing...");
        for (int i = 1; i < N; ++i) {
            int length = sa.lcp(i);
            if (length > lrs.length()) {
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        StdOut.println("\nresult!!");
        StdOut.println(lrs);
    }
}
