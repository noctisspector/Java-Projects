
package tankgame;

import java.io.IOException;

public class TankAssault extends Tank
{
    
    public TankAssault(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 8;
        projectileSpeed = 17;
        recoilVelocity = 1;
        
        projectileSprite = new Sprite("proj4x", 50);
        
        MAX_SPEED = 9;
        
        initialSpread = 5;
        
        fireOffset[0] = new Vector(40, 0);
        
    }
    
}
