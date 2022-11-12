/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.attempt.pkg1;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author gteuw
 */
public class NeuralNetworkAttempt1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        NeuralNetwork n = new NeuralNetwork(10, 2, 16, 28 * 28);
        double ans=100;
        Trainer t = new Trainer();
        while(ans>0.1){
            for (int i = 0; i < 600; i++) {
                 ans = (t.runBatch(n, i * 100, 100));
                if (i % 25 == 0) {
                    System.out.println(ans);
                }
            }
            t.shuffleData();
            System.out.println(t.checkAccuracy(n));
        }

    }

}
