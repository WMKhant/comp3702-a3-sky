package solver;

/**
 * COMP3702 A3 2017 Support Code
 * v1.0
 * last updated by Nicholas Collins 19/10/17
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problem.VentureManager;
import problem.Matrix;
import problem.ProblemSpec;

public class MySolver implements FundingAllocationAgent {
	private ProblemSpec spec = new ProblemSpec();
	private VentureManager ventureManager;
    private List<Matrix> probabilities;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		ventureManager = spec.getVentureManager();
        probabilities = spec.getProbabilities();
	}
	
	public void doOfflineComputation() {
		//Load in information

		//State stuff
		State[] states = {};

		//The value iteration
	    boolean converged = false;
	    while (!converged) {
	    	//initialise value = reward for all s
	    	ArrayList<Double> values = new ArrayList<>();
	    	for (State state : states) {
	    		//
			}
	    	if (converged) {
	    		converged = true;
			}
		}
	}

	private void silverCalculation() {
		int numVentures = ventureManager.getNumVentures();
		int maxAddFunding = ventureManager.getMaxAdditionalFunding();
		int maxManuFunding = ventureManager.getMaxManufacturingFunds();
		double disc = spec.getDiscountFactor();

		boolean converged = false;
		while (!converged) {
			//initialise value = reward for all s
			//ArrayList<Double> values = new ArrayList<>();
			//for (State state : states) {
			//stateValue = state.reward + disc * sum(transition(state, action, prevState) * value(prevState));
			//values.add(stateValue);
			//}
			//prevValue = value;
			//value = max(values);

			if (converged /*Math.abs(value - prevValue) < EPSILON*/) {
				converged = true;
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
