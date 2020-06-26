package sedgewick.coursera.week4.bonus;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class KnuthMorrisPratt {
    private final int[][] dfa;
    private final int M;

    public KnuthMorrisPratt(String pattern) {
        int R = 256;
        M = pattern.length();
        dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; ++j) {
            for (int c = 0; c < R; ++c) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public int search(String text) {
        int N = text.length();
        int i = 0;
        int j = 0;
        for (; i < N && j < M; ++i) {
            j = dfa[text.charAt(i)][j];
        }
        if (j == M) {
            return i - M;
        }
        // not found
        return N;
    }

    public int search(In in) {
        int i = 0;
        int j = 0;
        for (; !in.isEmpty() && j < M; ++i) {
            j = dfa[in.readChar()][j];
        }
        if (j == M) {
            return i - M;
        }
        // Not found
        return Integer.MIN_VALUE;
    }

    // kmp.txt
    public static void main(String[] args) {
        String text = "AABRAACADABRAACAADABRA";
        int N = text.length();
        String pattern = "AACAA";
        StdOut.println("text len: " + N);
        KnuthMorrisPratt kMF = new KnuthMorrisPratt(pattern);
        StdOut.println(kMF.search(text));
        StdOut.println(kMF.search(new In(args[0])));
    }
}
