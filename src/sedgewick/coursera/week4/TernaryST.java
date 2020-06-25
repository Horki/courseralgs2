package sedgewick.coursera.week4;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week4.interfaces.StringST;

public class TernaryST<T> implements StringST<T> {
    private class Node {
        char c;
        Node left;
        Node mid;
        Node right;
        T value;
    }

    private Node root;
    private int size;

    public TernaryST() {
        root = new Node();
        size = 0;
    }

    @Override
    public void put(String key, T value) {
        root = put(root, key, value, 0);
        ++size;
    }

    private Node put(Node x, String key, T value, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, value, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, value, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, value, d + 1);
        } else {
            x.value = value;
        }
        return x;

    }

    @Override
    public T get(String key) {
        Node x = get(root, key, 0);
        return (T) x.value;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    @Override
    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);
        if (x.value != null) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }


    @Override
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<>();
        Node x = get(root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (x.value != null) {
            queue.enqueue(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    @Override
    public String longestPrefixOf(String s) {
        if (s == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (s.length() == 0) {
            return null;
        }
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < s.length()) {
            char c = s.charAt(i);
            if (c < x.c) {
                x = x.left;
            } else if (c > x.c) {
                x = x.right;
            } else {
                ++i;
                if (x.value != null) {
                    length = i;
                }
                x = x.mid;
            }
        }
        return s.substring(0, length);
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), 0, s, queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) {
            return;
        }
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) {
            collect(x.left, prefix, i, pattern, queue);
        }
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.value != null) {
                queue.enqueue(prefix.toString() + x.c);
            }
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i + 1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) {
            collect(x.right, prefix, i, pattern, queue);
        }
    }

    @Override
    public int size() {
        return size;
    }
//
//    @Override
//    public void delete(String key) {
//
//    }

    // three.txt
    public static void main(String[] args) {
        String[] tries = StdIn.readAllStrings();
        TernaryST<Integer> ternaryST = new TernaryST<>();
        for (int i = 0; i < tries.length; ++i) {
            ternaryST.put(tries[i], i);
        }
        for (String item : tries) {
            StdOut.println(ternaryST.contains(item));
            StdOut.println(ternaryST.get(item));
        }
        for (String k : ternaryST.keys()) {
            StdOut.println(k + ": " + ternaryST.get(k));
        }
        StdOut.println("prefix 'fe'");
        for (String item : ternaryST.keysWithPrefix("fe")) {
            StdOut.println(item);
        }
        StdOut.println(ternaryST.size() == 34);
    }

}
