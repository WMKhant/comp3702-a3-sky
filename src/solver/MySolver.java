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

    private static double EPSILON = 0.00000000001;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		ventureManager = spec.getVentureManager();
        probabilities = spec.getProbabilities();
	}
	
	public void doOfflineComputation() {

		int ventures = ventureManager.getNumVentures();
		int maxFunding = ventureManager.getMaxAdditionalFunding();
		int maxManufacturing = ventureManager.getMaxManufacturingFunds();
		int maxArray = maxManufacturing * 2 + 1;

		double disc = spec.getDiscountFactor();

		if (ventureManager.getName().equals("bronze")) {

			// initialise state values
			States states = new States(ventures, maxManufacturing);

			// create actions
			Actions actions = new Actions(ventures, maxManufacturing, maxFunding);

			// value iteration
			boolean converged = false;

			while(!converged) {

				converged = true;
				states.setStates();

				// for each state (i,j)

				for (int i = 0; i < maxArray; i++) {
					for (int j = 0; j < maxArray; j++) {

						// check valid state

						if (!states.valid[i][j]) {
							continue;
						}

						int[] state = new int[]{i,j};

						List<Integer> values = new ArrayList<>();

						// for each action

						for (Action action : actions.actions) {

							double total = 0;

							// for each previous state
							for (int x = 0; x < maxArray; x++) {
								for (int y = 0; y < maxArray; y++) {

									int[] prevstate = new int[]{x,y};

									total += action.prob(state, prevstate) * (action.reward(state, prevstate) + disc * states.prevstates[i][j]);

								}
							}

						}

						// set state value
						double value = Collections.max(values);
						if (Math.abs(states.states[i][j] - value) > EPSILON) {
							converged = false;
						}
						states.states[i][j] = value;

					}
				}

			}


		}



	}
	
	public List<Integer> generateAdditionalFundingAmounts(List<Integer> manufacturingFunds,
														  int numFortnightsLeft) {
		// Example code that allocates an additional $10 000 to each venture.
		// TODO Replace this with your own code.

		List<Integer> additionalFunding = new ArrayList<Integer>();

		int totalManufacturingFunds = 0;
		for (int i : manufacturingFunds) {
			totalManufacturingFunds += i;
		}
		
		int totalAdditional = 0;
		for (int i = 0; i < ventureManager.getNumVentures(); i++) {
			if (totalManufacturingFunds >= ventureManager.getMaxManufacturingFunds() ||
			        totalAdditional >= ventureManager.getMaxAdditionalFunding()) {
				additionalFunding.add(0);
			} else {
				additionalFunding.add(1);
				totalAdditional ++;
				totalManufacturingFunds ++;
			}
		}

		return additionalFunding;
	}

}
