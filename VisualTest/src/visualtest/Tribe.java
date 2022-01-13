
package visualtest;

import java.awt.Color; 

public class Tribe implements Constants
{
    
    public int tribeIndex;
    
    public Tribe()
    {
        tribeIndex = (int)(Math.random() * TRIBE_COUNT);
    }
    
    public Tribe(Tribe tribe)
    {
        tribeIndex = tribe.tribeIndex;
    }
    
    public Color getColor()
    {
        return TRIBE_COLORS[tribeIndex];
    }
    
}
