package sedgewick.coursera.week4.tasks;

public class BoggleBoard {
    private int rows;
    private int cols;

    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        //
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        //
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        //
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        //
    }

    // Returns the number of rows.
    public int rows() {
        return rows;
    }

    // Returns the number of columns.
    public int cols() {
        return cols;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        return 'q';
    }

    // Returns a string representation of the board.
    public String toString() {
        return null;
    }
}
