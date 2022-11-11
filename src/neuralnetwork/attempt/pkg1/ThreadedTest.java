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
public class ThreadedTest extends Thread{
    
    private int num;
    private NeuralNetwork n;
    private TraingingData td;
    private ArrayList<Double> res;
    private double cost;

    public ThreadedTest(int num, NeuralNetwork n, TraingingData td) {
        this.num = num;
        this.n = n;
        this.td = td; 
        res=new ArrayList<>();
        cost=0;
    }
    
    public double getCost(){
        return cost;
    }
    
    
    
    public void run(){
        n.setInput(td.getInputValues());
        n.start();
        
        Neuron[] answers = n.getResults();
//        System.out.println("Answers");
//        for (int i = 0; i < answers.length; i++) {
//            System.out.println(i + ": [" + answers[i].getValue() + "]");
//        }
        
        makeImprovements(n, td.getExpected(), res, 3);
        this.cost=calculateCost(answers, td.getExpected());
    }
    
     public double calculateCost(Neuron[] answers, double[] expected) {
        double cost = 0;
        for (int i = 0; i < answers.length; i++) {
            cost += Math.pow((answers[i].getValue() - expected[i]), 2);
        }
        return cost;
    }
    
    public double calculateWeightImprovement(double prevLayerVal,double preSigLayerVal,double actualNeuronVal,double expectedNeuronVal){
        return prevLayerVal*sigmoid(preSigLayerVal)*(1-sigmoid(preSigLayerVal))*2*(actualNeuronVal-expectedNeuronVal);
    }
    
    public double calculateBiasImprovement(double preSigLayerVal,double actualNeuronVal,double expectedNerionVal){
        return sigmoid(preSigLayerVal)*(1-sigmoid(preSigLayerVal))*2*(actualNeuronVal-expectedNerionVal);
    }
    
    public double calculatePrevNeuronImprovement(Layer cur,Layer prev,int prevNeuronNumber,double[] expectedNeuronValues){
        double sum=0;
        for(int i=0;i<cur.getNumNeurons();i++){
            sum+=prev.getNeurons()[prevNeuronNumber].getWeightAt(i)*
                    sigmoid(cur.getNeurons()[i].getValue())* 1-sigmoid(cur.getNeurons()[i].getValue())*
                    2*(sigmoid(cur.getNeurons()[i].getValue())-expectedNeuronValues[i]);
        }
        return sum;
    }
    
    private double sigmoid(double input){
        return (1.0)/(1.0+Math.pow(Math.E, input*(-1.0)));
    }
    
    public void makeImprovements(NeuralNetwork n,double [] expectedValues,ArrayList<Double> improvements,int layerNum){
        if (layerNum==0) {
            return;
        }
        Layer prev=n.getLayerAt(layerNum-1);
        Layer cur=n.getLayerAt(layerNum);
        for (int i = 0; i < cur.numNeurons; i++) {
            improvements.add(calculateBiasImprovement(cur.getNeurons()[i].getZVal(), (cur.getNeurons()[i].getValue()), expectedValues[i]));
        }
        for (int i = 0; i < prev.numNeurons; i++) {
            for (int j = 0; j < cur.numNeurons; j++) {
                improvements.add(calculateWeightImprovement(prev.getNeurons()[i].getWeightAt(j), cur.getNeurons()[j].getZVal(), (cur.getNeurons()[j].getValue()), expectedValues[j]));
                
            }
        }
        
        double[]expectedValues2=new double[prev.numNeurons];
        for (int i = 0; i < expectedValues2.length; i++) {
            expectedValues2[i]=calculatePrevNeuronImprovement(cur,prev,i,expectedValues);
        }
        
        
        makeImprovements(n, expectedValues2, improvements, layerNum-1);
    }
    
    public int getNum(){
        return num;
    }
    
    public ArrayList<Double> getRes(){
        return res;
    }
}
