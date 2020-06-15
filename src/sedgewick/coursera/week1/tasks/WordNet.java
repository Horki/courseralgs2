package sedgewick.coursera.week1.tasks;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final SAP sap;
    private final Digraph G;
    private int V;

    private RedBlackBST<String, ArrayList<Integer>> nounsTree;
    private HashMap<Integer, String> nounsMap;

    // constructor takes the name of the two input files
    public WordNet(String syn, String hypernyms) {
        if (null == syn || null == hypernyms) {
            throw new IllegalArgumentException("WordNet: constructor fail");
        }
        V = 0;
        nounsTree = new RedBlackBST<>();
        nounsMap = new HashMap<>();

        loadSynset(syn);
        G = new Digraph(V);
        loadHypernyms(hypernyms);
        sap = new SAP(G);
    }

    private void loadSynset(String path) {
        StdOut.println("Loading synset!");
        In csv = new In(path);
        while (!csv.isEmpty()) {
            ++V;
            String row = csv.readLine();
            String[] items = row.split(",");

            int id = Integer.parseInt(items[0]);
            nounsMap.put(id, items[1]);

            // get syntet, nouns
            for (String noun : items[1].split(" ")) {
                ArrayList<Integer> ids;
                if (!nounsTree.contains(noun)) {
                    ids = new ArrayList<>();
                    nounsTree.put(noun, ids);
                } else {
                    ids = nounsTree.get(noun);
                }
                ids.add(id);
            }
            // ignore gloss
//            String[] gloss = items[2].split(" ");
        }
    }

    private void loadHypernyms(String path) {
        StdOut.println("Loading hypernyms!");
        In csv = new In(path);
        while (!csv.isEmpty()) {
            String row = csv.readLine();
            String[] items = row.split(",");
            // get id
            int v = Integer.parseInt(items[0]);
            // get hypernyms
            for (int i = 1; i < items.length; ++i) {
                int w = Integer.parseInt(items[i]);
                G.addEdge(v, w);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsTree.keys();
    }

    // is the word a WordNet noun?
    // logarithmic
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("isNoun: fail");
        }
        return nounsTree.contains(word);
    }

    // distance between nounA and nounB (defined below)
    // linear
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("distance: fail");
        }
        ArrayList<Integer> vs = nounsTree.get(nounA);
        ArrayList<Integer> ws = nounsTree.get(nounB);

        if (vs.size() == 1 && ws.size() == 1) {
            return sap.length(vs.get(0), ws.get(0));
        }
        return sap.length(vs, ws);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // linear
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("sap: fail");
        }
        ArrayList<Integer> vs = nounsTree.get(nounA);
        ArrayList<Integer> ws = nounsTree.get(nounB);
        int id;

        if (vs.size() == 1 && ws.size() == 1) {
            id = sap.ancestor(vs.get(0), ws.get(0));
        } else {
            id = sap.ancestor(vs, ws);
        }

        return nounsMap.get(id);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        StdOut.println("nouns");
//        for (String noun : wordNet.nouns()) {
//            StdOut.println(noun);
//        }
        String nounA = "European_magpie";
        String nounB = "brace_wrench";
        StdOut.println(wordNet.isNoun(nounA));
        StdOut.println(wordNet.isNoun(nounB));
        StdOut.println(wordNet.sap(nounA, nounB));
        StdOut.println(wordNet.distance(nounA, nounB));
    }
}
