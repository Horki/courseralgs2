package sedgewick.coursera.week5.tasks;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BurrowsWheeler {
    private BurrowsWheeler() {
        // Do not instantiate.
    }

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        while (!BinaryStdIn.isEmpty()) {
            String input = BinaryStdIn.readString();
            CircularSuffixArray circularSuffixArray = new CircularSuffixArray(input);
            for (int i = 0; i < circularSuffixArray.length(); ++i) {
                if (circularSuffixArray.index(i) == 0) {
                    BinaryStdOut.write(i);
                    break;
                }
            }
            for (int i = 0; i < circularSuffixArray.length(); ++i) {
                if (circularSuffixArray.index(i) == 0) {
                    BinaryStdOut.write(input.charAt(input.length() - 1), 8);
                    continue;
                }
                BinaryStdOut.write(input.charAt(circularSuffixArray.index(i) - 1), 8);
            }
            BinaryStdOut.close();
        }
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        while (!BinaryStdIn.isEmpty()) {
            int first = BinaryStdIn.readInt();
            String input = BinaryStdIn.readString();
            char[] t = input.toCharArray();
            Map<Character, Queue<Integer>> position = new HashMap<>();
            for (int i = 0; i < t.length; ++i) {
                if (!position.containsKey(t[i])) {
                    position.put(t[i], new Queue<>());
                }
                position.get(t[i]).enqueue(i);
            }
            Arrays.sort(t);
            int[] next = new int[t.length];
            for (int i = 0; i < t.length; ++i) {
                next[i] = position.get(t[i]).dequeue();
            }

            int cur = first;
            for (int i = 0; i < t.length; ++i) {
                BinaryStdOut.write(t[cur]);
                cur = next[cur];
            }
            BinaryStdOut.close();
        }
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        } else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}
