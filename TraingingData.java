/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

    public TraingingData(double[] inputValues, char answer) {
        this.inputValues = inputValues;
        this.answer = answer;
    }

    private void convertStringtoArray(String input) {
        double[] vals = new double[input.length()];
        for (int i = 0; i < input.length(); i++) {
            vals[i] = Double.parseDouble(input.charAt(i) + "");
        }
        inputValues = vals;
    }

    public double[] getInputValues() {
        return inputValues;
    }

    public char getAns() {
        return answer;
    }

    public double[] getExpected() {
        double[] expected = new double[10];

        for (int i = 0; i < 10; i++) {
            expected[i] = 0;
        }
        expected[Integer.parseInt(answer + "")] = 1;
        return expected;
    }

    public void printSelf() {
        for (int i = 0; i < inputValues.length; i++) {
            System.out.print(inputValues[i] + "  ");
            if (i % 28 == 0) {
                System.out.println("");
            }
        }
        System.out.println("");
    }

}
