
package spritetask;

import java.awt.Color;
import java.util.ArrayList;

public class ExplodingSprite extends Sprite implements Constants
{
    
    public int counter;
    
    public static final int COUNT = 10;
    
    public ExplodingSprite(int x, int y, int w, int h, Color t)
    {
        
        super(x, y, w, h, t);
        
        counter = 0;
        
        velx = Math.random()*20 - 3;
        vely = Math.random()*20 - 3;   
        
    }
    
    @Override
    public void act(ArrayList<Sprite> list)
    {
        
        if (!alive) return;
        
        int R = tint.getRed();
        int G = tint.getGreen();
        int B = tint.getBlue();
        int A = tint.getAlpha();
        R = Math.max(0, R - 0);
        G = Math.max(0, G - 5);
        B = Math.max(0, B - 5);
        
        posx += velx;
        posy += vely;
        
        velx /= 1.1;
        vely /= 1.1;

        tint = new Color(R, G, B, A);

        counter += 1;
        
        if (counter > 50)
        {
            
            explode(list);
            
        }
        
    }
    
    @Override
    public void change(ArrayList<Sprite> list)
    {   
        
        if (alive) explode(list);
        
    }
    
    public void explode(ArrayList<Sprite> list)
    {
        
        alive = false;
            
        for (int i = 0; i < COUNT; i++)
            list.add(new Sprite(posx, posy, 10, 10, tint));
        
    }
    
}
