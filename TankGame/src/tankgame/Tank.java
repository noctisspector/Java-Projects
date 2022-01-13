
package tankgame;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Tank extends Actor
{
    
    private boolean pressingFire;
    
    protected int FIRE_DELAY;
    private int fireDelay;
    
    private static final int FIRE_FRAME = 1;
    
    protected int FIRE_ANIM_DELAY;
    private int animDelay;
    private int animState;
    
    private boolean fireAnimation;
    
    private Vector movementKeyState;
    private boolean[] movementPressingStates;
    
    protected double projectileSpeed;
    protected double recoilVelocity;
    
    protected Sprite projectileSprite;
    
    public static final int TANK_SIZE = 200;
    
    protected double initialSpread;
    
    protected Vector[] fireOffset;
    
    protected int salvoCount;
    protected double projectileVelocityRandomness;
    
    protected double baseDamage;
    
    public static final String ID_tank = "tank";
    
    public static final String ID_assault = "tankAssault";
    public static final String ID_support = "tankSupport";
    public static final String ID_scout = "tankScout";
    public static final String ID_gunner = "tankGunner";
    
    public static final String ID_predator = "tankPredator";
    public static final String ID_stalker = "tankStalker";
    
    public static final String ID_defender = "tankDefender";
    public static final String ID_destroyer = "tankDestroyer";
    
    public static final String ID_hunter = "tankHunter";
    public static final String ID_ranger = "tankRanger";

    public Tank(Sprite sprite) throws IOException
    {
        
        super(sprite, 4, true);
        
        pressingFire = false;
        fireAnimation = false;
        fireDelay = FIRE_DELAY; 
        
        animDelay = FIRE_ANIM_DELAY;
        animState = 0;
        
        movementKeyState = new Vector(0, 0);
        
        movementPressingStates = new boolean[4];
        for (int i = 0; i < 4; i++)
            movementPressingStates[i] = false;
        
        zOrder = 2;
        
        projectileSpeed = 20;
        recoilVelocity = 3;
        
        projectileSprite = new Sprite("proj4x", 80);
        
        FIRE_DELAY = 15;
        
        initialSpread = 3;
        
        fireOffset = new Vector[1];
        fireOffset[0] = new Vector(20, 0);
        
        FIRE_ANIM_DELAY = 2;
        
        salvoCount = 1;
        
        projectileVelocityRandomness = 1.5;
        
        baseDamage = 45;
        
    }
    
    public void setMovementKeyState(Vector vector)
    {
        movementKeyState = new Vector(vector);
    }
    
    public void setMovementPressingState(boolean[] bool)
    {
        movementPressingStates = bool;
    }
    
    public Vector getMovementKeyState() { return new Vector(movementKeyState); }
    
    public boolean[] getMovementPressingState() { return movementPressingStates; }
    
    public void movementInput(Vector vector, int index)
    {
        
        if (!movementPressingStates[index])
        {
            movementPressingStates[index] = true;
            movementKeyState = Vector.add(movementKeyState, vector);
        }
        
        updateMovement();
        
    }
    
    public void movementReleaseInput(Vector vector, int index)
    {
        
        if (movementPressingStates[index])
        {
            movementPressingStates[index] = false;
            movementKeyState = Vector.add(movementKeyState, vector);
        }
        
        updateMovement();
        
    }
    
    public void updateMovement()
    {
        acceleration = new Vector(movementKeyState);
    }
    
    public void pressFire()
    {
        pressingFire = true;
    }
    
    public void releaseFire()
    {
        pressingFire = false;
    }
    
    @Override
    public void draw(Graphics g, JFrame frame)
    {
        
        Vector mouseResultant = Vector.add(mouseLocation, Vector.invert(location));
        
        double angle = mouseResultant.getAngle();
        
        drawSub(g, frame, angle);
        
    }
    
    @Override
    public void tick()
    {
        
        super.tick();
        
        //Shooting
        fireDelay++;
        
        if (pressingFire && fireDelay > FIRE_DELAY) 
        {
            
            fireDelay = 0;
            
            fireAnimation = true;
            animDelay = FIRE_ANIM_DELAY;
            
        }
        
        //Animating
        if (fireAnimation)
        {
            
            if (animDelay >= FIRE_ANIM_DELAY)
            {
                
                animDelay = 0;
                
                if (animState >= FRAME_COUNT - 1)
                {
                    fireAnimation = false;
                    currentFrame = 0;
                    animState = 0;   
                }
                
                else
                {
                    
                    animState++;
                    currentFrame++;
                    
                    //Shoot
                    if (animState == FIRE_FRAME)
                    {
                        Vector initialVelocity = new Vector(velocity);
                        
                        for (int j = 0; j < salvoCount; j++)
                            for (int i = 0; i < fireOffset.length; i++)
                                try 
                                {
                                    shoot(fireOffset[i], initialVelocity);
                                } 

                                catch (IOException ex) 
                                {
                                    Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
                                }
                    }

 
                }
                
            }
            
            animDelay++;
            
        }
        
    }
    
    private void shoot(Vector inputOffset, Vector initialVelocity) throws IOException
    {
        
        double desiredRotation = snappedRotation;
        Vector randomVel = new Vector(0, Engine.random(-projectileVelocityRandomness, projectileVelocityRandomness));
        
        final double spread  = Engine.random(-initialSpread, initialSpread);
        
        Vector projVelocity = new Vector(0, projectileSpeed);
        projVelocity = Vector.add(projVelocity, randomVel);
        projVelocity.setAngle(desiredRotation + spread);
        projVelocity = Vector.add(projVelocity, initialVelocity);

        Projectile projectile = (Projectile)Engine.spawnActorFromClass(new Projectile(projectileSprite, 1, projVelocity));
        
        Vector offset = new Vector(inputOffset);
        
        offset.addAngle(desiredRotation);
        
        Vector finalOffset = Vector.add(location, offset);
        
        projectile.setLocation(finalOffset);
        
        projectile.setBaseDamage(baseDamage);
        
        //Recoil
        Vector recoil = new Vector(0, recoilVelocity);
        recoil.setAngle(desiredRotation - 180);
        velocity = Vector.add(velocity, recoil);

    }
    
    public static Tank newTank() throws IOException { return new Tank(new Sprite(ID_tank, TANK_SIZE)); }
    
    public static Tank newTankAssault() throws IOException { return new TankAssault(new Sprite(ID_assault, TANK_SIZE)); }
    public static Tank newTankSupport() throws IOException { return new TankSupport(new Sprite(ID_support, TANK_SIZE)); }
    public static Tank newTankScout() throws IOException { return new TankScout(new Sprite(ID_scout, TANK_SIZE)); }
    public static Tank newTankGunner() throws IOException { return new TankGunner(new Sprite(ID_gunner, TANK_SIZE)); }
    
    public static Tank newTankPredator() throws IOException { return new TankPredator(new Sprite(ID_predator, TANK_SIZE)); }
    public static Tank newTankStalker() throws IOException { return new TankStalker(new Sprite(ID_stalker, TANK_SIZE)); }
    
    public static Tank newTankDefender() throws IOException { return new TankDefender(new Sprite(ID_defender, TANK_SIZE)); }
    public static Tank newTankDestroyer() throws IOException { return new TankDestroyer(new Sprite(ID_destroyer, TANK_SIZE)); }
    
    public static Tank newTankHunter() throws IOException { return new TankHunter(new Sprite(ID_hunter, TANK_SIZE)); }
    public static Tank newTankRanger() throws IOException { return new TankRanger(new Sprite(ID_ranger, TANK_SIZE)); }
    
}
