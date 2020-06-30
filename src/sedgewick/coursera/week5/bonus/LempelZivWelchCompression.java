package sedgewick.coursera.week5.bonus;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;
import sedgewick.coursera.week5.bonus.interfaces.Compress;

public class LempelZivWelchCompression implements Compress {
    private LempelZivWelchCompression() {
        // Do not instantiate.
    }

    // number of input chars
    private static final int R = 256;
    // number of codewords = 2^12
    private static final int L = 4096;
    // codeword width
    private static final int W = 12;

    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();
        for (int i = 0; i < R; i++) {
            st.put("" + (char) i, i);
        }
        // R is codeword for EOF.
        int code = R + 1;
        while (input.length() > 0) {
            // Find max prefix match.
            String s = st.longestPrefixOf(input);
            // Print s's encoding.
            BinaryStdOut.write(st.get(s), W);
            int t = s.length();
            // Add s to symbol table.
            if (t < input.length() && code < L) {
                st.put(input.substring(0, t + 1), code++);
            }
            // Scan past s in input.
            input = input.substring(t);
        }
        // Write EOF
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];
        // next available codeword value
        int i;
        // Initialize table for chars.
        for (i = 0; i < R; ++i) {
            st[i] = "" + (char) i;
        }
        // (unused) lookahead for EOF
        st[i++] = " ";
        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];
        while (true) {
            // Write current substring.
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) {
                break;
            }
            // Get next codeword.
            String s = st[codeword];
            // If lookahead is invalid,
            if (i == codeword) {
                // make codeword from last one.
                s = val + val.charAt(0);
            }
            if (i < L) {
                // Add new entry to code table.
                st[i++] = val + s.charAt(0);
            }
            // Update current codeword.
            val = s;
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
