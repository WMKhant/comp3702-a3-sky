package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Action {

    public int[] values;
    int maxManufacturing;

    public Action(int values[], int maxManufacturing) {
        this.values = values;
        this.maxManufacturing = maxManufacturing;
    }

    public double prob(int[] state, int[] prevstate, List<Matrix> probabilities) {

        double prob = 1.0;

        for (int i = 0; i < state.length; i++) {
            int x = Math.max(state[i] - maxManufacturing, 0) +  values[i] - Math.max(prevstate[i] - maxManufacturing, 0);
            if (x < 0 || x >= maxManufacturing) {
                return 0;
            }
//            System.out.println(prevstate[i] + ", " + x);
            prob *= probabilities.get(i).get(Math.max(prevstate[i] - maxManufacturing,0), x);
        }

        System.out.println(prob);
        return prob;

    }

    public double reward(int[] state, int[] prevstate, List<Double> sales) {

        double profit = 0;

        for (int i = 0; i < state.length; i++) {
            profit += 0.6 * sales.get(i) * (Math.max(state[i] - maxManufacturing, 0) - Math.max(prevstate[i] - maxManufacturing, 0) + values[i]);
            profit -= 0.25 * sales.get(i) * Math.min(state[i] - maxManufacturing, 0);
        }

        return profit;

    }

}
