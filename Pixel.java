import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

public class Pixel extends JPanel {
    private int x;
    private int y;
    private int b;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
        int b = 0;
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }

    public void increase() {
        if (b < 200) {
            b += 50;
            this.setBackground(new ColorUIResource(b, b, b));
        }

    }

    public void paint() {
        this.setBackground(new ColorUIResource(255, 255, 255));
        b = 255;
    }

    public void unpaint() {
        this.setBackground(new ColorUIResource(0, 0, 0));
        b = 0;
    }
}
