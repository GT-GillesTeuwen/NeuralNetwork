import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

class Main {

    private static MnistMatrix[] mnistMatrix;

    public static void main(String[] args) throws IOException {

        int batchSize = 300;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocation(200, 200);
        frame.setVisible(true);

        NNVisualiser nv = new NNVisualiser();
        frame.add(nv);
        nv.repaint();

        JFrame avgErrorFrame = new JFrame();
        // set size, layout and location for frame.
        avgErrorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Plot errPlot = new Plot();
        avgErrorFrame.add(errPlot);
        avgErrorFrame.setSize(800, 800);
        avgErrorFrame.setLocation(200, 200);
        avgErrorFrame.setVisible(true);
        errPlot.addValue(0);

        JFrame accuracyFrame = new JFrame();
        // set size, layout and location for frame.
        accuracyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Plot accuracyPlot = new Plot();
        accuracyFrame.add(accuracyPlot);
        accuracyFrame.setSize(800, 800);
        accuracyFrame.setLocation(200, 200);
        accuracyFrame.setVisible(true);
        accuracyPlot.addValue(1);

        createTrainingData();

        int[] numNodesInLayers = { 784, 16, 16, 10 };

        NeuralNetwork nn = new NeuralNetwork(numNodesInLayers, 1, "sigmoid");
        // nn.loadValues("NNValues_DigitID_0.8049_0.3.txt");
        nv.setNeuralNetwork(nn);
        double avgErr = 1;
        int counter = 0;
        while (avgErr > 0) {
            avgErr = 0;
            double[] errI = new double[batchSize];
            double[][] err2 = new double[batchSize][];
            for (int j = 0; j < mnistMatrix.length / batchSize; j++) {

                for (int i = 0; i < batchSize; i++) {
                    nv.repaint();
                    TraingingData currentTraingingData = createTrainingDataFromMnistMatrix(
                            mnistMatrix[j * batchSize + i]);
                    nn.setInput(currentTraingingData);
                    nn.forward();
                    nv.repaint();
                    double[] output = nn.getOutput();
                    // for (int i = 0; i < output.length; i++) {
                    // System.out.println(i + " [" + output[i] + "]");
                    // }
                    // System.out.println(currentTraingingData.getAns());
                    double[] err = (calculateError(output, currentTraingingData.getExpected()));
                    double sum = 0;
                    for (int k = 0; k < err.length; k++) {
                        sum += err[k];
                    }
                    avgErr += sum;
                    err2[i] = err;
                    errI[i] = sum;
                    nn.backward(err);
                }
                nv.repaint();
                nn.adjust();
                nv.repaint();
            }
            shuffleData();
            System.out.println(avgErr / mnistMatrix.length);
            errPlot.addValue(avgErr / mnistMatrix.length);
            double accuracy = checkAccuracy(nn);
            System.out.println(accuracy);
            accuracyPlot.addValue(accuracy);
            accuracyPlot.repaint();
            errPlot.repaint();
            counter++;
            System.out.println(counter);
            if (counter == 25) {
                // nn.writeValues(accuracy, "batchSize" + batchSize);
                counter = 0;
            }
        }
    }

    public static double[] calculateError(double[] output, double[] expected) {
        double[] error = new double[output.length];

        for (int i = 0; i < error.length; i++) {
            error[i] = Math.pow(expected[i] - output[i], 2);
        }
        double sum = 0;
        for (int i = 0; i < error.length; i++) {
            sum += error[i];
        }
        // System.out.println(sum);
        return error;

    }

    public static void createTrainingData() {
        try {
            mnistMatrix = new MnistDataReader().readData("data\\train-images.idx3-ubyte",
                    "data\\train-labels.idx1-ubyte");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TraingingData createTrainingDataFromMnistMatrix(MnistMatrix m) {
        double[] input = new double[m.getNumberOfRows() * m.getNumberOfColumns()];
        for (int r = 0; r < m.getNumberOfRows(); r++) {
            for (int c = 0; c < m.getNumberOfColumns(); c++) {
                input[r * m.getNumberOfColumns() + c] = (m.getValue(r, c));
            }
        }

        return new TraingingData(input, (m.getLabel() + "").charAt(0));
    }

    public static void shuffleData() {
        List<MnistMatrix> l = Arrays.asList(mnistMatrix);
        Collections.shuffle(l);

        l.toArray(mnistMatrix);
    }

    public static double checkAccuracy(NeuralNetwork n) throws IOException {
        MnistMatrix[] mat = new MnistDataReader().readData("data\\t10k-images.idx3-ubyte",
                "data\\t10k-labels.idx1-ubyte");
        double correct = 0;
        for (int i = 0; i < mat.length; i++) {
            TraingingData d = createTrainingDataFromMnistMatrix(mnistMatrix[i]);
            n.setInput(d);
            n.forward();

            double[] answers = n.getOutput();
            if (isCorrect(answers, d.getExpected())) {
                correct += 1;
            }
        }
        return correct / mat.length;

    }

    public static boolean isCorrect(double[] answer, double[] expected) {
        return getLargestIndex(answer) == getLargestIndex(expected);
    }

    public static int getLargestIndex(double[] d) {
        int ret = 0;
        double max = -1;
        for (int i = 0; i < d.length; i++) {
            if (d[i] > max) {
                max = d[i];
                ret = i;
            }

        }
        return ret;
    }

}