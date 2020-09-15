package sedgewick.coursera.week1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import sedgewick.coursera.week1.abstracts.Paths;

public class MatrixMaze {
    private final int width;
    private final int height;
    private final int vertex;
    private final boolean[][] matrix;
    private final GraphAPI graph;

    public MatrixMaze(boolean[][] maze) {
        matrix = maze;
        width = maze[0].length;
        height = maze.length;
        StdOut.println("w = " + width + ", h = " + height);
        vertex = width * height;
        graph = new GraphAPI(vertex);
        generateGraph();
    }

    public MatrixMaze(In csv, int h, int w) {
        width = w;
        height = h;
        matrix = new boolean[h][w];
        int i = 0, j = 0;
        while (!csv.isEmpty()) {
            String row = csv.readLine();
            for (String item : row.split(",")) {
                matrix[i][j++] = item.equals("0");
            }
            j = 0;
            ++i;
        }
        StdOut.println("w = " + width + ", h = " + height);
        vertex = width * height;
        graph = new GraphAPI(vertex);
        generateGraph();
    }

    private void generateGraph() {
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                if (!matrix[h][w]) {
                    continue;
                }
                // UP
                if (h - 1 >= 0 && matrix[h - 1][w]) {
                    graph.addEdge(idx(h, w), idx(h - 1, w));
                }
                // DOWN
                if (h + 1 < height && matrix[h + 1][w]) {
                    graph.addEdge(idx(h, w), idx(h + 1, w));
                }
                // LEFT
                if (w - 1 >= 0 && matrix[h][w - 1]) {
                    graph.addEdge(idx(h, w), idx(h, w - 1));
                }
                // RIGHT
                if (w + 1 < width && matrix[h][w + 1]) {
                    graph.addEdge(idx(h, w), idx(h, w + 1));
                }
            }
        }
    }

    private int idx(int i, int j) {
        return (i * width) + j;
    }

    private void outputResult(Paths search, int from, int to) {
        if (!search.hasPathTo(to)) {
            StdOut.println("nope");
            return;
        }
        StringBuilder buff = new StringBuilder("");
        // Get walls
        for (int i = 0; i < vertex; ++i) {
            buff.append(matrix[i / width][i % width] ? '0' : '1');
        }
        // get path
        for (int i : search.pathTo(to)) {
            buff.setCharAt(i, '#');
        }
        // from - to
        buff.setCharAt(from, 'S');
        buff.setCharAt(to, 'E');
        for (int i = 0; i < vertex; ++i) {
            StdOut.print(buff.charAt(i));
            if ((i + 1) % width == 0) {
                StdOut.println();
            }
        }
    }

    public void outputResultDfs(int from, int to) {
        validateRow(from);
        validateRow(to);
        Paths search = new DFS(graph, from);
        outputResult(search, from, to);
    }


    public void outputResultBfs(int from, int to) {
        validateRow(from);
        validateRow(to);
        Paths search = new BFS(graph, from);
        outputResult(search, from, to);
    }


    private void validateRow(int x) {
        if (x < 0 || x >= vertex) {
            throw new IllegalArgumentException("Out of bounds");
        }
    }

    public GraphAPI getGraph() {
        return graph;
    }

    // MatrixMaze matrix.csv $(wc -l matrix.csv)
    // rows: $ wc -l matrix.csv
    // cols: $ head -n 1 matrix.csv | awk -F ',' '{print NF}'
    public static void main(String[] args) {
//        boolean[][] matrix = {
//                {true, false, true}, // 0 1 0 | 0 1 2
//                {true, false, true}, // 0 1 0 | 3 4 5
//                {true, true, true},  // 0 0 0 | 6 7 8
//        };
//        MatrixMaze m = new MatrixMaze(matrix);
        In csv = new In(args[0]);
        int rows = Integer.parseInt(args[1]);
        int cols = Integer.parseInt(args[2]);
        MatrixMaze m = new MatrixMaze(csv, rows, cols);
        GraphAPI g = m.getGraph();
        StdOut.println("greph = " + g);
        int from = 0;
        int to = rows * cols - 1;
        {
            StdOut.println("DFS:");
            Paths dfs = new DFS(g, from);
            for (int v = 0; v < g.V(); v++) {
                StdOut.print(from + " to " + v + ": ");
                if (dfs.hasPathTo(v)) {
                    for (int x : dfs.pathTo(v)) {
                        if (x == from) {
                            StdOut.print(x);
                        } else {
                            StdOut.print("-" + x);
                        }
                    }
                }
                StdOut.println();
            }
        }
        {
            StdOut.println("BFS:");
            Paths bfs = new BFS(g, from);
            for (int v = 0; v < g.V(); v++) {
                StdOut.print(from + " to " + v + ": ");
                if (bfs.hasPathTo(v)) {
                    for (int x : bfs.pathTo(v)) {
                        if (x == from) {
                            StdOut.print(x);
                        } else {
                            StdOut.print("-" + x);
                        }
                    }
                }
                StdOut.println();
            }
        }

        StdOut.println("DFS:");
        m.outputResultDfs(from, to);
        StdOut.println("BFS:");
        m.outputResultBfs(from, to);
    }
}
