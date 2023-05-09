public class Matrix {
    double[][] values;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        values = new double[rows][cols];

    }

    public void randInit() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = ((Math.random() * (1000)) - 500) / 1000;
            }
        }
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public double getValueAt(int row, int col) {
        return values[row][col];
    }

    public void setValueAt(int row, int col, double val) {
        values[row][col] = val;
    }

    public void setInputValues(double[] in) {
        if (this.cols > 1) {
            System.out.println("Cannot set input values when matrix has multiple rows");
            return;
        }

        if (this.rows != in.length) {
            System.out.println("Cannot set input values when lengths mismatch");
            return;
        }

        for (int i = 0; i < in.length; i++) {
            values[i][0] = in[i];
        }
    }

    public double[] getOutputValues() {
        if (cols > 1) {
            System.out.println("Can't get output values for matrix with more than one column");
            return null;
        }

        double[] output = new double[this.rows];

        for (int i = 0; i < output.length; i++) {
            output[i] = values[i][0];
        }
        return output;
    }

    public void applyActivation(String activation) {
        if (activation == "sigmoid") {
            sigmoid();
        } else if (activation == "relu") {
            relu();
        }
    }

    public Matrix deriveActivation(String activation) {
        if (activation == "sigmoid") {
            return sigmoidPrime();
        } else if (activation == "relu") {
            return reluPrime();
        }
        return null;
    }

    private void sigmoid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = sigmoidFunction(values[i][j]);
            }
        }
    }

    private void relu() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = sigmoidFunction(values[i][j]);
            }
        }
    }

    private Matrix reluPrime() {
        Matrix prime = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (values[i][j] > 0) {
                    prime.setValueAt(i, j, 1);
                } else {
                    prime.setValueAt(i, j, 0);
                }

            }
        }
        return prime;

    }

    private Matrix sigmoidPrime() {
        Matrix prime = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prime.setValueAt(i, j, (values[i][j] * (1 - values[i][j])));
            }
        }
        return prime;

    }

    public void transpose() {
        double[][] tVals = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tVals[j][i] = values[i][j];
            }
        }
        int temp = rows;
        rows = cols;
        cols = temp;
        this.values = tVals;
    }

    public Matrix transpose(boolean b) {
        Matrix m = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.setValueAt(j, i, values[i][j]);
            }
        }
        return m;
    }

    private double sigmoidFunction(double input) {
        return 1 / (1 + Math.pow(Math.E, (-1 * input)));
    }

    private double reluFunction(double input) {
        if (input < 0) {
            return 0;
        } else {
            return input;
        }
    }

    public void add(Matrix m) {
        if (m.rows != this.rows) {
            System.out.println("Cannot add matrices with differing row number\t this matrix has " + this.rows
                    + " rows, the input matrix has " + m.rows + " rows.");
            return;
        }

        if (m.cols != this.cols) {
            System.out.println("Cannot add matrices with differing column number\t this matrix has " + this.cols
                    + " cols, the input matrix has " + m.cols + " cols.");
            return;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] += m.getValueAt(i, j);
            }
        }
    }

    public Matrix multiply(Matrix m) {
        if (this.cols != m.rows) {
            System.out.println("Cannot multiply matrices with differing inner numbers");
            return null;
        }

        Matrix newMat = new Matrix(this.rows, m.cols);

        for (int i = 0; i < newMat.rows; i++) {
            for (int j = 0; j < newMat.cols; j++) {
                double sum = 0;
                for (int j2 = 0; j2 < this.cols; j2++) {
                    sum += values[i][j2] * m.getValueAt(j2, j);
                }
                newMat.setValueAt(i, j, sum);
            }
        }

        return newMat;
    }

    public void multiply(double d) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] *= d;
            }
        }

    }

    public void hadamardProduct(Matrix m) {
        if (m.rows != this.rows) {
            System.out.println(
                    "Cannot compute the hadamard product of matrices with differing row number\t this matrix has "
                            + this.rows
                            + " rows, the input matrix has " + m.rows + " rows.");
            return;
        }

        if (m.cols != this.cols) {
            System.out.println(
                    "Cannot compute the hadamard product of matrices with differing row number\\t this matrix has "
                            + this.cols
                            + " cols, the input matrix has " + m.cols + " cols.");
            return;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] *= m.getValueAt(i, j);
            }
        }
    }

    public void set0() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = 0;
            }
        }
    }

    private double numeratorOfsoftmaxFunction(double input) {
        return Math.exp(input);
    }

    public void softmax() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = (values[i][j]);
                sum += values[i][j];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                values[i][j] = values[i][j] / sum;
            }
        }

    }
}
