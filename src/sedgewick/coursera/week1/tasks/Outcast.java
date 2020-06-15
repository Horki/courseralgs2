package sedgewick.coursera.week1.tasks;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int index = -1;
        int maxDistance = Integer.MIN_VALUE;

        for (int i = 0; i < nouns.length; ++i) {
            int cnt = 0;
            for (int j = 0; j < nouns.length; ++j) {
                if (i != j) {
                    cnt += wordNet.distance(nouns[i], nouns[j]);
                }
            }
            if (cnt > maxDistance) {
                maxDistance = cnt;
                index = i;
            }
        }
        return nouns[index];
    }

    // Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int i = 2; i < args.length; ++i) {
            In in = new In(args[i]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[i] + ": " + outcast.outcast(nouns));
        }
    }
}