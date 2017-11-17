package solver;

/**
 * COMP3702 A3 2017 Support Code
 * v1.0
 * last updated by Nicholas Collins 19/10/17
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import problem.*;

public class MySolver implements FundingAllocationAgent {

    private ProblemSpec spec = new ProblemSpec();
    private VentureManager ventureManager;
    private List<Matrix> probabilities;
    private List<Double> sales;
    private States states;
    private Actions actions;

    private static double EPSILON = 1e-12;

    public MySolver(ProblemSpec spec) throws IOException {
        this.spec = spec;
        ventureManager = spec.getVentureManager();
        probabilities = spec.getProbabilities();
        sales = spec.getSalePrices();
    }

    public void doOfflineComputation() {

        int ventures = ventureManager.getNumVentures();
        int maxFunding = ventureManager.getMaxAdditionalFunding();
        int maxManufacturing = ventureManager.getMaxManufacturingFunds();
        int length = maxManufacturing + 1;
        int length2 = (ventures == 2) ? 1 : length;

        double disc = spec.getDiscountFactor();


        // initialise state values
        states = new States(ventures, maxManufacturing);

        for (int i = 0; i < states.states.length; i++) {
            for (int j = 0; j < states.states[0].length; j++) {
                System.out.println(Arrays.toString(states.states[i][j]));
            }
        }

        System.out.println("-------------------------------------");

        for (int i = 0; i < states.states.length; i++) {
            System.out.println(Arrays.toString(Arrays.stream(states.valid[i]).map((x) -> x[0]).toArray()));
        }

        // create actions
        actions = new Actions(ventures, maxManufacturing, maxFunding);

        for (Action action : actions.actions) {
            System.out.println(Arrays.toString(action.values));
        }

        // calculate all probabilities

        double[][][] probs = new double[ventures][length][length];
        for (int i = 0; i < ventures; i++) {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < length; y++) {
                    double pr = 0;
                    if (y > x) {
                        pr = 0;
                    } else if (0 < y && y <= x) {
                        pr = probabilities.get(i).get(x, x - y);
                    } else if (y == 0) {
                        double p = 0;
                        for (int j = x; j < length; j++) {
                            p += probabilities.get(i).get(x, j);
                        }
                        pr = p;
                    }
                    probs[i][x][y] = pr;
                }
            }
        }

        // calculate all rewards

        double[][] rewards = new double[ventures][length];

        for (int i = 0; i < ventures; i++) {
            for (int x = 0; x < length; x++) {
                double reward = 0;
                for (int y = 0; y < length; y++) {
                    reward += probabilities.get(i).get(x, y) * (0.6 * Math.min(x, y) - 0.25 * Math.max(y - x, 0));
                }
                rewards[i][x] = reward * sales.get(i);
            }
        }

        System.out.println(Arrays.toString(rewards[0]));

        System.out.println(Arrays.toString(rewards[1]));

//        System.out.println(Arrays.toString(rewards[2]));



        for (int i = 0; i < probs[0].length; i++) {
            System.out.println(Arrays.toString(probs[0][i]));
        }

        // value iteration
        boolean converged = false;

        int counter = 0;

        while (!converged) {

            counter++;
            converged = true;
            states.setStates();

            // for each state (i,j)

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    for (int k = 0; k < length2; k++) {
                        // check valid state

                        if (!states.valid[i][j][k]) {
                            continue;
                        }

                        int[] state = (ventures == 2) ? new int[]{i, j} : new int[]{i, j, k};

                        List<Double> values = new ArrayList<>();

                        // for each action

                        for (Action action : actions.actions) {

                            double total = 0;

                            double pr = 0;

                            // for each previous state
                            for (int x = 0; x < length; x++) {
                                for (int y = 0; y < length; y++) {
                                    for (int z = 0; z < length2; z++) {
                                        if (!states.valid[x][y][k]) {
                                            continue;
                                        }

                                        int[] prevstate = (ventures == 2) ? new int[]{x, y} : new int[]{x, y, z};

                                        double p = action.prob(state, prevstate, probs);

                                        pr += p;

                                        total += p * (action.reward(state, rewards) + disc * states.prevStates[i][j][k]);
                                    }
                                }
                            }

//                            System.out.println("PROB: " + pr);
                            values.add(total);

                        }

                        // set state value
                        double value = Collections.max(values);
                        if (Math.abs(states.states[i][j][k] - value) > EPSILON) {
                            converged = false;
                        }
                        states.states[i][j][k] = value;
                    }
                }
            }

        }

        for (int i = 0; i < states.states.length; i++) {
            for (int j = 0; j < states.states[0].length; j++) {
                System.out.println(Arrays.toString(states.states[i][j]));
            }
        }
        System.out.println("Iterations: " + counter);

        for (int k = 0; k < length2; k++) {
            for (int j = 0; j < length; j++) {
                for (int i = 0; i < length; i++) {
                    if (i + j + k > maxManufacturing) {
                        continue;
                    }
                    List<Integer> funds = new ArrayList<>();
                    funds.add(i);
                    funds.add(j);
                    if (ventures == 3) {
                        funds.add(k);
                    }
                    List<Integer> additional = generateAdditionalFundingAmounts(funds, 7);
                    System.out.println(funds + " -> " + additional);
                }
            }
        }
//        System.out.println(generateAdditionalFundingAmounts(new ArrayList<Integer>(Arrays.asList(0,7,0)), 7));
    }

    public List<Integer> generateAdditionalFundingAmounts(List<Integer> manufacturingFunds,
                                                          int numFortnightsLeft) {
        // Example code that allocates an additional $10 000 to each venture.
        // TODO Replace this with your own code.

        List<Integer> additionalFunding = new ArrayList<Integer>();

//		int totalManufacturingFunds = 0;
//		for (int i : manufacturingFunds) {
//			totalManufacturingFunds += i;
//		}
//
//		int totalAdditional = 0;
//		for (int i = 0; i < ventureManager.getNumVentures(); i++) {
//			if (totalManufacturingFunds >= ventureManager.getMaxManufacturingFunds() ||
//			        totalAdditional >= ventureManager.getMaxAdditionalFunding()) {
//				additionalFunding.add(0);
//			} else {
//				additionalFunding.add(1);
//				totalAdditional ++;
//				totalManufacturingFunds ++;
//			}
//		}

        double max = -Double.MAX_VALUE;
        Action best = null;

        for (Action action : actions.actions) {

            int i = manufacturingFunds.get(0) + action.values[0];
            int j = manufacturingFunds.get(1) + action.values[1];
            int k = (manufacturingFunds.size() == 2) ? 0 : manufacturingFunds.get(2) + action.values[2];

            if (i + j + k <= ventureManager.getMaxManufacturingFunds()) {
                if (states.states[i][j][k] > max) {
                    best = action;
                    max = states.states[i][j][k];
                }
            }

        }

//        System.out.println(max);

        additionalFunding.add(best.values[0]);
        additionalFunding.add(best.values[1]);
        if (manufacturingFunds.size() == 3) {
            additionalFunding.add(best.values[2]);
        }


        return additionalFunding;
    }

}
