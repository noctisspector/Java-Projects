
package tankgame;

import java.io.IOException;

public class TankRanger extends TankScout
{
    
    public TankRanger(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 55;
        projectileSpeed = 21;
        recoilVelocity = 3;
        
        projectileSprite = new Sprite("proj4x", 30);
        
        initialSpread = 3;
        
        salvoCount = 5;
        
        fireOffset[0] = new Vector(30, 0);
        
        projectileVelocityRandomness = 4;
        
    }
    
}
