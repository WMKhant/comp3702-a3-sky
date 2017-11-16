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

    public double prob(int[] state, int[] prevState, List<Matrix> probabilities) {

        double prob = 1.0;

        for (int i = 0; i < state.length; i++) {
            int x = state[i] + values[i];
            int y = prevState[i];
            if (y > x || x > maxManufacturing) {
                return 0;
            } else if (0 < y && y <= x) {
                prob *= probabilities.get(i).get(x, x - y);
            } else if (y == 0) {
                double p = 0;
                for (int j = x; j <= maxManufacturing; j++) {
                    p += probabilities.get(i).get(x, j);
                }
                prob *= p;
            }
        }

        return prob;

    }

    public double reward(int[] state, List<Matrix> probabilities, List<Double> sales) {

        double reward = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j <= maxManufacturing; j++) {
                reward += probabilities.get(i).get(state[i], j) * sales.get(i) * (0.6 * Math.min(state[i], j) - 0.25 * Math.max(j - state[i], 0));
            }
        }

        return reward;

    }

}
