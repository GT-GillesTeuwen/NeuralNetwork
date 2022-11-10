/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.attempt.pkg1;

/**
 *
 * @author gteuw
 */
public class LastLayer extends Layer{

    public LastLayer(Neuron[] neurons, int numNeurons) {
        super(neurons, numNeurons);
    }
    
    
    
    public void forward(){
        for (int i = 0; i < neurons.length; i++) {
            //System.out.println("["+neurons[i].getValue()+"] ");
        }
    }
}
