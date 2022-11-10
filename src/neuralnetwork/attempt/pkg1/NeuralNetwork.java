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
public class NeuralNetwork {

    private Layer[] layers;
    private int numLayers;
    private int numInputs;

    public NeuralNetwork(int numLLNeurons, int numHidden, int numHNeurons, int numFLNeurons) {
        layers=new Layer[numHidden+2];
        this.numLayers=numHidden+2;
        int currLayer=numLayers-1;
        numInputs=numFLNeurons;
        Neuron[] lastLayerNeurons = new Neuron[numLLNeurons];
        for (int i = 0; i < numLLNeurons; i++) {
            lastLayerNeurons[i] = new Neuron(0);
        }
        LastLayer lastLayer = new LastLayer(lastLayerNeurons, numLLNeurons);
        layers[currLayer--]=lastLayer;
        
        Layer prevLayer = lastLayer;       
        for (int i = 0; i < numHidden; i++) {
            Neuron[] hiddenLayerNeurons = new Neuron[numHNeurons];
            for (int j = 0; j < numHNeurons; j++) {
                hiddenLayerNeurons[j] = new Neuron(prevLayer.numNeurons);
            }
            HiddenLayer hiddenLayer = new HiddenLayer(prevLayer, hiddenLayerNeurons, numHNeurons);
            layers[currLayer--]=hiddenLayer;
            prevLayer=hiddenLayer;
            
        }
        
        Neuron[] firstLayerNeurons = new Neuron[numFLNeurons];
        for (int i = 0; i < numFLNeurons; i++) {
            firstLayerNeurons[i] = new Neuron(prevLayer.numNeurons);
        }
        FirstLayer firstLayer = new FirstLayer(prevLayer, firstLayerNeurons, numFLNeurons);
        layers[currLayer--]=firstLayer;
    }
    
    public void start(){
        for (int q = 0; q < numLayers; q++) {
            layers[q].forward();
        }
    }
    
    public void setInput(double [] inVals){
        for (int i = 0; i < numInputs; i++) {
            layers[0].neurons[i].setValue(inVals[i]);
        }
    }
    
    public Neuron[] getResults(){
        return layers[numLayers-1].getNeurons();
        
    }
    
    public Layer getLayerAt(int i){
        return layers[i];
    }
    
    public int getNumLayers(){
        return numLayers;
    }

}
