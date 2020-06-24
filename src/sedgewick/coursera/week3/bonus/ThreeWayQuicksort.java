package sedgewick.coursera.week3.bonus;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ThreeWayQuicksort {
    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // 3-way partitioning (using dth character)
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo) {
            return;
        }
        int lt = lo;
        int gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) {
                exch(a, lt++, i++);
            } else if (t > v) {
                exch(a, i, gt--);
            } else {
                ++i;
            }
        }
        // sort 3 sub-arrays recursively
        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    // input_msd.txt
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        for (String item : a) {
            StdOut.println(item);
        }
        sort(a);
        StdOut.println("After sort");
        String[] res = res();
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i].equals(res[i]));
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
