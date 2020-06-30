package sedgewick.coursera.week5.bonus;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import sedgewick.coursera.week5.bonus.interfaces.Compress;

public class RunLength implements Compress {
    private RunLength() {
        // Do not instantiate.
    }

    // maximum run-length count
    private final static int R = 256;
    // numbers of bits per count
    private final static int lgR = 8;

    public static void compress() {
        char cnt = 0;
        boolean b, old = false;
        while (!BinaryStdIn.isEmpty()) {
            b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(cnt);
                cnt = 0;
                old = !old;
            } else {
                if (cnt == R - 1) {
                    BinaryStdOut.write(cnt);
                    cnt = 0;
                    BinaryStdOut.write(cnt);
                }
            }
            ++cnt;
        }
        BinaryStdOut.write(cnt);
        BinaryStdOut.close();
    }

    public static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(lgR);
            for (int i = 0; i < run; ++i) {
                BinaryStdOut.write(bit);
            }
            bit = !bit;
        }
        BinaryStdOut.close();
    }

    // - > abra.txt
    // + > abra.txt
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            compress();
        } else if (args[0].equals("+")) {
            expand();
        } else {
            throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}
