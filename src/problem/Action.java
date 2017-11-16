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

    public double prob(int[] state, int[] prevState, double[][][] probs) {

        double prob = 1.0;

        for (int i = 0; i < state.length; i++) {
            int x = state[i] + values[i];
            int y = prevState[i];
            if (x > maxManufacturing) {
                return 0;
            }
//            if (y > x || x > maxManufacturing) {
//                return 0;
//            } else if (0 < y && y <= x) {
//                prob *= probabilities.get(i).get(x, x - y);
//            } else if (y == 0) {
//                double p = 0;
//                for (int j = x; j <= maxManufacturing; j++) {
//                    p += probabilities.get(i).get(x, j);
//                }
//                prob *= p;
//            }
            prob *= probs[i][x][y];
        }

        return prob;

    }

    public double reward(int[] state, double[][] rewards) {

        double reward = 0;

        for (int i = 0; i < state.length; i++) {
            int x = state[i];
//            for (int y = 0; y <= maxManufacturing; y++) {
//                reward += probabilities.get(i).get(x, y) * sales.get(i) * (0.6 * Math.min(x, y) - 0.25 * Math.max(y - x, 0));
//            }
            reward += rewards[i][x];
        }

        return reward;

    }

}
