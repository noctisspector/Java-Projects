
package visualtest;

public class Cell implements Constants
{
    
    public boolean state;
    public Tribe tribe;
    
    //Default
    public Cell()
    {
        state = (int)(Math.random() * 100) < SEED;
        tribe = new Tribe();
    }
    
    //Copies everything
    public Cell(Cell cell)
    {
        state = cell.state;
        tribe = new Tribe(cell.tribe);
    }
    
    //Static copy method
    public static Cell copy(Cell cell)
    {
        return new Cell(cell);
    }
    
}
