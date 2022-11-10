/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.attempt.pkg1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gteuw
 */
public class Trainer {

    private ArrayList<TraingingData> data = new ArrayList<>();

    public Trainer() {
        createTrainingData();
    }
    
    public double runBatch(NeuralNetwork n,int start,int number){
        ArrayList<Double>[] mat=new ArrayList[number];
        double out=testBatch(n, start, number,mat);
        double[] finalAdjustments=new double[mat[0].size()];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].size(); j++) {
                finalAdjustments[j]+=mat[i].get(j);
            }
        }
        
        for (int i = 0; i < finalAdjustments.length; i++) {
            finalAdjustments[i]=finalAdjustments[i]/mat[0].size();
        }
        
        applyAdjustments(n, finalAdjustments);
        return out;
    }
    
    public void applyAdjustments(NeuralNetwork n,double[] adjustments){
        int num=0;
        for (int i = n.getNumLayers()-1; i > 1; i--) {
            
            Layer cur=n.getLayerAt(i);
            Layer prev=n.getLayerAt(i-1);
            
            for (int j = 0; j < cur.getNumNeurons(); j++) {
                cur.getNeurons()[j].setBias(cur.getNeurons()[j].getBias()-adjustments[num++]);
            }
            
            for (int j = 0; j < prev.numNeurons; j++) {
                for (int k = 0; k < cur.getNumNeurons(); k++) {
                    prev.getNeurons()[j].setWeightAt(k,  prev.getNeurons()[j].getWeightAt(k)-adjustments[num++]);
                }
            }
            
        }
    }
    public void simpleTest(NeuralNetwork n,int dataNumber){
        n.setInput(data.get(dataNumber).getInputValues());
        n.start();
        
        Neuron[] answers = n.getResults();
        System.out.println("Answers");
        for (int i = 0; i < answers.length; i++) {
            System.out.println(i + ": [" + answers[i].getValue() + "]");
        }
    }

    public double doTest(NeuralNetwork n,int dataNumber,ArrayList<Double>[] improvementMatrix,int num) {
        n.setInput(data.get(dataNumber).getInputValues());
        n.start();
        
        Neuron[] answers = n.getResults();
//        System.out.println("Answers");
//        for (int i = 0; i < answers.length; i++) {
//            System.out.println(i + ": [" + answers[i].getValue() + "]");
//        }
        ArrayList<Double> imp=new ArrayList<>();
        makeImprovements(n, data.get(dataNumber).getExpected(), imp, 3);
        improvementMatrix[num]=(imp);
       return (calculateCost(answers, data.get(dataNumber).getExpected()));
    }
    
    public double testBatch(NeuralNetwork n,int start,int number,ArrayList<Double>[] improvementMatrix){
        double avgCost=0;
        ThreadedTest[] allThreads=new ThreadedTest[number];
        for (int i = 0; i < number; i++) {
            allThreads[i]=new ThreadedTest(i, n, data.get(start+i));
        }
        for (int i = 0; i < number; i++) {
            allThreads[i].start();
        }
        boolean busy=true;
        while(busy){
            busy=false;
            for (int i = 0; i < number; i++) {
                if (allThreads[i].isAlive()) {
                    busy=true;
                }
            }
        }
        
        for (int i = 0; i < number; i++) {
            improvementMatrix[allThreads[i].getNum()] =allThreads[i].getRes();
        }
        
        
        return avgCost/number;
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

    public double calculateCost(Neuron[] answers, double[] expected) {
        double cost = 0;
        for (int i = 0; i < answers.length; i++) {
            cost += Math.pow((answers[i].getValue() - expected[i]), 2);
        }
        return cost;
    }

    public void createTrainingData() {
        try {
            Scanner file = new Scanner(new FileReader("trainingData.txt"));
            while (file.hasNext()) {
                String[] details = file.nextLine().split(":");
                data.add(new TraingingData(details[0], details[1].charAt(0)));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
