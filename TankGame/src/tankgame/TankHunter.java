
package tankgame;

import java.io.IOException;

public class TankHunter extends TankScout
{
    
    public TankHunter(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 40;
        projectileSpeed = 40;
        recoilVelocity = 7;
        
        projectileSprite = new Sprite("proj4x", 65);
        
        initialSpread = 1;
        
        fireOffset[0] = new Vector(50, 0);
        
    }
    
}
