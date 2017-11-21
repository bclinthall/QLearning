package solution;

import util.Percept;
import util.QLearner;
import util.State;
import solution.MyState;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

/**
 * An agent that uses value iteration to play the game.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public class MyQLearner extends QLearner
{
    private static final boolean DEBUG = false;
    private static final double NE = 100.0;
    private State s;  // previous state
    private String a; // previous action
    private double r; // previous reward

    /**
     * The constructor takes the name.
     * 
     * @param name
     *            the name of the player.
     */
    public MyQLearner(String name)
    {
        super(name);
        s = null;
        a = null;
        r = Double.NEGATIVE_INFINITY;
    }

    /*
	private String chooseAction(State current){
    	
		
		double maxF = Double.NEGATIVE_INFINITY;
		String optAction = "";
		for (String action : current.actions()){
			double thisF = maxExplorationFunction(current, action);
			if (thisF > maxF){
    			maxF = thisF;
    			optAction = action;
			}
		}
		return optAction;
		
	}*/

	double chance= 1.0/50.0;
    @Override
    protected double explorationFunction(State state, String action)
    {
        double val = value(getN(), state, action);
        if (val < 100.0){
            return Double.POSITIVE_INFINITY;
       // } else if (Math.random() < chance)//{
       //     return Double.NEGATIVE_INFINITY;//
        } else {
			return value(getQ(), state, action);
            //return q.get(state, action);
        }
    }
	public double alpha(State current, List<String> actions){
		double visits = 0.0;
		for (String action : actions){
    		visits += value(getN(), s, action);
		}
		
		//return 90.0 / (99.0+visits) + 0.1;
		return 1.0 / (1.0+visits);
	}

    /**
     * Plays the game using a Q-Learning agent.
     * 
     * @param percept
     *            the percept.
     * @return the desired action.
     */
     List<String> actions = Arrays.asList(new String[]{"N","S","E","W"});
    public String play(Percept percept)
    {
//        if(s != null && s.isTerminal()){
//			newGame();
//        }
        State current = getState(percept);
        //List<String> actions = current.actions();
		double reward = percept.current().reward();
        if (current.isTerminal()){
            for (String action : actions){
				putValue(getQ(), current, action, reward);
            }
        }
        if (s != null) {
			addValue(getN(), s, a, 1.0);
            double newQ = percept.gamma() * maxValue(current, actions); //Utility at this state, discounted by one
            newQ += r; 					 		//Plus reward at previous
            newQ -= value(getQ(), s, a);		//Difference from old value
            newQ *= alpha(s, actions);			//Take a percentage of that
            newQ += value(getQ(), s, a);		//Add it to the old value
            putValue(getQ(), s, a, newQ);
        }
        if (current.isTerminal()){
			return null;
        }
		r = reward;
		a = maxExplorationAction(current, actions);
		s = current;
        return a;
    }
    State getState(Percept percept){
        return new MyState(percept);
    }
    @Override
    public void newGame(){
        s = null;
        a = null;
        r = Double.NEGATIVE_INFINITY;
    }

}
