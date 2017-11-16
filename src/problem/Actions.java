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
        createActions(ventures);
    }

    private void createActions(int ventures) {
        if (ventures == 2) {
            for (int i = 0; i <= maxFunding; i++) {
                for (int j = 0; j <= maxFunding; j++) {
                    if (checkValidAction(new int[]{i,j})) {
                        actions.add(new Action(new int[]{i,j}, maxManufacturing));
                    }
                }
            }
        } else if (ventures == 3) {
            for (int i = 0; i <= maxFunding; i++) {
                for (int j = 0; j <= maxFunding; j++) {
                    for (int k = 0; k <= maxFunding; k++) {
                        if (checkValidAction(new int[]{i,j,k})) {
                            actions.add(new Action(new int[]{i,j,k}, maxManufacturing));
                        }
                    }
                }
            }
        }

    }

    private boolean checkValidAction(int values[]) {
        return Arrays.stream(values).sum() <= maxFunding;
    }
}
