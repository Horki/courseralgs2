package sedgewick.coursera.week5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Harvester {
    // "gcg(cgg|agg)*ctg" chromosomeX.txt
    public static void main(String[] args) {
        String regexp = args[0];
        In in = new In(args[1]);
        String input = in.readAll().replace("\n", "");
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            StdOut.println(matcher.group());
        }
    }
}
