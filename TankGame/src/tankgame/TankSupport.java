
package tankgame;

import java.io.IOException;

public class TankSupport extends Tank
{
    
    public TankSupport(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 45;
        projectileSpeed = 12;
        recoilVelocity = 20;
        
        projectileSprite = new Sprite("proj4x", 100);
        
        MAX_SPEED = 5;
        
    }
    
}
