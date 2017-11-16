package solver;

import problem.Matrix;
import problem.ProblemSpec;

import java.util.ArrayList;
import java.util.List;

public class State {
    private List<Integer> fundings;
    private List<Double> salePrices;
    private List<Matrix> probabilities;

    public State(ProblemSpec spec) {
        this.fundings = spec.getInitialFunds();
        this.salePrices = spec.getSalePrices();
        this.probabilities = spec.getProbabilities();
    }

    public void updateFunds(int[] changes) {
        for (int venture = 0; venture < fundings.size(); venture++) {
            fundings.set(venture, fundings.get(venture) + changes[venture]);
        }
    }

    public double reward() {
        //sum of expected profits across all ventures
        double profit = 0;
        //for each venture
        for (int venture = 0; venture < fundings.size(); venture++) {
            List<Double> relevantProbs = probabilities.get(venture).getRow(fundings.get(venture));
            //for each possible outcome
            for (int sales = 0; sales < relevantProbs.size(); sales++) {
                double earnings = sales * salePrices.get(venture);
                double delta = relevantProbs.get(sales) * (earnings - fundings.get(venture));
                if (delta < 0) {
                    profit += 0.25 * delta;
                } else {
                    profit += 0.6 * delta;
                }
            }
        }
        return profit;
    }
}
