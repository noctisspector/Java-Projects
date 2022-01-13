
package tankgame;

import java.io.IOException;

public class Projectile extends Actor
{
    
    private double baseDamage;
    
    public Projectile(Sprite sprite, int frameCount_, Vector velocity) throws IOException
    {
        
        super(sprite, frameCount_, true);
        
        adjustSpriteSize(0.2);
        
        zOrder = 1;
        
        this.velocity = velocity;
        
        FRICTION = 1;
        hasMaxSpeed = false;
        
        baseDamage = 0;

    }
    
    public void setBaseDamage(double baseDamage)
    {
        this.baseDamage = baseDamage;
    }
    
    public void setAngleOverride(double angle) { angleOverride = angle; }
    
}
