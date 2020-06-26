package sedgewick.coursera.week4.tasks;

import edu.princeton.cs.algs4.Bag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    private static class Node {
        private String value; // word that ends on this node
        private Node[] next = new Node[R];
    }

    private class Trie {
        private Node root;

        private Trie() {
            root = new Node();
        }

        private void put(String word) {
            root = put(root, word, 0);
        }

        private Node put(Node x, String word, int d) {
            if (x == null) {
                x = new Node();
            }
            if (d == word.length()) {
                x.value = word;
                return x;
            }
            int idx = charToIndex(word.charAt(d));
            x.next[idx] = put(x.next[idx], word, d + 1);
            return x;
        }

        private boolean contains(String key) {
            return get(key) != null;
        }

        private String get(String key) {
            Node x = get(root, key, 0);
            if (x == null) {
                return null;
            }
            return x.value;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) {
                return null;
            }
            if (d == key.length()) {
                return x;
            }
            int idx = charToIndex(key.charAt(d));
            return get(x.next[idx], key, d + 1);
        }
    }

    // extended ASCII
    private static final int R = 26;
    private Trie trie;
    private int[] letters;
    private boolean[] onPath;
    private Set<String> words;
    private ArrayList<Bag<Integer>> adj;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new Trie();
        for (String word : dictionary) {
            if (word.length() >= 3) {
                trie.put(word);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words = new HashSet<>();
        adj = new ArrayList<>();
        int v = board.rows() * board.cols();
        letters = new int[v];

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                int idx = i * board.cols() + j;
                letters[idx] = board.getLetter(i, j) - 'A';

                // add neighbors
                adj.add(idx, new Bag<>());
                for (int k = i - 1; k <= i + 1; ++k) {
                    for (int m = j - 1; m <= j + 1; ++m) {
                        if (k == i && m == j) {
                            continue;
                        }
                        if (isValid(board, k, m)) {
                            adj.get(idx).add(k * board.cols() + m);
                        }
                    }
                }
            }
        }

        for (int s = 0; s < v; ++s) {
            for (int t = 0; t < v; ++t) {
                if (s == t) {
                    continue;
                }
                // dfs find all path from s to t
                onPath = new boolean[v];
                dfs(s, t, trie.root.next[letters[s]]);
            }
        }
        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (trie.contains(word)) {
            return points(word);
        }
        return 0;
    }

    private void dfs(int v, int t, Node x) {
        // if no prefix match then stop expanding
        if (x == null) {
            return;
        }

        // deal with special Qu case
        // if v stands for Q, then move to node 'U'
        if (letters[v] == 16) {
            x = x.next[charToIndex('U')];
        }

        if (x == null) {
            return;
        }

        // add v to current path
        onPath[v] = true;

        // found path from s to t
        if (v == t) {
            if (x.value != null) {
                words.add(x.value);
            }
        } else {
            for (int w : adj.get(v)) {
                if (!onPath[w]) {
                    dfs(w, t, x.next[letters[w]]);
                }
            }
        }
        // done exploring from v, so remove from path
        onPath[v] = false;
    }

    private static boolean isValid(BoggleBoard board, int row, int col) {
        return (row >= 0) && (col >= 0) && (row < board.rows()) && (col < board.cols());
    }

    private static int charToIndex(char c) {
        return c - 'A';
    }


    // word length 	|  	points
    // -----------------------
    //      3–4 	|	1
    //        5 	|	2
    //        6 	|	3
    //        7 	|	5
    //        8+ 	|	11
    private static int points(String word) {
        int[] scores = {0, 0, 0, 1, 1, 2, 3, 5};
        int N = word.length();
        if (N >= 8) {
            return 11;
        }
        return scores[N];
    }
}
