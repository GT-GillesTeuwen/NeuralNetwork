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
public class TraingingData {
    private double[] inputValues;
    private char answer;

    public TraingingData(String input, char answer) {
        convertStringtoArray(input);
        this.answer = answer;
    }
    
    private void convertStringtoArray(String input){
        double[] vals=new double[input.length()];
        for (int i = 0; i < input.length(); i++) {
            vals[i]=Double.parseDouble(input.charAt(i)+"");
        }
        inputValues=vals;
    }
    
    public double[] getInputValues(){
        return inputValues;
    }
    
    public double[] getExpected(){
        double [] expected=new double[11];
        if(answer=='?'){
            for (int i = 0; i < 10; i++) {
                expected[i]=0;
            }
            expected[10]=1;
            return expected;
        }
        
        for (int i = 0; i < 11; i++) {
            expected[i]=0;
        }
        expected[Integer.parseInt(answer+"")]=1;
        return expected;
    }
    
    
}
