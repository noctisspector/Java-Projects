
package tankgame;

import java.io.IOException;

public class TankDestroyer extends TankSupport
{
    
    public TankDestroyer(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 80;
        projectileSpeed = 30;
        recoilVelocity = 3;
        
        initialSpread = 1;
        
        projectileSprite = new Sprite("proj4x", 60);
        
        projectileVelocityRandomness = 8;
        
    }
    
}
