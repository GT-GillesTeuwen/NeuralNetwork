import javax.swing.JFrame;

public class VisualTest {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocation(200, 200);
        frame.setVisible(true);

        NNVisualiser n = new NNVisualiser();
        frame.add(n);
        n.repaint();
        int[] numNodesInLayers = { 784, 16, 16, 10 };
        NeuralNetwork nn = new NeuralNetwork(numNodesInLayers, 0, "sigmoid");
        nn.loadValues("values\\NNValues_DigitID_0.9711_1.0_sigmoid_batchSize30.txt");
        n.setNeuralNetwork(nn);
        n.repaint();

    }
}
