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
public class Neuron {
    private double value;
    private double zVal;
    private double [] weights;
    private double bias;

    public Neuron(int numNextNeurons) {
        weights=new double[numNextNeurons];
        defaultInit();
    }

    
    
    public void defaultInit(){
        value=0.0;
        
        for (int i = 0; i < weights.length; i++) {
            weights[i]=0;
        }
        
        bias=0;
    }
    
    public void randomInit(){
        value=0.0;
        
        for (int i = 0; i < weights.length; i++) {
            weights[i]=(Math.random()*(4))-2;
        }
        
        bias=(Math.random()*(4))-2;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = sigmoid(value+bias);
        this.zVal=value+bias;
    }

    public double getWeightAt(int i) {
        return weights[i];
    }

    public double getZVal(){
        return this.zVal;
    }

    public double getBias() {
        return bias;
    }

    public void setWeightAt(int index,double weight) {
        this.weights[index] = weight;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
    
    
    
    private double relu(double input){
        if (input<0) {
            return 0;
        }else{
            return input;
        }
        
    }
    
    private double sigmoid(double input){
        return (1.0)/(1.0+Math.pow(Math.E, input*(-1.0)));
    }
    
    public double getForwardValue(int i){
        return (this.value*weights[i]);
    }
}
