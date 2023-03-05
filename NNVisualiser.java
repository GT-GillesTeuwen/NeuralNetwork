import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class NNVisualiser extends JPanel {

    NeuralNetwork nn = null;
    double[] cord = { 2, 1.5, 1.3, 1.25, 1.23, 1.11, 1, 0.98, 0.97 };
    int marg = 100;

    protected void paintComponent(Graphics grf) {
        if (nn == null) {
            return;
        }
        this.setBackground(Color.black);
        // create instance of the Graphics to use its methods
        super.paintComponent(grf);
        Graphics2D plane = (Graphics2D) grf;

        // Sets the value of a single preference for the rendering algorithms.
        plane.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // get width and height
        int width = getWidth();
        int height = getHeight();

        // find value of x and scale to plot points
        double x = (double) (width - 2 * marg) / (nn.numLayers - 1);
        double scale = (double) (height - 2 * marg) / getMax();

        // set color for points
        plane.setPaint(Color.RED);

        // set points to the graph

        for (int i = 0; i < nn.numLayers; i++) {
            double x1 = (marg) + (i * (((width - 2 * marg) * 1.0) / (nn.numLayers - 1)));
            for (int j = 0; j < nn.activations[i].getRows(); j++) {
                double y1 = (marg) + (j * (((height - 2 * marg) * 1.0) / (nn.activations[i].getRows() - 1)));

                double colour = nn.activations[i].getValueAt(j, 0);
                if (colour < 0) {
                    colour *= -1;
                }
                if (colour > 1) {
                    colour = 1;
                }
                plane.setPaint(new ColorUIResource((int) (colour * 255), (int) (colour * 255), (int) (colour * 255)));
                plane.fill(new Ellipse2D.Double(x1, y1, 15, 15));
                plane.setPaint(Color.white);
                plane.setStroke(new BasicStroke(2.0f));
                plane.draw(new Ellipse2D.Double(x1, y1, 15, 15));
            }
        }
        plane.setStroke(new BasicStroke(1.0f));

        for (int i = 1; i < nn.numLayers - 1; i++) {
            double x1 = (marg) + (i * (((width - 2 * marg) * 1.0) / (nn.numLayers - 1))) + 15;
            for (int j = 0; j < nn.weights[i].getRows(); j++) {
                double y2 = (marg) + (j * (((height - 2 * marg) * 1.0) / (nn.weights[i].getRows() - 1))) + 7.5;
                for (int k = 0; k < nn.weights[i].getCols(); k++) {
                    double colour = nn.weights[i].getValueAt(j, k);
                    double red = 0;
                    double green = 0;
                    if (colour < 0) {
                        colour *= -1;
                        red = colour;
                    } else {
                        green = colour;
                    }
                    if (red > 1) {
                        red = 1;
                    }
                    if (green > 1) {
                        green = 1;
                    }
                    plane.setPaint(
                            new ColorUIResource((int) (red * 255), (int) (green * 255), (int) (0 * 255)));
                    double y1 = (marg) + (k * (((height - 2 * marg) * 1.0) / (nn.weights[i].getCols() - 1))) + 7.5;
                    for (int l = 0; l < cord.length; l++) {
                        double x2 = (marg) + ((i + 1) * (((width - 2 * marg) * 1.0) / (nn.numLayers - 1)));
                        plane.draw(new Line2D.Double(x1, y1, x2, y2));
                    }

                }

            }
        }

    }

    // create getMax() method to find maximum value
    private double getMax() {
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < cord.length; i++) {
            if (cord[i] > max)
                max = cord[i];

        }
        return max;
    }

    public void setNeuralNetwork(NeuralNetwork nn) {
        this.nn = nn;
    }

}