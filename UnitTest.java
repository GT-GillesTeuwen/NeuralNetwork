public class UnitTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE

    private static int numTest = 0;
    private static int numPassed = 0;
    private static int totalErrors = 0;
    private static String currentTest = "";

    public static void main(String[] args) {

        setTest();

        addTest();

        matrixMultiplyTest();

        scalarMultiplyTest();

        arraySetTest();

        sigmoidTest();

        transposeTest();

        oneDToTwoDTest();

        System.out
                .println(PURPLE_BOLD_BRIGHT + "\n\n================\n" + "Total Errors: "
                        + totalErrors
                        + "\n================" + ANSI_RESET);
    }

    public static void AssertEquals(double expected, double actual) {
        numTest++;
        if (expected == actual) {
            numPassed++;
            System.out.println(ANSI_GREEN + "Test " + numTest + " Passed" + ANSI_RESET);
        } else {
            totalErrors++;
            System.out.print(ANSI_RED + "Test " + numTest + " Failed\t");
            System.out.print("Expected " + expected + "\t" + "Got " + actual + "\n" + ANSI_RESET);
        }
    }

    public static void startTest(String name) {
        numTest = 0;
        numPassed = 0;
        currentTest = name;
        System.out.println("\n==========================\nStarting " + name + "\n==========================");
    }

    public static void printTestResults() {
        String out = "";
        if (numPassed == 0) {
            out += ANSI_RED;
        } else if (numPassed == numTest) {
            out += ANSI_GREEN;
        } else {
            out += ANSI_YELLOW;
        }
        out += ("\n " + currentTest + " scored " + numPassed + "/" + numTest + ANSI_RESET);

        System.out.println(out);
    }

    public static void setTest() {
        startTest("Set test");
        Matrix m1 = new Matrix(1, 1);
        Matrix m2 = new Matrix(1, 1);

        m1.setValueAt(0, 0, 1);
        m2.setValueAt(0, 0, 2);

        AssertEquals(1, m1.getValueAt(0, 0));
        AssertEquals(2, m2.getValueAt(0, 0));
        printTestResults();
    }

    public static void addTest() {
        startTest("Add test");
        Matrix m1 = new Matrix(1, 1);
        Matrix m2 = new Matrix(1, 1);

        m1.setValueAt(0, 0, 1);
        m2.setValueAt(0, 0, 2);

        m1.add(m2);
        AssertEquals(3, m1.getValueAt(0, 0));
        printTestResults();

    }

    public static void matrixMultiplyTest() {
        startTest("Matrix Multiply test");
        Matrix m3 = new Matrix(2, 3);
        Matrix m4 = new Matrix(3, 2);

        m3.setValueAt(0, 0, 1);
        m3.setValueAt(0, 1, 2);
        m3.setValueAt(0, 2, 3);
        m3.setValueAt(1, 0, 4);
        m3.setValueAt(1, 1, 5);
        m3.setValueAt(1, 2, 6);

        m4.setValueAt(0, 0, 10);
        m4.setValueAt(0, 1, 11);
        m4.setValueAt(1, 0, 20);
        m4.setValueAt(1, 1, 21);
        m4.setValueAt(2, 0, 30);
        m4.setValueAt(2, 1, 31);

        Matrix ans = m3.multiply(m4);

        AssertEquals(140, ans.getValueAt(0, 0));
        AssertEquals(146, ans.getValueAt(0, 1));
        AssertEquals(320, ans.getValueAt(1, 0));
        AssertEquals(335, ans.getValueAt(1, 1));
        printTestResults();
    }

    public static void scalarMultiplyTest() {
        startTest("Scalar Multiply test");
        Matrix m1 = new Matrix(2, 3);

        m1.setValueAt(0, 0, 2);
        m1.setValueAt(0, 1, 4);
        m1.setValueAt(0, 2, 6);

        m1.setValueAt(1, 0, 1);
        m1.setValueAt(1, 1, 3);
        m1.setValueAt(1, 2, 5);

        m1.multiply(0.5);

        AssertEquals(1, m1.getValueAt(0, 0));
        AssertEquals(2, m1.getValueAt(0, 1));
        AssertEquals(3, m1.getValueAt(0, 2));
        AssertEquals(0.5, m1.getValueAt(1, 0));
        AssertEquals(1.5, m1.getValueAt(1, 1));
        AssertEquals(2.5, m1.getValueAt(1, 2));
        printTestResults();

    }

    public static void arraySetTest() {
        startTest("Input Array Set Test");
        Matrix m1 = new Matrix(5, 1);

        double[] input = new double[5];
        for (int i = 0; i < input.length; i++) {
            input[i] = ((i + 1) / 10.0);
        }

        m1.setInputValues(input);

        for (int i = 0; i < input.length; i++) {
            AssertEquals(((i + 1) / 10.0), m1.getValueAt(i, 0));
        }

        printTestResults();

    }

    public static void sigmoidTest() {
        startTest("Sigmoid test");
        Matrix m1 = new Matrix(2, 3);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                m1.setValueAt(i, j, i - j);
            }
        }

        m1.applyActivation("sigmoid");

        double[] answers = { 0.5, 0.26894142137, 0.119202922022, 0.73105857863, 0.5, 0.26894142137 };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                AssertEquals(Math.round(answers[i * 3 + j] * 100000) / 100000,
                        Math.round(m1.getValueAt(i, j) * 100000) / 100000);
            }
        }
        printTestResults();

    }

    public static void transposeTest() {
        startTest("Transpose test");
        Matrix m1 = new Matrix(2, 3);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                m1.setValueAt(i, j, i - j);
            }
        }

        m1.transpose();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                AssertEquals(j - i, m1.getValueAt(i, j));
            }
        }
        printTestResults();

    }

    public static void oneDToTwoDTest() {
        startTest("1D to 2D Test");
        Matrix m1 = new Matrix(2, 3);

        int[] oneD = { 0, 1, 2, 3, 4, 5, 6, 7 };

        int[][] twoD = new int[2][4];

        for (int i = 0; i < oneD.length; i++) {
            twoD[i / 4][i % 4] = oneD[i];
        }

        for (int i = 0; i < twoD.length; i++) {
            for (int j = 0; j < twoD[i].length; j++) {
                AssertEquals(4 * i + j, twoD[i][j]);
            }
        }
        printTestResults();

    }

}
