package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LSDRadixSort {
    public static void sort(String[] a, int W) {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];
        for (int d = W - 1; d >= 0; --d) {
            int[] cnt = new int[R + 1];
            for (String value : a) {
                cnt[value.charAt(d) + 1]++;
            }
            for (int r = 0; r < R; ++r) {
                cnt[r + 1] += cnt[r];
            }
            for (String s : a) {
                aux[cnt[s.charAt(d)]++] = s;
            }
            System.arraycopy(aux, 0, a, 0, N);
        }
        StdOut.println("done");
    }

    // input_lsd.txt
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        StdOut.println("Before");
        for (String item : a) {
            StdOut.println(item);
        }
        int w = a[0].length();
        for (String s : a) {
            assert s.length() == w : "Strings must have fixed length";
        }
        sort(a, w);
        StdOut.println("After sort:");
        for (String item : a) {
            StdOut.println(item);
        }

    }
}
