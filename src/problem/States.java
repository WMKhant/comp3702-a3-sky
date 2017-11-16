package problem;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

public class States {

    int maxManufacturing;
    public double[][][] states;
    public double[][][] prevStates;
    public boolean[][][] valid;

    public States(int ventures, int maxManufacturing) {

        this.maxManufacturing = maxManufacturing;
        int length = maxManufacturing + 1;
        int length2 = (ventures == 2) ? 1 : length;


        states = new double[length][length][length2];
        prevStates = new double[length][length][length2];
        valid = new boolean[length][length][length2];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length2; k++) {
                    states[i][j][k] = 0;
                    valid[i][j][k] = checkValidState(new int[]{i,j,k});
                }
            }
        }
    }

    private boolean checkValidState(int[] values) {
        return Arrays.stream(values).sum() <= maxManufacturing;
    }

    public void setStates() {
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[0].length; j++) {
                for (int k = 0; k < states[0][0].length; k++) {
                    prevStates[i][j][k] = states[i][j][k];
                }
            }
        }
    }

}
