package sedgewick.coursera.week6.bonus_1_linear;

public class Simplex {
    // simplex tableaux
    private final double[][] a;
    // M constraints, N variables
    private final int m;
    private final int n;

    public Simplex(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        a = new double[m + 1][m + n + 1];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = A[i][j];
            }
        }
        for (int j = n; j < m + n; ++j) {
            a[j - n][j] = 1.0;
        }
        for (int j = 0; j < n; ++j) {
            a[m][j] = c[j];
        }
        for (int i = 0; i < m; ++i) {
            a[i][m + n] = b[i];
        }
    }

    private int bland() {
        for (int q = 0; q < m; ++q) {
            if (a[m][q] > 0) {
                // entering column q has positive
                // objective function coefficient optimal
                return q;
            }
        }
        // optimal
        return -1;
    }

    private int minRatioRule(int q) {
        // leaving row
        int p = -1;
        for (int i = 0; i < m; ++i) {
            if (a[i][q] <= 0) {
                continue;
            } else if (p == -1) {
                // consider only positive entries
                p = i;
            } else if ((a[i][m + n] / a[i][q]) < (a[p][m + n] / a[p][q])) {
                // row 'p' has min ratio so fat
                p = i;
            }
        }
        return p;
    }

    private void pivot(int p, int q) {
        for (int i = 0; i <= m; ++i) {
            for (int j = 0; j <= (m + n); ++j) {
                // scale all entries, but row p and column q
                a[i][j] -= a[p][j] * a[i][q] / a[p][q];
            }
        }
        for (int i = 0; i <= m; ++i) {
            if (i != p) {
                // zero out comuln q
                a[i][q] = .0;
            }
        }
        for (int j = 0; j <= (m + n); ++j) {
            if (j != q) {
                // scale row p
                a[p][j] /= a[p][q];
            }
        }
        a[p][q] = 1.0;
    }

    public void solve() {
        while (true) {
            int q = bland();
            if (q == -1) {
                break;
            }
            int p = minRatioRule(q);
            if (p == -1) {
                throw new ArithmeticException("Linear program is unbounded!");
            }
            pivot(p, q);
        }
    }
}