package problem;

public class Action {

    int[] values;

    public Action(int values[]) {
        this.values = values;
    }

    public double prob(int[] state, int[] prevstate) {
        return 0.1;
    }

    public double reward(int[] state, int[] prevstate) {
        return 0.2;
    }

}
