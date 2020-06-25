package sedgewick.coursera.week4.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteForce {
    public static int search(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();
        for (int i = 0; i <= N; ++i) {
            int j = 0;
            for (; j < M; ++j) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                return i;
            }
        }
        // not found
        return N;
    }

    public static int altSearch(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();
        int i = 0;
        int j = 0;
        for (; i < N && j < M; ++i) {
            if (text.charAt(i) == pattern.charAt(j)) {
                ++j;
            } else {
                i -= j;
                j = 0;
            }
        }
        if (j == M) {
            return i - M;
        }
        // not found
        return N;
    }

    // input.txt
    public static void main(String[] args) {
        String[] input = StdIn.readAllStrings();
        String text = String.join(" ", input);
        int N = text.length();
        String pattern = "attack at dawn";
        StdOut.println("text len: " + N);
        StdOut.println(search(pattern, text) == 1517);
        StdOut.println(altSearch(pattern, text) == 1517);
    }
}
