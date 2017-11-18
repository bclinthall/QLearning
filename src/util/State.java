package util;
import java.util.List;
/**
 * The abstract State class.
 * 
 * @author Mitch Parry
 * @version 2016-04-27
 *
 */
public abstract class State
{
    protected boolean terminal;
    protected List<String> actions;

    /**
     * Constructor sets the terminal state.
     * 
     * @param p
     *            the percept
     */
    public State(Percept p)
    {
        terminal = p.current().isTerminal();
        actions = p.actions();
    }

    /**
     * @return true if the state is terminal.
     */
    public boolean isTerminal()
    {
        return terminal;
    }

    /**
     * @return the possible actions.
     */
    public List<String> actions()
    {
        return actions;
    }

    /*
     * You must implement your own hashCode(), equals(), and toString() method. 
     * Eclipse: "Source-->Generate hashCode() and equals()".
     * 
     * You can implement multiple concrete classes that extend this class and 
     * use them in your QLearningPlayer. 
     */

    /**
     * Display the state.
     */
    public abstract void display();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
