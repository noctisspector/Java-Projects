
package spritetask;

import java.awt.Color; 
import java.awt.Graphics; 
import java.util.ArrayList;

public class Sprite implements Constants
{
    //The Sprite is a oval that takes a random path on the screen.
    //It also begins to fade out over time.

    public boolean alive;
    public int type;

    public int posx;
    public int posy;
    public double velx;
    public double vely;

    public int recw;
    public int rech;
    public Color tint;
    public Color tintoriginal;
    
    public boolean collision;

    //The 'type' integer is used to track which class you have (0=Parent).  
    //If you are doing the Advancded Interaction Bonus/Challenge, you can use
    //   this number to determine if a bucket of the ArrayList holds a Sprite
    //   Object or a Child Class Object... you might create certain behaviors that
    //   depend on which object something is.    


    public Sprite(int x, int y, int w, int h, Color t)
    {
        alive = true;
        type = 0;  //Parent = 0, Children = n (some other unique number to that class)

	posx = x;
	posy = y;
	recw = w;
	rech = h;
	tint = t;

        //the next two lines give the object a random velocity:
        velx = Math.random()*5 - 2;
        vely = Math.random()*5 - 2;        
        tintoriginal = t;  //stores the color for the change() method
        
        collision = false;
    }	

    public void act(ArrayList<Sprite> list)
    {
        if(!alive) return;  //dead object don't act.

        //darkens the color by 5 and moves the object in the velocity direction...
        int R = tint.getRed();
        int G = tint.getGreen();
        int B = tint.getBlue();
        int A = tint.getAlpha() - 1;
        if(A <= 1) 
        {
            A = 0;
            alive = false;  //when it becomes transparent, it "dies" off
        }

        tint = new Color(R, G, B, A);

        posx += velx;
        posy += vely;
        //This will eventually make it go off the screen...
        //You could add some if-statements here to make it "bounce" or wrap-around
        //to the other side of the screen, or "kill" (alive = false) it if it goes off:  
        //if(posx > WIDE) posx = 0;  ...


        //PS.  Notice that the ArrayList is one of the parameters, but it's never
        // used in this code... that's because you may want access to it in at 
        // least one Child Class.  So I planned ahead and included it here so there are
        // no problems with the Inheritance and Polymorphism.
    }
    
    public void change(ArrayList<Sprite> list)
    {   //resets the tint back to original and changes direction:

        tint = tintoriginal;  
        velx = Math.random()*5 - 2;
        vely = Math.random()*5 - 2;  
    }

    public void draw(Graphics g)
    {
        
        if(!alive) return;
        g.setColor(tint);
        g.fillOval(posx, posy, recw, rech);
      
    }
    
}
