
package spritetask;

import java.awt.Color;
import java.util.ArrayList;

public class BouncerSprite extends Sprite implements Constants
{
    
    public double gravity;
    public static final double BOUNCINESS = 1;
    
    public BouncerSprite(int x, int y, int w, int h, Color t)
    {
        
        super(x, y, w, h, t);
        
        collision = true;
        
        gravity = 0.4;
        
    }
    
    @Override
    public void act(ArrayList<Sprite> list)
    {
        
        if(!alive) return;  

        int R = tint.getRed();
        int G = tint.getGreen();
        int B = tint.getBlue();
        int A = tint.getAlpha() - 1;
        if(A <= 1) 
        {
            A = 0;
            alive = false;  
        }

        tint = new Color(R, G, B, A);
        
        vely += gravity;
        
        posx += velx;
        posy += vely;
        
        //Bounce off ground
        if (posy + rech >= HIGH) wallBounce(false, true);
        
        //Bounce off ceiling
        if (posy <= 0) wallBounce(false, true);
        
        //Bounce off left wall
        if (posx - recw <= 0) wallBounce(true, false);
        
        //Bounce off right wall
        if (posx + recw >= WIDE) wallBounce(true, false);
        
        //Bounce against self
        for (int i = 0; i < list.size(); i++) 
            if (list.get(i).alive && list.get(i) != this && list.get(i).collision)
            {
                
                Sprite temp = list.get(i);

                double testingDistance = pythag(recw/2 + temp.recw/2, rech/2 + temp.rech/2);
                
                if (distanceTo(temp) <= testingDistance)
                {
                    
                    //Bounce this
                    double tempX = velx;
                    double tempY = vely;
                    
                    velx = temp.velx;
                    vely = temp.vely;
                    
                    //Bounce temp
                    temp.velx = tempX;
                    temp.vely = tempY;
                    
                }
                
            }

    }
    
    public double distanceTo(Sprite sprite)
    {
        
        return pythag(posx - sprite.posx, posy - sprite.posy);
        
    }
    
    public double pythag(double a, double b)
    {
        
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        
    }
    
    public void wallBounce(boolean bounceX, boolean bounceY)
    {
        
        if (bounceX) velx *= -1 * BOUNCINESS;
        if (bounceY) vely *= -1 * BOUNCINESS;

    }
    
    @Override
    public void change(ArrayList<Sprite> list)
    {
        
        //gravity *= Math.random() * 6 - 3;
        
        int newR = (int)(Math.random() * 20 + 10);
        
        recw = newR;
        rech = newR;
        
    }
    
}
