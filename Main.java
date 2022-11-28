import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

class Main {

    private static MnistMatrix[] mnistMatrix;

    public static void main(String[] args) throws IOException {
        int[] numNodesInLayers = { 784, 16, 16, 10 };
        NeuralNetwork nn = new NeuralNetwork(numNodesInLayers, 1, "sigmoid");
        nn.loadValues("values\\NNValues_DigitID_0.9843_1.0_sigmoid_batchSize30.txt");

        Trainer t = new Trainer(nn, createTrainingData("data\\train-images.idx3-ubyte",
                "data\\train-labels.idx1-ubyte"),
                createTrainingData("data\\t10k-images.idx3-ubyte",
                        "data\\t10k-labels.idx1-ubyte"));
        t.train();
    }


    public static TraingingData[] createTrainingData(String dataFile, String labelFile) {
        try {
            MnistMatrix[] mnistMatrix = new MnistDataReader().readData(dataFile, labelFile);
            TraingingData[] allTrainingData = new TraingingData[mnistMatrix.length];
            for (int i = 0; i < mnistMatrix.length; i++) {
                allTrainingData[i] = createTrainingDataFromMnistMatrix(mnistMatrix[i]);
            }
            return allTrainingData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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