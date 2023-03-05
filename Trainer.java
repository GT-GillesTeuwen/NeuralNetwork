import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

public class Trainer {

    private TraingingData[] allTrainingData;
    private TraingingData[] validationData;
    private NeuralNetwork nn;

    public Trainer(NeuralNetwork nn, TraingingData[] td, TraingingData[] vd) {
        this.nn = nn;
        this.allTrainingData = td;
        this.validationData = vd;
    }

    public void train() throws IOException {
        int batchSize = 30;

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

        nv.setNeuralNetwork(nn);
        double avgErr = 1;
        int counter = 0;
        while (avgErr > 0) {
            avgErr = 0;
            double[] errI = new double[batchSize];
            double[][] err2 = new double[batchSize][];
            for (int j = 0; j < allTrainingData.length / batchSize; j++) {

                for (int i = 0; i < batchSize; i++) {
                    nv.repaint();
                    TraingingData currentTraingingData = (allTrainingData[j * batchSize + i]);
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
            System.out.println(avgErr / allTrainingData.length);
            errPlot.addValue(avgErr / allTrainingData.length);
            double accuracy = checkAccuracy(nn);
            System.out.println(accuracy);
            accuracyPlot.addValue(accuracy);
            accuracyPlot.repaint();
            errPlot.repaint();
            counter++;
            System.out.println(counter);
            if (counter == 25) {
                nn.writeValues(accuracy, "batchSize" + batchSize + " new set");
                counter = 0;
            }
        }
    }

    public double[] calculateError(double[] output, double[] expected) {
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

    public void shuffleData() {
        List<TraingingData> l = Arrays.asList(allTrainingData);
        Collections.shuffle(l);

        l.toArray(allTrainingData);
    }

    public double checkAccuracy(NeuralNetwork n) throws IOException {
        double correct = 0;
        for (int i = 0; i < validationData.length; i++) {
            TraingingData d = validationData[i];
            n.setInput(d);
            n.forward();

            double[] answers = n.getOutput();
            if (isCorrect(answers, d.getExpected())) {
                correct += 1;
            }
        }
        return correct / validationData.length;

    }

    public boolean isCorrect(double[] answer, double[] expected) {
        return getLargestIndex(answer) == getLargestIndex(expected);
    }

    public int getLargestIndex(double[] d) {
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
