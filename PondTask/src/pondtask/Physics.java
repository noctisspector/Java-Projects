
package pondtask;

//**************************************************************************
//   Physics Interface			Author:  MurraySV
//   
//   Contains the constants used throughout the pond program.
//**************************************************************************

import java.awt.Color;

public interface Physics {

    //Colors of the pond...
    public static final Color BOARDER = new Color(174, 140, 80);
    public static final Color WATER_DAY = new Color(130, 210, 190);
    public static final Color WATER_NIGHT = new Color(110, 190, 225);
        

    /**
     * How wide the Visual Frame is.  Do not make this larger than your screen's width (1250-1920 for most). 
     */
    public static final int SCREENWIDTH = 1000;

    
    /**
     * How tall the Visual Frame is.  Do not make this larger than your screen's height (760-1080 for most). 
     * If you keep this value proportional to the SCREENWIDTH, you'll have a nicer image.
     */
    public static final int SCREENHEIGHT = 650;
    
    
    /**
     * That amount of delay between refreshing the window ( 50 ~~ 1sec )
     * This is the amount of time between each change in the "hour" in the pond's day.
     * Decrease this value if you want to speed up the output.
     * (Minimum of zero)
     */
    public static final int PAUSE = 1;
    
    
    /**
     * The amount of time added to each hour of the day. 
     * Recommended to keep this == 1
     * If you really want to speed up you pond, decrease PAUSE (above).
     * If your PAUSE is already at 1, then you can increase TIME to 2 or 3 (so the hours increase faster)
     * ... but this could effect how some critters act.
     */
    public static final int TIMECHANGE = 1;
    
    
    /**
     * The size of a "square" in the pond.
     * Keep this proportional to your window.  Roughly 1/50 of the SCREENWIDTH
     */
    public static final int PIX = 20;
    
        
    /**
     * The Actual Pond's Width.
     * Keep this number a multiple of PIX.
     * Keep this number smaller than SCREEN_WIDTH.  
     */
    public static final int PONDWIDTH = 800;      

    
    /**
     * The Actual Pond's Height.
     * Keep this number a multiple of PIX.
     * Keep this number smaller than SCREEN_HEIGHT.  
     */
    public static final int PONDHEIGHT = 500;     
	
    
    /**
     * Rough % of spaces that are filled with rocks at the start
     * Higher percentage == more rocks, too high and there's no room in the pond!
     */
    public static final int PERCENTROCKS = 10;  
	
    
    /**
     * Rough % of spaces that are filled with food at the start
     * Higher percentage == more food, too high and there's no room in the pond!
     */
    public static final int PERCENTFOOD = 10;  
	
    
    /**
     * The chance that new food will show up at the end of any given day
     * Too Low and new food will not show up, too high and food always shows.
     */
    public static final int CHANCENEWFOOD = 85; 	
	    
    
    /**
     * Total (N)umber (O)f (A)nimals in the pond at the start
     * Experiment all you want with this number.  If it's too high, and there is
     *  not enough space in the pond, the remaining critters will not be placed.
     *  So, if you make it too high, it's meaningless.
     * DEFAULT: 40
     * 
     * Standard Pond:
     * DEPOPULATION: 18-
     * OVERPOPULATION: 19+
     * 
     * 
     */
    public static final int NOA = 30;       
	   
}
