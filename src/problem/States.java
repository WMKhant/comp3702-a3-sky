package problem;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class States {

    int maxManufacturing;
    public double[][] states;
    public double[][] prevstates;
    public boolean[][] valid;

    public States(int ventures, int maxManufacturing) {

        if (ventures != 2) {
            throw new NotImplementedException();
        }

        this.maxManufacturing = maxManufacturing;

        int maxArray = maxManufacturing * 2 + 1;

        states = new double[maxArray][maxArray];
        valid = new boolean[maxArray][maxArray];

        for (int i = 0; i < maxArray; i++) {
            for (int j = 0; j < maxArray; j++) {
                states[i][j] = 0;
                valid[i][j] = checkValid(new int[]{i,j});
            }
        }

    }

    private boolean checkValid(int[] values) {
        int pos = 0;
        int neg = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > 0) {
                pos += values[i];
            } else {
                neg -= values[i];
            }
        }

        return pos < maxManufacturing && neg < maxManufacturing;
    }

    public void setStates() {
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[0].length; j++) {
                prevstates[i][j] = states[i][j];
            }
        }
    }

}
