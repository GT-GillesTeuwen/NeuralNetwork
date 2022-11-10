/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.attempt.pkg1;

import java.util.ArrayList;

/**
 *
 * @author gteuw
 */
public class NeuralNetworkAttempt1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        NeuralNetwork n = new NeuralNetwork(11, 2, 16, 28 * 28);

        Trainer t = new Trainer();
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < 200; i++) {
                t.runBatch(n, 0, 400);

            }
            System.out.println(t.runBatch(n, 0, 2));
            t.simpleTest(n, 0);
        }

    }

}
