package solution;
import java.util.Arrays;
import util.State;
import util.Percept;
import util.GridCell;
public class SmallDetailState extends State{
    private String[][] reducedNeighborhood = new String[Percept.NEIGHBORHOOD_SIZE][Percept.NEIGHBORHOOD_SIZE];
	private int s = Percept.NEIGHBORHOOD_SIZE - 1;
    public SmallDetailState(Percept p){
        super(p);
        GridCell[][] neighborhood = p.neighborhood();
        for (int i=0; i<s; i++){
            for (int j=0; j<s; j++){
                reducedNeighborhood[i][j] = getType(neighborhood[i+1][j+1]);
            }
        }
        /*reducedNeighborhood[0][0] = "";
        reducedNeighborhood[0][s-1] = "";
        reducedNeighborhood[s-1][0] = "";
        reducedNeighborhood[s-1][s-1] = "";*/
    }
    /**
     * Encode the type of a grid cell.
     * 
     * @param cell
     *            the grid cell.
     * @return an integer code for the cell type.
     */
    public String getType(GridCell cell)
    {
        if (cell == null)
        {
            return "X";
        }
        else if (cell.isGoal())
        {
            return "+";
        }
        else if (cell.isHole())
        {
            return "-";
        }
        else if (cell.isNormal())
        {
            return " ";
        }
        else
        {
            return "O";
        }
    }
    /**
     * @return true if the state is terminal.
     */
    public boolean isTerminal()
    {
        return terminal;
    }

    @Override
    public void display(){
        for (int i=0; i<s; i++){
            //for (int j=0; j<s; j++){
            //    System.out.printf(reducedNeighborhood[i][j]);
            //}
            System.out.println(String.join("|", reducedNeighborhood[i]));
        }
    }

    @Override
    public String toString(){
        String str = "";
        for (int i=0; i<s; i++){
            for (int j=0; j<s; j++){
                str += reducedNeighborhood[i][j];
            }
            str += "|";
        }
        return str;
    }

    @Override
    public int hashCode()
    {
        return Arrays.deepHashCode(reducedNeighborhood);
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        SmallDetailState other = (SmallDetailState) obj;
        
        return Arrays.deepEquals(this.reducedNeighborhood, other.reducedNeighborhood);
    }
}
