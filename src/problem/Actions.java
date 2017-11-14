package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Actions {

    public List<Action> actions;
    int ventures;
    int maxManufacturing;
    int maxFunding;

    public Actions(int ventures, int maxManufacturing, int maxFunding) {
        this.ventures = ventures;
        this.maxFunding = maxFunding;
        this.maxManufacturing = maxManufacturing;
        this.actions = new ArrayList<>();
        createActions();
    }

    private void createActions() {
        for (int i = 0; i < maxManufacturing; i++) {
            for (int j = 0; j < maxManufacturing; j++) {
                if (checkValidAction(new int[]{i,j})) {
                    actions.add(new Action(new int[]{i,j}));
                }
            }
        }
    }

    private boolean checkValidAction(int values[]) {

        return Arrays.stream(values).sum() < maxManufacturing && Arrays.stream(values).allMatch((x) -> x < maxFunding);

    }
}
