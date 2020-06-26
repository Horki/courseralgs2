package sedgewick.coursera.week4.bonus;

import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week4.bonus.interfaces.SubString;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp implements SubString {
    private final long patternHash;
    // pattern length
    private final int M;
    // Modulus
    private final long Q;
    // Radix
    private final int R;
    // R^(M-1)/Q
    private long RM;

    public RabinKarp(String pattern) {
        M = pattern.length();
        R = 256;
        Q = longRandomPrime();
        RM = 1;
        for (int i = 1; i <= M - 1; ++i) {
            RM = (R * RM) % Q;
        }
        patternHash = hash(pattern);
    }

    private long hash(String key) {
        long h = 0;
        for (int j = 0; j < M; ++j) {
            h = (R * h + key.charAt(j)) % Q;
        }
        return h;
    }

    // Large Prime, without overflow
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    // Monte Carlo version
    public int search(String text) {
        int N = text.length();
        long textHash = hash(text);
        if (patternHash == textHash) {
            return 0;
        }
        for (int i = M; i < N; ++i) {
            textHash = (textHash + Q - RM * text.charAt(i - M) % Q) % Q;
            textHash = (textHash * R + text.charAt(i)) % Q;
            if (patternHash == textHash) {
                // Found
                return i - M + 1;
            }
        }
        // not found
        return N;
    }

    public static void main(String[] args) {
        String text = "AABRAACADABRAACAADABRA";
        int N = text.length();
        String pattern = "AACAA";
        StdOut.println("text len: " + N);
        SubString subString = new RabinKarp(pattern);
        StdOut.println(subString.search(text));
    }
}
