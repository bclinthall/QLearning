package solution;

import util.Percept;
import util.QLearner;
import util.State;
import solution.MyState;
import java.util.Map;
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
	private MyHashMap<Double> q;
	private Nsa nsa;

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
        q = new MyHashMap<>(Double.valueOf(0));
        nsa = new Nsa();
    }
    
	private String chooseAction(State current){
		double maxF = Double.NEGATIVE_INFINITY;
		String optAction = "";
		for (String action : current.actions()){
			double thisF = explorationFunction(current, action);
			if (thisF > maxF){
    			maxF = thisF;
    			optAction = action;
			}
		}
		return optAction;
	}

    @Override
    protected double explorationFunction(State state, String action)
    {
        if (nsa.get(state, action) < 100){
            return Double.POSITIVE_INFINITY;
        } else {
            return q.get(state, action);
        }
    }

	/**
	 * Calculates the maximum action value
	 * @param currentState
	 * 		  the current state
	 * @return the highest Q[currentState, action] for any available action.
	 */
    public double maxActionValue(State currentState){
        double maxVal = Double.NEGATIVE_INFINITY;
		for (String action : currentState.actions()){
			double actionVal = q.get(currentState, action);
			if (actionVal > maxVal){
    			maxVal = actionVal;
			}
		}
		return maxVal;
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
        
        if (s != null) {
            if (s.isTerminal()){
                q.put(s, "", percept.reward());
            }
			int newNsa = nsa.increment(s, a);
            double newQ = percept.gamma() * maxActionValue(current) - q.get(s, a);
            newQ += percept.reward();
            newQ *= nsa.getNormalized(s, a);
            newQ += q.get(s, a);
            q.put(s, a, newQ);
        }
		s = current;
		r = percept.reward();
		a = chooseAction(current);
        return a;
    }

}
class MyHashMap<V> extends HashMap<String, V>{
    protected V defaultValue;
	public MyHashMap(V defaultValue){
    	this.defaultValue = defaultValue;
	}

	public String makeKey(State state, String action){
    	return state + action;
	}
    public void put(State state, String action, V value){
		super.put(makeKey(state, action), value);
    }
    @Override
    public V get(Object k){
		if (containsKey(k)){
    		return super.get(k);
		}else{
    		return defaultValue;
		}
    }
	public V get(State state, String action) {
		String k = makeKey(state, action);
		return get(k);
	}
}
class Nsa extends MyHashMap<Integer>{
	private int total = 0;
	public Nsa(){
		super(0);
	}
	public int increment(State s, String a){
		total++;
		String k = makeKey(s, a);
		int newFreq = super.get(k) + 1;
		super.put(k, newFreq);
		return newFreq;
	}
	public double getNormalized(State s, String a){
		return (double)super.get(s, a)/(double)total;
	}
}
