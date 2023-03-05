
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author gteuw
 */
public class UI extends javax.swing.JFrame implements MouseMotionListener, MouseListener {
    private static MnistMatrix[] mnistMatrix;
    /**
     * Creates new form UI
     */
    final int ROWS = 28;
    final int COLS = 28;
    final int PIXEL_SIZE = 12;
    boolean down = false;
    Pixel[][] pixels = new Pixel[ROWS][COLS];
    JRadioButton[] btns = new JRadioButton[11];

    NeuralNetwork nn;

    public UI() {

        int[] numNodesInLayers = { 784, 16, 16, 10 };
        nn = new NeuralNetwork(numNodesInLayers, 0, "sigmoid");
        nn.loadValues("values\\NNValues_DigitID_0.9843_1.0_sigmoid_batchSize30.txt");
        MouseListener ml2 = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change
                // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent me) {
                down = true;// throw new UnsupportedOperationException("Not supported yet."); //To change
                            // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                down = false;// throw new UnsupportedOperationException("Not supported yet."); //To change
                             // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if (down) {
                    Pixel p = (Pixel) me.getComponent();
                    p.paint();
                    int x = p.getXCoord();
                    int y = p.getYCoord();
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if ((y + i < 28 && y + i > -1) && (x + j < 28 && x + j > -1)) {
                                pixels[y + i][x + j].increase();
                            }
                        }
                    }

                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (down) {
                    Pixel p = (Pixel) me.getComponent();
                    p.paint();
                    int x = p.getXCoord();
                    int y = p.getYCoord();
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            try {
                                if ((y + i) != y && (x + j) != x) {
                                    pixels[y + i][x + j].increase();
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    }

                }
            }
        };
        MouseListener mll = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change
                // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent me) {
                down = true;// throw new UnsupportedOperationException("Not supported yet."); //To change
                            // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                down = false;// throw new UnsupportedOperationException("Not supported yet."); //To change
                             // body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }

        };
        Border blackline = BorderFactory.createLineBorder(new ColorUIResource(0, 0, 0));
        int x = 0;
        int y = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Pixel panel = new Pixel(j, i);
                panel.setBackground(new ColorUIResource(0, 0, 0));
                panel.setBounds(x, y, PIXEL_SIZE, PIXEL_SIZE);
                // panel.setBorder(blackline);
                panel.addMouseListener(ml2);
                pixels[i][j] = panel;
                this.add(panel);
                x += PIXEL_SIZE;

            }
            x = 0;
            y += PIXEL_SIZE;
        }
        this.addMouseListener(mll);
        // jPanel1.addMouseListener(ml2);
        this.setSize(1000, 1000);
        initComponents();
        setMinimumSize(new Dimension(500, 377).getSize());

        btns[0] = jRadioButton1;
        btns[1] = jRadioButton2;
        btns[2] = jRadioButton3;
        btns[3] = jRadioButton4;
        btns[4] = jRadioButton5;
        btns[5] = jRadioButton6;
        btns[6] = jRadioButton7;
        btns[7] = jRadioButton8;
        btns[8] = jRadioButton9;
        btns[9] = jRadioButton10;
        btns[10] = jRadioButton11;

        createTrainingData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("0");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("1");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("2");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("3");

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setText("4");

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setText("5");

        buttonGroup1.add(jRadioButton7);
        jRadioButton7.setText("6");

        buttonGroup1.add(jRadioButton8);
        jRadioButton8.setText("7");

        buttonGroup1.add(jRadioButton9);
        jRadioButton9.setText("8");
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton10);
        jRadioButton10.setText("9");

        buttonGroup1.add(jRadioButton11);
        jRadioButton11.setText("?");

        jButton3.setText("Guess");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(374, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jRadioButton1)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jRadioButton2))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                        .createSequentialGroup()
                                                        .addComponent(jButton2)
                                                        .addPreferredGap(
                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jButton3,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 77,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jButton1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 70,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButton8))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButton6))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButton4))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButton11)
                                                        .addComponent(jRadioButton10))))
                                .addGap(20, 20, 20)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton1)
                                        .addComponent(jRadioButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton3)
                                        .addComponent(jRadioButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton5)
                                        .addComponent(jRadioButton6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton7)
                                        .addComponent(jRadioButton8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton9)
                                        .addComponent(jRadioButton10))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButton11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED,
                                                        31, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addGap(16, 16, 16)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pixels[i][j].unpaint();

            }
        }
        btns[10].setSelected(true);
    }// GEN-LAST:event_jButton1ActionPerformed

    int count = 0;

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        System.out.println(++count);
        try {
            FileWriter fw = new FileWriter("trainingData.txt", true);
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (pixels[i][j].getBackground().equals(Color.getHSBColor(0, 0, 1f))) {
                        fw.append('1');
                    } else {
                        fw.append('0');
                    }

                }

            }
            if (this.jRadioButton1.isSelected()) {
                fw.append(":0\n");
            } else if (this.jRadioButton2.isSelected()) {
                fw.append(":1\n");
            } else if (this.jRadioButton3.isSelected()) {
                fw.append(":2\n");
            } else if (this.jRadioButton4.isSelected()) {
                fw.append(":3\n");
            } else if (this.jRadioButton5.isSelected()) {
                fw.append(":4\n");
            } else if (this.jRadioButton6.isSelected()) {
                fw.append(":5\n");
            } else if (this.jRadioButton7.isSelected()) {
                fw.append(":6\n");
            } else if (this.jRadioButton8.isSelected()) {
                fw.append(":7\n");
            } else if (this.jRadioButton9.isSelected()) {
                fw.append(":8\n");
            } else if (this.jRadioButton10.isSelected()) {
                fw.append(":9\n");
            } else if (this.jRadioButton11.isSelected()) {
                fw.append(":?\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }// GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton9ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jRadioButton9ActionPerformed

    int index = 0;

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        double[] input = new double[784];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                input[(COLS * i) + j] = pixels[i][j].getBackground().getRed();
            }
        }

        // double adj = 0.125;
        // double centre = 0.7;
        // double[] weight1 = { adj, adj, adj, adj, centre, adj, adj, adj, adj };
        // double[] newIn = new double[784];
        // for (int i = 0; i < newIn.length; i++) {
        // if (i + 28 + 1 < 784) {
        // newIn[i] = convolve(input, weight1, i + 28 + 1);
        // if (newIn[i] > 0) {
        // newIn[i] *= (1 + (1 - (newIn[i] / 255)));

        // }
        // }

        // }

        // for (int i = 0; i < newIn.length; i++) {
        // pixels[i / 28][i % 28].setBackground(new ColorUIResource((int) newIn[i],
        // (int) newIn[i], (int) newIn[i]));
        // }
        // double[] eg =
        // createTrainingDataFromMnistMatrix(mnistMatrix[index++]).getInputValues();
        // for (int i = 0; i < eg.length; i++) {
        // pixels[i / 28][i % 28].setBackground(new ColorUIResource((int) eg[i], (int)
        // eg[i], (int) eg[i]));
        // }

        // for (int i = 0; i < newIn.length; i++) {
        // System.out.print(newIn[i] + " ");
        // if (i % 28 == 0) {
        // System.out.println();
        // }
        // }
        System.out.println();
        nn.setInput(input);
        nn.forward();
        System.out.println(nn.getAnswer());
        double[] confidences = nn.getOutput();
        for (int i = 0; i < confidences.length; i++) {
            System.out.println(i + " [" + confidences[i] + "]");
        }

        btns[nn.getAnswer()].setSelected(true);

    }// GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                UI a = new UI();
                a.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseDragged(MouseEvent me) {
        System.out.println("AA");
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    private double convolve(double[] old, double[] weights, int p) {
        double con = 0;
        for (int i = 0; i < weights.length; i++) {
            if (p + (((i / 3) - 2) * 28) + (i % 3) - 2 < 783 && p + (((i / 3) - 2) * 28) + (i % 3) - 2 > 0) {
                con += old[p + (((i / 3) - 2) * 28) + (i % 3) - 2] * weights[i];
            }

        }
        if (con > 255) {
            con = 255;
        }
        return con;
    }

    public void createTrainingData() {
        try {
            mnistMatrix = new MnistDataReader().readData("data\\train-images.idx3-ubyte",
                    "data\\train-labels.idx1-ubyte");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TraingingData createTrainingDataFromMnistMatrix(MnistMatrix m) {
        double[] input = new double[m.getNumberOfRows() * m.getNumberOfColumns()];
        for (int r = 0; r < m.getNumberOfRows(); r++) {
            for (int c = 0; c < m.getNumberOfColumns(); c++) {
                input[r * m.getNumberOfColumns() + c] = (m.getValue(r, c));
            }
        }

        return new TraingingData(input, (m.getLabel() + "").charAt(0));
    }
}
