package problem;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

public class States {

    int maxManufacturing;
    public double[][] states;
    public double[][] prevStates;
    public boolean[][] valid;

    public States(int ventures, int maxManufacturing) {

        if (ventures != 2) {
            throw new NotImplementedException();
        }

        this.maxManufacturing = maxManufacturing;

        int length = maxManufacturing + 1;

        states = new double[length][length];
        prevStates = new double[length][length];
        valid = new boolean[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                states[i][j] = 0;
                valid[i][j] = checkValidState(new int[]{i,j});
            }
        }

    }

    private boolean checkValidState(int[] values) {
        return Arrays.stream(values).sum() <= maxManufacturing;
    }

    public void setStates() {
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[0].length; j++) {
                prevStates[i][j] = states[i][j];
            }
        }
    }

}
