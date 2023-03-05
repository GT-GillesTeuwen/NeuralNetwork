import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Plot extends JPanel {
    // initialize coordinates
    ArrayList<Double> coordinates = new ArrayList<>();
    double[] cord = { 2, 1.5, 1.3, 1.25, 1.23, 1.11, 1, 0.98, 0.97 };
    int marg = 60;

    protected void paintComponent(Graphics grf) {
        // create instance of the Graphics to use its methods
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D) grf;

        // Sets the value of a single preference for the rendering algorithms.
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // get width and height
        int width = getWidth();
        int height = getHeight();

        // draw graph
        graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
        graph.draw(new Line2D.Double(marg, height - marg, width - marg, height - marg));

        // find value of x and scale to plot points
        double x = (double) (width - 2 * marg) / (coordinates.size() - 1);
        double scale = (double) (height - 2 * marg) / getMax();

        // set color for points
        graph.setPaint(Color.RED);

        // set points to the graph
        for (int i = 0; i < coordinates.size(); i++) {
            double x1 = marg + i * x;
            double y1 = height - marg - scale * coordinates.get(i);
            graph.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 8, 8));
        }
    }

    // create getMax() method to find maximum value
    private double getMax() {
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i) > max)
                max = coordinates.get(i);

        }
        return max;
    }

    public void addValue(double val) {
        coordinates.add(val);
    }

    // main() method start
    public static void main(String args[]) {
        // create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Plot p = new Plot();
        frame.add(p);
        frame.setSize(800, 800);
        frame.setLocation(200, 200);
        frame.setVisible(true);

    }
}
