package skeleton;

import util.Percept;
import util.QLearner;
import util.State;
import skeleton.MyState;
import java.util.List;

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

    @Override
    protected double explorationFunction(State state, String action)
    {
        if (value(getN(), state, action) < 100.0){
            return Double.POSITIVE_INFINITY;
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
		
		return 1.0 / (visits );
	}

    /**
     * Plays the game using a Q-Learning agent.
     * 
     * @param percept
     *            the percept.
     * @return the desired action.
     */
    public String play(Percept percept)
    {
        State current = new MyState(percept);
        List<String> actions = current.actions();
        if (s != null) {
            if (s.isTerminal()){
                putValue(getQ(), s, "", percept.reward());
                //q.put(s, "", percept.reward());
            }
			addValue(getN(), s, a, 1.0);
			//int newNsa x= nsa.increment(s, a);
            double newQ = percept.gamma() * maxValue(current, actions) - value(getQ(), s, a);
            newQ += percept.reward();
            newQ *= value(getN(), s, a) * alpha(s, actions);
            newQ += value(getQ(), s, a);
            putValue(getQ(), s, a, newQ);
        }
		s = current;
		r = percept.reward();
		a = maxExplorationAction(current, current.actions());
        return a;
    }

}
