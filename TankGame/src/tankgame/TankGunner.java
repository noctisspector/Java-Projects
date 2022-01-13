
package tankgame;

import java.io.IOException;

public class TankGunner extends Tank
{
    
    public TankGunner(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 15;
        projectileSpeed = 10;
        recoilVelocity = 3;
        
        projectileSprite = new Sprite("proj4x", 50);
        
        initialSpread = 3;
        
        fireOffset = new Vector[2];
        fireOffset[0] = new Vector(20, 15);
        fireOffset[1] = new Vector(20, -15);
        
    }
    
}
