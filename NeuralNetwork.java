import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NeuralNetwork {
    private double LEARNING_RATE = 0.05;
    int numLayers;
    int[] numNodesInLayers;
    Matrix[] weights;
    Matrix[] biases;
    Matrix[] activations;

    Matrix adjustL3;
    Matrix adjustL2;
    Matrix adjustL1;

    Matrix adjustB3;
    Matrix adjustB2;
    Matrix adjustB1;

    String activationFunction;

    int ttlBack = 0;

    public NeuralNetwork(int[] numNodesInLayers, double lr, String activationFunction) {
        LEARNING_RATE = lr;
        numLayers = numNodesInLayers.length;
        this.numNodesInLayers = numNodesInLayers;
        weights = new Matrix[numLayers - 1];
        biases = new Matrix[numLayers - 1];
        activations = new Matrix[numLayers];

        this.activationFunction = activationFunction;

        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Matrix(numNodesInLayers[i + 1], numNodesInLayers[i]);
            weights[i].randInit();
        }

        for (int i = 0; i < biases.length; i++) {
            biases[i] = new Matrix(numNodesInLayers[i + 1], 1);
            biases[i].randInit();
        }

        for (int i = 0; i < activations.length; i++) {
            activations[i] = new Matrix(numNodesInLayers[i], 1);
        }

        adjustL3 = new Matrix(weights[numLayers - 2].getRows(), weights[numLayers - 2].getCols());
        adjustL3.set0();
        adjustL2 = new Matrix(weights[numLayers - 3].getRows(), weights[numLayers - 3].getCols());
        adjustL2.set0();
        adjustL1 = new Matrix(weights[numLayers - 4].getRows(), weights[numLayers - 4].getCols());
        adjustL1.set0();

        adjustB3 = new Matrix(biases[numLayers - 2].getRows(), biases[numLayers - 2].getCols());
        adjustB2 = new Matrix(biases[numLayers - 3].getRows(), biases[numLayers - 3].getCols());
        adjustB1 = new Matrix(biases[numLayers - 4].getRows(), biases[numLayers - 4].getCols());
        adjustB3.set0();
        adjustB2.set0();
        adjustB1.set0();
    }

    public void setInput(TraingingData td) {
        activations[0].setInputValues(td.getInputValues());
    }

    public void setInput(double[] d) {
        activations[0].setInputValues(d);
    }

    public void forward() {
        activations[0].applyActivation(activationFunction);
        for (int i = 1; i < activations.length; i++) {
            activations[i] = weights[i - 1].multiply(activations[i - 1]);
            activations[i].add(biases[i - 1]);
            activations[i].applyActivation(activationFunction);
        }

    }

    public void backward(double[] expected) {
        // BackwardThread[] backwardThreads = new BackwardThread[expected.length];
        // for (int i = 0; i < expected.length; i++) {
        // backwardThreads[i] = new BackwardThread(expected[i], this);
        // }

        // for (int i = 0; i < backwardThreads.length; i++) {
        // backwardThreads[i].start();
        // }
        // boolean busy = true;
        // while (busy) {
        // busy = false;
        // for (int i = 0; i < backwardThreads.length; i++) {
        // if (backwardThreads[i].isAlive()) {
        // busy = true;
        // }
        // }
        // }

        // Adjustments for L3

        //// Delta L3
        Matrix expectedMat = new Matrix(expected.length, 1);
        expectedMat.setInputValues(expected);
        Matrix deltaL3 = new Matrix(expected.length, 1);
        double[] activationArr = activations[numLayers - 1].getOutputValues();
        deltaL3.setInputValues(activationArr);
        expectedMat.multiply(-1);
        deltaL3.add(expectedMat);
        Matrix derActivation = activations[numLayers - 1].deriveActivation(activationFunction);
        deltaL3.hadamardProduct(derActivation);
        //// Activation L3
        activationArr = activations[numLayers - 2].getOutputValues();
        Matrix activationMat = new Matrix(activationArr.length, 1);
        activationMat.setInputValues(activationArr);
        activationMat.transpose();
        Matrix curL3 = deltaL3.multiply(activationMat);

        // Adjustments for L2

        //// Delta L2
        Matrix tempWeights = weights[numLayers - 2].transpose(true);
        Matrix deltaL2 = tempWeights.multiply(deltaL3);
        derActivation = activations[numLayers - 2].deriveActivation(activationFunction);
        deltaL2.hadamardProduct(derActivation);
        //// Activation L2
        activationArr = activations[numLayers - 3].getOutputValues();
        activationMat = new Matrix(activationArr.length, 1);
        activationMat.setInputValues(activationArr);
        activationMat.transpose();
        Matrix curL2 = deltaL2.multiply(activationMat);

        // Adjustments for L1

        //// Delta L1
        tempWeights = weights[numLayers - 3].transpose(true);
        Matrix deltaL1 = tempWeights.multiply(deltaL2);
        derActivation = activations[numLayers - 3].deriveActivation(activationFunction);
        deltaL1.hadamardProduct(derActivation);
        //// Activation L1
        activationArr = activations[numLayers - 4].getOutputValues();
        activationMat = new Matrix(activationArr.length, 1);
        activationMat.setInputValues(activationArr);
        activationMat.transpose();
        Matrix curL1 = deltaL1.multiply(activationMat);

        adjustL3.add(curL3);
        adjustL2.add(curL2);
        adjustL1.add(curL1);

        adjustB3.add(deltaL3);
        adjustB2.add(deltaL2);
        adjustB1.add(deltaL1);

        ttlBack++;
    }

    public void adjust() {
        adjustL3.multiply((1.0 / ttlBack));
        adjustL3.multiply(-1 * LEARNING_RATE);
        weights[numLayers - 2].add(adjustL3);
        adjustL3.set0();

        adjustL2.multiply((1.0 / ttlBack));
        adjustL2.multiply(-1 * LEARNING_RATE);
        weights[numLayers - 3].add(adjustL2);
        adjustL2.set0();

        adjustL1.multiply((1.0 / ttlBack));
        adjustL1.multiply(-1 * LEARNING_RATE);
        weights[numLayers - 4].add(adjustL1);
        adjustL1.set0();

        adjustB3.multiply((1.0 / ttlBack));
        adjustB3.multiply(-1 * LEARNING_RATE);
        biases[numLayers - 2].add(adjustB3);
        adjustB3.set0();

        adjustB2.multiply((1.0 / ttlBack));
        adjustB2.multiply(-1 * LEARNING_RATE);
        biases[numLayers - 3].add(adjustB2);
        adjustB2.set0();

        adjustB1.multiply((1.0 / ttlBack));
        adjustB1.multiply(-1 * LEARNING_RATE);
        biases[numLayers - 4].add(adjustB1);
        adjustB1.set0();

        ttlBack = 0;
    }

    public double[] getOutput() {
        return activations[numLayers - 1].getOutputValues();
    }

    public void loadValues(String fileName) {
        try {
            Scanner file = new Scanner(new FileReader(fileName));
            String next = file.nextLine();
            next = file.nextLine();
            // String[] nums = next.split(",");

            for (int k = 0; k < 3; k++) {
                next = file.nextLine();
                next = file.nextLine();
                String[] vals = next.split(",");
                for (int i = 0; i < vals.length; i++) {
                    System.out.println((i / weights[k].getCols()) + ":" + (i % weights[k].getCols()));
                    weights[k].setValueAt(i / weights[k].getCols(), i % weights[k].getCols(),
                            Double.parseDouble(vals[i]));
                }
            }

            for (int k = 0; k < 3; k++) {
                next = file.nextLine();
                next = file.nextLine();
                String[] vals = next.split(",");
                for (int i = 0; i < vals.length; i++) {
                    biases[k].setValueAt(i, 0, Double.parseDouble(vals[i]));
                }
            }
            System.out.println();
        } catch (FileNotFoundException e) {

        }
    }

    public void writeValues(double acc, String name) {
        try {
            FileWriter myWriter = new FileWriter(
                    "values\\NNValues_DigitID_" + acc + "_" + LEARNING_RATE + "_" + activationFunction + "_" + name
                            + ".txt");
            String out = "Num nodes per layer\n";
            for (int i = 0; i < numNodesInLayers.length; i++) {
                out += numNodesInLayers[i] + ",";
            }
            out += "\n";
            for (int i = 0; i < weights.length; i++) {
                out += "Weights at " + i + "\n";
                for (int j = 0; j < weights[i].getRows(); j++) {
                    for (int k = 0; k < weights[i].getCols(); k++) {
                        out += weights[i].getValueAt(j, k) + ",";
                    }
                }
                out += ("\n");
            }

            for (int i = 0; i < biases.length; i++) {
                out += "Biases at " + i + "\n";
                for (int j = 0; j < biases[i].getRows(); j++) {
                    for (int k = 0; k < biases[i].getCols(); k++) {
                        out += biases[i].getValueAt(j, k) + ",";
                    }
                }
                out += ("\n");
            }

            myWriter.write(out);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private int getLargestIndex(double[] d) {
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

    public int getAnswer() {
        return getLargestIndex(getOutput());
    }
}
