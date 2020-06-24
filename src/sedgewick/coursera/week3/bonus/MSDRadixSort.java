package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MSDRadixSort {
    // Radix
    private static int R = 256;
    // Cutoff for small sub-arrays
    private static final int M = 15;
    // Auxiliary array for distribution
    private static String[] aux;

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }

    public static void sort(String[] a) {
        aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo) {
            return;
        }
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; ++i) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int r = 0; r < R + 1; ++r) {
            count[r + 1] += count[r];
        }
        for (int i = lo; i <= hi; ++i) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; ++i) {
            a[i] = aux[i - lo];
        }
        // sort R subarrays recursively
        for (int r = 0; r < R; ++r) {
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    // input_lsd.txt
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        for (String item : a) {
            StdOut.println(item);
        }
        sort(a);
        StdOut.println("after sort");
        StdOut.println("After sort:");
        String[] res = res();
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i].equals(res[i]));
        }
    }

    private static String[] res() {
        return new String[]{
                "1ICK750",
                "1ICK750",
                "1OHV845",
                "1OHV845",
                "1OHV845",
                "2IYE230",
                "2RLA629",
                "2RLA629",
                "3ATW723",
                "3CIO720",
                "3CIO720",
                "4JZY524",
                "4PGC938",
        };
    }
}
