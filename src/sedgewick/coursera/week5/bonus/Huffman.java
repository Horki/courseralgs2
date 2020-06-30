package sedgewick.coursera.week5.bonus;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Huffman {
    private Huffman() {
        // not init
    }

    private static final int R = 256;

    private static class Node implements Comparable<Node> {
        private final char c;
        private final int freq;
        private final Node left;
        private final Node right;

        Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node that) {
            return freq - that.freq;
        }
    }

    private static Node buildTrie(int[] freq) {
        // Initialize priority queue with singleton trees.
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char c = 0; c < R; ++c) {
            if (freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1) {
            // merge two smallest trees
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        }
        return new Node('\0', 0, null, null);
    }

    // Preorder Traversal
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.c, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static void expand() {
        // read in encoding trie
        Node root = readTrie();
        // read in number of characters
        int N = BinaryStdIn.readInt();
        for (int i = 0; i < N; ++i) {
            Node x = root;
            // expand codeword for i-th char
            while (!x.isLeaf()) {
                if (!BinaryStdIn.readBoolean()) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            BinaryStdOut.write(x.c, 8);
        }
        BinaryStdOut.close();
    }

    private static void compress() {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // tabulate frequency counts
        int[] freq = new int[R];
        for (int i = 0; i < input.length; ++i) {
            freq[input[i]]++;
        }

        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
        String[] st = new String[R];
        buildCode(st, root, "");

        // print trie for decoder
        writeTrie(root);

        // print number of bytes in original uncompressed message
        BinaryStdOut.write(input.length);

        // use Huffman code to encode input
        for (int i = 0; i < input.length; ++i) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); ++j) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                } else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                } else throw new IllegalStateException("Illegal state");
            }
        }

        // close output stream
        BinaryStdOut.close();
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        } else {
            st[x.c] = s;
        }
    }

    // + < abra.txt
    // - < abra.txt
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
