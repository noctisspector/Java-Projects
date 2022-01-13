
package tankgame;

import java.io.IOException;

public class TankPredator extends TankAssault
{
    
    public TankPredator(Sprite sprite) throws IOException
    {
        
        super(sprite);
        
        FIRE_DELAY = 1;
        projectileSpeed = 10;
        recoilVelocity = 1;
        
        projectileSprite = new Sprite("proj4x", 30);
        
        MAX_SPEED = 8;
        
        initialSpread = 7;
        
        fireOffset[0] = new Vector(70, 0);
        
        FIRE_ANIM_DELAY = 1;
        
    }
    
}
