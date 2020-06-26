package sedgewick.coursera.week4.bonus;

import edu.princeton.cs.algs4.StdOut;

public class BoyerMoore {
    private final int[] right;
    private final String pattern;
    private final int M;

    public BoyerMoore(String pattern) {
        this.pattern = pattern;
        M = pattern.length();
        int R = 256;
        right = new int[R];
        for (int c = 0; c < R; ++c) {
            right[c] = -1;
        }
        for (int j = 0; j < M; ++j) {
            right[pattern.charAt(j)] = j;
        }
    }

    public int search(String text) {
        int N = text.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; --j) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    skip = j - right[text.charAt(i + j)];
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            if (skip == 0) {
                // found
                return i;
            }
        }
        // NOT found
        return N;
    }

    public static void main(String[] args) {
        String text = "AABRAACADABRAACAADABRA";
        int N = text.length();
        String pattern = "AACAA";
        StdOut.println("text len: " + N);
        BoyerMoore bm = new BoyerMoore(pattern);
        StdOut.println(bm.search(text));

    }
}
