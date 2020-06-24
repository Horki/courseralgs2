package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MSDRadixSort {
    // Radix
    private static int R = 256;
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

    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // Cutoff to insertion sort
    public static void sort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); --j) {
                exch(a, j, j - 1);
            }
        }
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

    // input_msd.txt
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        String[] aCp = a.clone();
        for (String item : a) {
            StdOut.println(item);
        }
        sort(a);
        StdOut.println("After sort 1:");
        String[] res = res();
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i].equals(res[i]));
        }
        StdOut.println("After sort 2:");
        sort(aCp, 0, aCp.length - 1, 0);
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(aCp[i].equals(res[i]));
        }
    }

    private static String[] res() {
        return new String[]{
                "are",
                "by",
                "sea",
                "seashells",
                "seashells",
                "sells",
                "sells",
                "she",
                "she",
                "shells",
                "shore",
                "surely",
                "the",
                "the",
        };
    }
}
