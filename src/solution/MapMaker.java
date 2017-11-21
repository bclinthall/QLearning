package solution;

import util.MarkovDecisionProcess;
import util.State;
import util.Percept;
import util.GridCell;
import util.GridWorld;
import solution.MyQLearner;

import java.util.HashMap;
import java.util.List;
public class MapMaker{
	public static void displayMap(MarkovDecisionProcess mdp, MyQLearner qlearner){
    	List<GridCell> gridCells = mdp.getStates();
		HashMap<GridCell, String> labels = new HashMap<>();
        for (GridCell gridCell : gridCells){
            State state = qlearner.getState(new Percept(mdp, gridCell, 0.0));
            List<String> actions = state.actions();
            double maxVal = Double.NEGATIVE_INFINITY;
            String maxAction = "";
            for (String action : actions){
				double val = qlearner.value(qlearner.getQ(), state, action);
				if (val > maxVal){
    				maxVal = val;
    				maxAction = action;
				} else if (val == maxVal){
					maxAction += action;
				}
            }
            if (qlearner.value(qlearner.getN(), state, maxAction.charAt(0) + "") < 100){
				maxAction = maxAction.toLowerCase();
            }
            labels.put(gridCell, maxAction);
        }
		GridWorld.display(mdp, labels);
	}
}
