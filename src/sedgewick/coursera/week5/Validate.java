package sedgewick.coursera.week5;

import edu.princeton.cs.algs4.StdOut;

public class Validate {
    // "(a|aa)*b" aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaac
    public static void main(String[] args) {
        {
            String regexp = "[$_A-Za-z][$_A-Za-z0-9]*";
            String input = "ident123";
            StdOut.println("regex[" + regexp + "] = input[" + input + "] = " + input.matches(regexp));
        }
        {
            String regexp = "[a-z]+@([a-z]+\\.)+(edu|com)";
            String input = "rs@cs.princeton.edu";
            StdOut.println("regex[" + regexp + "] = input[" + input + "] = " + input.matches(regexp));
        }
        {
            String regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}";
            String input = "166-11-4433";
            StdOut.println("regex[" + regexp + "] = input[" + input + "] = " + input.matches(regexp));
        }
        {
            String regexp = args[0];
            String input = args[1];
            StdOut.println("regex[" + regexp + "] = input[" + input + "] = " + input.matches(regexp));
        }

    }
}
