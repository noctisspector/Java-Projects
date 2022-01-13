
package visualtest;

import java.awt.Color;

public interface Constants 
{

    //-----------------------
    //---General Settings----
    //-----------------------
    
    public static final int ROW = 800;  
    public static final int COL = 1200;  
    public static final int RES = 1;  
    /*
    Size of the game board & screen resolution
    
    High Res: 800, 1200, 1
    Mid Res: 100, 150, 8
    Low Res: 40, 60, 15
    
    */
    
    public static final boolean GRID = false;
    /*
    Whether to display the grid or not
    */

    public static final int SEED = 30;
    /*
    DEFAULT VALUE = 30
    % of cells alive at the start of the game.
    0 for a blank screen.
    */
    
    public static final int TRIBE_COUNT = 3;
    /*
    Number of unique tribes. Will be assigned random colors.
    */
    
    public static final int DRAW_RADIUS = 50;
    /*
    DEFAULT VALUE = 1
    Radius of draw brush. Values 0+
    */
    
    //-----------------------
    //---Behavior Settings---
    //-----------------------
    
    public static final Range FREEDOM = new Range(4, 8);
    /*
    DEFAULT VALUE = 8
    Value 0 - 8 where...
    0 = Only reproduce when only one tribe is present.
    8 = Tribes are not a factor in reproduction.
    */
    
    public static final int REPRODUCTIVE_CONDITION = 3;
    /*
    DEFAULT VALUE = 3
    Value 0 - 8 where...
    Specific number of neighboors for reproduction
    */
    
    public static final Range LONLINESS = new Range(1, 1);
    /*
    DEFAULT VALUE = 1
    Value 0 - 8 where...
    Number of neighboors (or less) that will cause the death of a cell.
    */
    
    public static final Range FAMILIAR_STRESS = new Range(5, 9);
    /*
    DEFAULT VALUE = 7
    Value 0 - 9 where...
    Number of neighboors (or more) that will cause the death of a cell (if they are all one tribe).
    */
    
    public static final Range UNFAMILIAR_STRESS = new Range(4, 4);
    /*
    DEFAULT VALUE = 4
    Value 0 - 9 where...
    Number of neighboors (or more) that will cause the death of a cell (if they are NOT all one tribe).
    */
    
    //-----------------------
    //---Misc Settings-------
    //-----------------------
    
    public static final int MARGIN = 30;
    
    public static final int WIDE = 2 * MARGIN + COL * RES;  
    public static final int HIGH = 2 * MARGIN + ROW * RES;  

    public static final int WAIT = 0;
    
    public static final Color GRID_TINT = Color.DARK_GRAY;

    public static final Color[] TRIBE_COLORS = new Color[TRIBE_COUNT];
    public static final int[] FREEDOM_LIST = new int[TRIBE_COUNT];
    public static final int[] LONLINESS_LIST = new int[TRIBE_COUNT];
    public static final int[] FAMILIAR_STRESS_LIST = new int[TRIBE_COUNT];
    public static final int[] UNFAMILIAR_STRESS_LIST = new int[TRIBE_COUNT];
    
}
