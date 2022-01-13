
package tankgame;

import java.io.IOException;

public class TankStalker extends TankAssault
{
    
    public TankStalker(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 15;
        projectileSpeed = 10;
        recoilVelocity = 6;
        
        projectileSprite = new Sprite("proj4x", 50);
        
        MAX_SPEED = 20;
        
        initialSpread = 10;
        
        fireOffset[0] = new Vector(30, 0);
        
    }
    
}
