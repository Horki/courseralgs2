package sedgewick.coursera.week5;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// g/re/p
public class GloballySearchForRegularExpressionsAndPrintMatchingLines {
    // "s..ict.." words.txt
    public static void main(String[] args) {
        String re = "(.*" + args[0] + ".*)";
//        String re = "(.*s..ict..*)";
        StdOut.println("re = " + re);
        NondeterministicFiniteStateAutomata nfa = new NondeterministicFiniteStateAutomata(re);
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            if (nfa.recognizes(line)) {
                StdOut.println(line);
            }
        }
    }
}
