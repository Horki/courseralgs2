package sedgewick.coursera.week5.tasks;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // TODO: implement
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // TODO: implement
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        } else {
            throw new IllegalArgumentException("Illegal command line argument");
        }

    }

}
