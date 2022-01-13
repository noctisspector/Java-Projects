
package tankgame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PowerCube extends Actor implements Constants
{
    
    private static final double spawnChance = 0.003;
    private static final int maxPowerCubes = 10;
    private static int powerCubeCount = 0;
    private static final int MARGIN = 20;
    private static final double RAND_VELOCITY = 1.5;
    private static final int MAX_LIFE = 800;
    
    private int timer;
    
    public PowerCube() throws IOException
    {
        
        super(new Sprite("powerCube", 40), 1, true);
        
        powerCubeCount++;
        
        int randX = (int)(Engine.random(MARGIN, WIDE - MARGIN));
        int randY = (int)(Engine.random(MARGIN, HIGH - MARGIN));
        location = new Vector(randX, randY);
        
        randX = (int)(Engine.random(-RAND_VELOCITY, RAND_VELOCITY));
        randY = (int)(Engine.random(-RAND_VELOCITY, RAND_VELOCITY));
        velocity = new Vector(randX, randY);
        
        angleOverride = Math.toRadians(Math.random() * 360);
        
        canTakeDamage = true;
        maxHealth = 100;
        currentHealth = maxHealth;

        FRICTION = 1;
        
        timer = 0;
        
    }
    
    @Override
    public void destroy()
    {
        if (dead) return;
        super.destroy();
        powerCubeCount--;
    }
    
    @Override
    public void tick()
    {
        
        super.tick();
        timer++;
        
        if (timer > MAX_LIFE) destroy();
        
    }

    public static void staticTick()
    {
        
        //Spawn random power cubes
        if (Math.random() < spawnChance && powerCubeCount < maxPowerCubes)
        {
            
            try 
            {
                Engine.spawnActorFromClass(new PowerCube());
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(PowerCube.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}
