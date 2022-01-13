
package tankgame;

import java.io.IOException;

public class TankScout extends Tank
{
    
    public TankScout(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 20;
        projectileSpeed = 30;
        recoilVelocity = 4;
        
        projectileSprite = new Sprite("proj4x", 50);
        
        initialSpread = 1;
        
        fireOffset[0] = new Vector(50, 0);
        
    }
    
}
