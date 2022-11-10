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
public class HiddenLayer extends Layer{
    
    private Layer nextLayer;

    public HiddenLayer(Layer nextLayer, Neuron[] neurons, int numNeurons) {
        super(neurons, numNeurons);
        this.nextLayer = nextLayer;
    }
    
    
    
    @Override
    public void forward() {
        for (int j = 0; j < nextLayer.getNumNeurons(); j++) {
            double newVal=0;
            for (int i = 0; i < neurons.length; i++) {
                newVal+=neurons[i].getForwardValue(j);
            }
            nextLayer.setNeuronValue(j, newVal);
        }

    }
    
    
}
