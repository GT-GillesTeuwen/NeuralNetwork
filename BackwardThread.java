public class BackwardThread extends Thread {

    private double[] expected;
    private volatile NeuralNetwork n;

    Matrix[] mat;

    public BackwardThread(double[] expected, NeuralNetwork n) {
        this.expected = expected;
        this.n = n;
    }

    @Override
    public void run() {
        // mat = new Matrix[6];
        // int numLayers = n.numLayers;

        // mat[0] = curL3;
        // mat[1] = curL2;
        // mat[2] = curL1;
        // mat[3] = deltaL3;
        // mat[4] = deltaL2;
        // mat[5] = deltaL1;
    }

    public Matrix[] getMat() {
        return mat;
    }
}
