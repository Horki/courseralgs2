package sedgewick.coursera.week5.bonus;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class Dumps {
    private static void binaryDump(int bitsPerLine) {
        int count = 0;
        for (; !BinaryStdIn.isEmpty(); ++count) {
            if (bitsPerLine == 0) {
                BinaryStdIn.readBoolean();
                continue;
            } else if (count != 0 && count % bitsPerLine == 0) {
                StdOut.println();
            }
            if (BinaryStdIn.readBoolean()) {
                StdOut.print(1);
            } else {
                StdOut.print(0);
            }
        }
        if (bitsPerLine != 0) {
            StdOut.println();
        }
        StdOut.println(count + " bits");
    }

    private static void hexDump(int bytesPerLine) {
        int i = 0;
        for (; !BinaryStdIn.isEmpty(); ++i) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) {
                StdOut.printf("");
            } else if (i % bytesPerLine == 0) {
                StdOut.printf("\n", i);
            } else {
                StdOut.print(" ");
            }
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (bytesPerLine != 0) {
            StdOut.println();
        }
        StdOut.println((i * 8) + " bits");
    }

    private static void pictureDump(int width, int height) {
        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                if (!BinaryStdIn.isEmpty()) {
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) {
                        picture.set(col, row, Color.BLACK);
                    } else {
                        picture.set(col, row, Color.WHITE);
                    }
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }

    // "bin|hex|picture" > abra.txt
    public static void main(String[] args) {
        String dumpType = args[0];
        switch (dumpType) {
            case "bin":
                StdOut.println("Binary dump: 16 bits");
                binaryDump(16);
                break;
            case "hex":
                StdOut.println("Hex dump: 4 bytes");
                hexDump(4);
                break;
            case "picture":
                StdOut.println("Picture dump: 16 width, 6 height");
                pictureDump(16, 6);
                break;
            default:
                throw new IllegalArgumentException("Invalid dump option");
        }
    }
}
