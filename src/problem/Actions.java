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
        for (int i = 0; i <= maxFunding; i++) {
            for (int j = 0; j <= maxFunding; j++) {
                if (checkValidAction(new int[]{i,j})) {
                    actions.add(new Action(new int[]{i,j}, maxManufacturing));
                }
            }
        }
    }

    private boolean checkValidAction(int values[]) {
        return Arrays.stream(values).sum() <= maxFunding;
    }
}
