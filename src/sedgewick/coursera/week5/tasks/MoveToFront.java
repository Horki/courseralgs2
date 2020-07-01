package sedgewick.coursera.week5.tasks;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private MoveToFront() {
        // Do not instantiate.
    }

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] orderedSequence = new char[R];
        for (int i = 0; i < R; ++i) {
            orderedSequence[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            for (int i = 0; i < R; ++i) {
                if (orderedSequence[i] == input) {
                    BinaryStdOut.write(orderedSequence[i], 8);
                    swap(orderedSequence, 0, i);
                }
            }
        }
        BinaryStdOut.close();
    }

    private static void swap(char array[], int first, int second) {
        char temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] orderedSequence = new char[R];
        for (int i = 0; i < R; ++i) {
            orderedSequence[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char input = BinaryStdIn.readChar();
            BinaryStdOut.write(orderedSequence[input], 8);
            swap(orderedSequence, 0, input);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    // sample1.txt
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        } else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}
