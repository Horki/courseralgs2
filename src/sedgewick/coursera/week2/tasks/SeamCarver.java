package sedgewick.coursera.week2.tasks;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;

public class SeamCarver {
    private Picture picture;
    // We define the energy of a pixel at the border of the image to be 1000,
    // so that it is strictly larger than the energy of any interior pixel.
    private static final double BORDER_ENERGY = 1000.;
    private static final int RED_IDX = 255 * 65536;

    private class RGB {
        // TODO: figure out how to add (0, 255) range!
        private final int red;
        private final int green;
        private final int blue;

        public RGB(int idx) {
            red = (idx >> 16) & 0XFF;
            green = (idx >> 8) & 0XFF;
            blue = (idx >> 0) & 0XFF;
        }

        public String toString() {
            return String.format("(%3d, %3d, %3d)", red, green, blue);
        }
    }

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (null == picture) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int col, int row) {
        validateColumn(col);
        validateRow(row);
        if (col == 0 || col == width() - 1 || row == 0 || row == height() - 1) {
            return BORDER_ENERGY;
        }
        validateColumn(col - 1);
        validateColumn(col + 1);
        validateRow(row - 1);
        validateRow(row + 1);

        // Up
        RGB rgbUp = new RGB(picture.getRGB(col - 1, row));
        // Down
        RGB rgbDown = new RGB(picture.getRGB(col + 1, row));
        // Left
        RGB rgbLeft = new RGB(picture.getRGB(col, row - 1));
        // Right
        RGB rgbRight = new RGB(picture.getRGB(col, row + 1));

        double gradRx = rgbUp.red - rgbDown.red;
        double gradGx = rgbUp.green - rgbDown.green;
        double gradBx = rgbUp.blue - rgbDown.blue;

        double gradRy = rgbLeft.red - rgbRight.red;
        double gradGy = rgbLeft.green - rgbRight.green;
        double gradBy = rgbLeft.blue - rgbRight.blue;

        return Math.sqrt(gradRx * gradRx + gradGx * gradGx + gradBx * gradBx +
                gradRy * gradRy + gradGy * gradGy + gradBy * gradBy);
    }

    private void validateColumn(int col) {
        if (col < 0 || col > width()) {
            throw new IllegalArgumentException("Column pixel is out of bounds");
        }
    }

    private void validateRow(int row) {
        if (row < 0 || row > height()) {
            throw new IllegalArgumentException("Row pixel is out of bounds");
        }
    }

    public void outputEnergy() {
        for (int i = 0; i < width() * height(); ++i) {
            StdOut.printf("%7.02f ", energy(i % width(), i / width()));
            if (i % width() == 0) {
                StdOut.println();
            }
        }
    }

    public void outputRgb() {
        for (int i = 0; i < width() * height(); ++i) {
            StdOut.printf("%7d ", picture.getRGB(i % width(), i / width()));
            if (i % width() == 0) {
                StdOut.println();
            }
        }
    }

    public void outputAsRgb() {
        for (int i = 0; i < width() * height(); ++i) {
            StdOut.printf("%s ", new RGB(picture.getRGB(i % width(), i / width())));
            if (i % width() == 0) {
                StdOut.println();
            }
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transposeRgb();
        int[] seam = findVerticalSeam();
        transposeRgb();
        return seam;
    }

    private void transposeRgb() {
        // swap width and height for new image
        Picture transpose = new Picture(height(), width());
        for (int i = 0; i < width() * height(); ++i) {
            transpose.setRGB(i / width(), i % width(), picture.getRGB(i % width(), i / width()));
        }
        picture = transpose;
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (null == seam) {
            throw new IllegalArgumentException("Remove Horizontal seam: NULL");
        }
        transposeRgb();
        removeVerticalSeam(seam);
        transposeRgb();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[] distTo = new double[height() * width()];
        int[] edgeTo = new int[height() * width()];
        double[] energy = new double[height() * width()];

        for (int idx = 0; idx < height() * width(); ++idx) {
            int row = idx / width();
            int col = idx % width();
            energy[idx] = energy(col, row);
            distTo[idx] = row == 0 ? 0 : Double.POSITIVE_INFINITY;
            edgeTo[idx] = -1;
        }

        // Topological order
        // Similar as in Acyclic SP
        for (int row = 0; row < height() - 1; ++row) {
            for (int col = 0; col < width(); ++col) {
                int nextRow = row + 1;
                for (int nextCol = col - 1; nextCol <= col + 1; nextCol++) {
                    if (nextCol >= 0 && nextCol < width()) {
                        double weight = energy[nextRow * width() + nextCol];
                        if (distTo[nextRow * width() + nextCol] > distTo[row * width() + col] + weight) {
                            distTo[nextRow * width() + nextCol] = distTo[row * width() + col] + weight;
                            edgeTo[nextRow * width() + nextCol] = col;
                        }
                    }
                }
            }
        }

        int[] verticalSeam = new int[height()];

        double min = Double.POSITIVE_INFINITY;
        int lowCol = -1;
        for (int col = 0; col < width(); col++) {
            if (min > distTo[(height() - 1) * width() + col]) {
                lowCol = col;
                min = distTo[(height() - 1) * width() + col];
            }
        }
        for (int row = height() - 1; row >= 0; --row) {
            verticalSeam[row] = lowCol;
            lowCol = edgeTo[row * width() + lowCol];
        }

        return verticalSeam;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (null == seam) {
            throw new IllegalArgumentException("Remove Vertical seam: NULL");
        }
        if (width() <= 1) {
            throw new IllegalArgumentException("insufficient picture width");
        }

        Picture trimmed = new Picture(width() - 1, height());
        for (int row = 0; row < height(); ++row) {
            int trimColumn = seam[row];
            for (int col = 0; col < trimColumn; ++col) {
                trimmed.setRGB(col, row, picture.getRGB(col, row));
            }
            for (int col = trimColumn + 1; col < width(); ++col) {
                trimmed.setRGB(col - 1, row, picture.getRGB(col, row));
            }
        }
        picture = trimmed;
    }

    // Color Vertical buffer that is going to be deleted
    public void setVerticalBuffer(int[] verticalSeam) {
        if (null == verticalSeam) {
            throw new IllegalArgumentException("Set Vertical Buffer seam: NULL");
        }
        if (verticalSeam.length != height()) {
            throw new IllegalArgumentException("Vertical Buffer is not good");
        }
        for (int r = 0; r < height(); ++r) {
            picture.setRGB(verticalSeam[r], r, RED_IDX);
        }
    }

    // Color Horizontal buffer that is going to be deleted
    public void setHorizontalBuffer(int[] horizontalSeam) {
        if (null == horizontalSeam) {
            throw new IllegalArgumentException("Set Vertical Buffer seam: NULL");
        }
        if (horizontalSeam.length != width()) {
            throw new IllegalArgumentException("Horizontal Buffer is not good");
        }

        for (int c = 0; c < width(); ++c) {
            picture.setRGB(c, horizontalSeam[c], RED_IDX);
        }
    }


    public static void main(String[] args) {
        File file = new File(args[0]);
        Picture picture = new Picture(file);
        SeamCarver seamCarver = new SeamCarver(picture);

        {
            int[] verticalSeam = seamCarver.findVerticalSeam();
            seamCarver.setVerticalBuffer(verticalSeam);
            Picture result = seamCarver.picture();
            result.save(args[1]);
            StdOut.println("Check result, red buffer vertical");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            seamCarver.removeVerticalSeam(verticalSeam);
            Picture picture1 = seamCarver.picture();
            picture1.save(args[1]);
            StdOut.println("Check result, removed vertical");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        }
        {
            int[] horizontalSeam = seamCarver.findHorizontalSeam();
            seamCarver.setHorizontalBuffer(horizontalSeam);
            Picture result = seamCarver.picture();
            result.save(args[1]);
            StdOut.println("Check result, red buffer horizontal");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            seamCarver.removeHorizontalSeam(horizontalSeam);
            Picture picture1 = seamCarver.picture();
            picture1.save(args[1]);
            StdOut.println("Check result, removed horizontal");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        }


    }
}

