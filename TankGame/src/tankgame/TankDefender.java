
package tankgame;

import java.io.IOException;

public class TankDefender extends TankSupport
{
    
    public TankDefender(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 80;
        projectileSpeed = 12;
        recoilVelocity = 3;
        
        initialSpread = 20;
        
        projectileSprite = new Sprite("proj4x", 50);
        
        salvoCount = 8;
        
        projectileVelocityRandomness = 8;
        
    }
    
}
