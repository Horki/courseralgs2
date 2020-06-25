package sedgewick.coursera.week4;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week4.interfaces.StringST;

public class TrieST<T> implements StringST<T> {
    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    // extended ASCII
    private static final int R = 256;
    private Node root;
    private int size;

    public TrieST() {
        root = new Node();
        size = 0;
    }

    public void put(String key, T val) {
        root = put(root, key, val, 0);
        ++size;
    }

    private Node put(Node x, String key, T val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.value = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public T get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return (T) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    @Override
    public String longestPrefixOf(String s) {
        int len = search(root, s, 0, 0);
        return s.substring(0, len);
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        Queue<String> q = new Queue<>();
        collect(root, "", s, q);
        return q;
    }

    @Override
    public int size() {
        return size;
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.value != null) {
            length = d;
        }
        if (d == s.length()) {
            return length;
        }
        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }


    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) {
            return;
        }
        if (x.value != null) {
            results.enqueue(prefix.toString());
        }
        for (char c = 0; c < R; ++c) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) {
            return;
        }
        if (d == pat.length() && x.value != null) {
            q.enqueue(pre);
        }
        if (d == pat.length()) {
            return;
        }
        char next = pat.charAt(d);
        for (char c = 0; c < R; ++c) {
            if (next == '.' || next == c) {
                collect(x.next[c], pre + c, pat, q);
            }
        }
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            x.value = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.value != null) {
            return x;
        }
        for (char c = 0; c < R; ++c) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }


    // three.txt
    public static void main(String[] args) {
        String[] tries = StdIn.readAllStrings();
        TrieST<Integer> trieST = new TrieST<>();
        for (int i = 0; i < tries.length; ++i) {
            trieST.put(tries[i], i);
        }
        for (String item : tries) {
            StdOut.println(trieST.contains(item));
            StdOut.println(trieST.get(item));
        }
        for (String k : trieST.keys()) {
            StdOut.println(k + ": " + trieST.get(k));
        }
        StdOut.println("prefix 'fe'");
        for (String item : trieST.keysWithPrefix("fe")) {
            StdOut.println(item);
        }
        StdOut.println(trieST.size() == 34);
    }
}
