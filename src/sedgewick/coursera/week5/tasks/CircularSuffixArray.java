package sedgewick.coursera.week5.tasks;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private final String s;
    private final Integer[] index;

    private class CircularSort implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(a) != s.charAt(b)) {
                    return s.charAt(a) - s.charAt(b);
                }
                ++a;
                ++b;
                if (a >= s.length()) {
                    a = 0;
                }
                if (b >= s.length()) {
                    b = 0;
                }
            }
            return 0;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String is null!");
        }
        this.s = s;
        index = new Integer[length()];
        for (int i = 0; i < length(); ++i) {
            index[i] = i;
        }
        Arrays.sort(index, new CircularSort());
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException("out of bounds");
        }
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String input = "ABRACADABRA!";
        CircularSuffixArray array = new CircularSuffixArray(input);
        StdOut.println(array.length() == 12);
        for (int i = 0; i < array.length(); ++i) {
            StdOut.print(input.charAt(array.index(i)));
        }
        StdOut.println();
    }
}
