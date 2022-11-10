/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.attempt.pkg1;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gteuw
 */
public class UI2 {

    public UI2() {
        JFrame frame = new JFrame("GUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setMinimumSize(new Dimension(10, 10));
        panel.setMaximumSize(new Dimension(10, 10));
        panel.setLocation(40, 40);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
