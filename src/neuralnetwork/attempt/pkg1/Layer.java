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
public class Layer {
    protected Neuron [] neurons;
    protected int numNeurons;

    public Layer(Neuron[] neurons, int numNeurons) {
        this.neurons = neurons;
        this.numNeurons = numNeurons;
    }
    
    
    
    protected void defaultInit(){
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].defaultInit();
        }
    }
    
    protected void randomInit(){
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].randomInit();
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public int getNumNeurons() {
        return numNeurons;
    }
    
    public void setNeuronValue(int i,double newValue){
        neurons[i].setValue(newValue);
    }
    
    public void forward(){
        System.out.println("Damn");
    }
    
    
    
}
