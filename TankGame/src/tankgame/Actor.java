
package tankgame;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Actor 
{
    
    public static final int Z_MAX = 10;
    public static final int Z_MIN = -10;
    
    private static final double RES_SCALE = 2;
    private int SPRITE_SIZE;
    private static final int SPRITE_SCALE_TYPE = AffineTransformOp.TYPE_BILINEAR;
    
    private static final int ROTATION_COUNT = 12;
    protected int FRAME_COUNT;
    
    protected String spriteName;
    
    protected int currentFrame = 0;
    
    protected Vector location;
    protected Vector velocity;
    protected Vector acceleration;
    
    protected double MAX_SPEED;
    protected double FRICTION;
    
    protected Vector mouseLocation;
    
    private final BufferedImage[] currentSprites;
    private final BufferedImage[] staticSprites;
    private final BufferedImage[][] rotatedSprites;
    
    private final double[] rotationAngles;
    
    private final double ANGLE_INTERVAL = 360 / ROTATION_COUNT;
    
    protected double angleOverride;
    protected double rotation;
    protected double snappedRotation;
    
    protected boolean hasRotation;
    
    protected int zOrder;
    
    protected boolean hasMaxSpeed;
    
    protected boolean canTakeDamage;
    protected double maxHealth;
    protected double currentHealth;
    
    protected boolean visible;
    protected boolean dead;
    
    private Collision collision;

    public Actor(Sprite sprite, int frameCount_, boolean hasRotation) throws IOException
    {
        
        spriteName = sprite.image;
        FRAME_COUNT = frameCount_;
        SPRITE_SIZE = sprite.size;
        
        this.hasRotation = hasRotation;

        location = new Vector(300, 300);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        
        mouseLocation = new Vector(0, 0);

        rotatedSprites = new BufferedImage[ROTATION_COUNT][FRAME_COUNT];
        rotationAngles = new double[ROTATION_COUNT];
        
        currentSprites = new BufferedImage[FRAME_COUNT];
        staticSprites = new BufferedImage[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++)
            staticSprites[i] = ImageIO.read(new File(spriteName + (i + 1) + ".png"));
        
        angleOverride = 0;
        
        zOrder = 0;
        
        rotation = 0;
        snappedRotation = 0;
        FRICTION = 1.1;
        
        hasMaxSpeed = true;
        MAX_SPEED = 7;
        
        maxHealth = 100;
        canTakeDamage = false;
        currentHealth = maxHealth;
        
        visible = true;
        dead = false;
        
        collision = new Collision(location, Collision.CollisionType.CIRCLE, new CollisionData(10, 5, 5));
        
        initDraw();
        
    }
    
    public boolean zEqual(int z)
    {
        return z == zOrder;
    }
    
    public final void setLocation(Vector vector)
    {
        location = new Vector(vector);
    }
    
    public final void setVelocity(Vector vector)
    {
        velocity = new Vector(vector);
    }
    
    public final void setAcceleration(Vector vector)
    {
        acceleration = new Vector(vector);
    }
    
    public final void adjustSpriteSize(double mod)
    {
        SPRITE_SIZE = (int)(mod * SPRITE_SIZE);
    }
    
    public void setMouseLocation(Vector mouseLocation_)
    {
        mouseLocation = new Vector(mouseLocation_);
    }
    
    private void initDraw()
    {
        
        for (int frame = 0; frame < FRAME_COUNT; frame++)
        {
        
            double angle = 0;

            for (int i = 0; i < ROTATION_COUNT; i++)
            {
                
                if (hasRotation)
                {

                    final double radians = Math.toRadians(angle);
                    final double sin = Math.abs(Math.sin(radians));
                    final double cos = Math.abs(Math.cos(radians));

                    final int width = (int)Math.floor(staticSprites[frame].getWidth() * cos + staticSprites[frame].getHeight() * sin);
                    final int height = (int)Math.floor(staticSprites[frame].getWidth() * sin + staticSprites[frame].getHeight() * cos);

                    final BufferedImage rotatedImage = new BufferedImage(width, height, staticSprites[frame].getType());

                    final AffineTransform at = new AffineTransform();

                    final double rightScale = 0.78;

                    at.translate(width / 2, height / 2);
                    at.scale(RES_SCALE, RES_SCALE);
                    if (radians % (Math.PI / 2) == 0) at.scale(rightScale, rightScale);
                    at.rotate(radians, 0, 0);
                    at.translate(- staticSprites[frame].getWidth() / 2, - staticSprites[frame].getHeight() / 2);


                    final AffineTransformOp rotateOP = new AffineTransformOp(at, SPRITE_SCALE_TYPE);

                    rotateOP.filter(staticSprites[frame], rotatedImage);

                    rotatedSprites[i][frame] = rotatedImage;
                    rotationAngles[i] = angle;

                    angle += ANGLE_INTERVAL;
                
                }
                
                else
                {
                    
                    rotatedSprites[i][frame] = staticSprites[frame];
                    rotationAngles[i] = 0;
                    
                }

            }
        
        }

    }
    
    public void draw(Graphics g, JFrame frame)
    {
        
        if (!visible) return;

        drawSub(g, frame, angleOverride);
        
    }
    
    protected void drawSub(Graphics g, JFrame frame, double angle)
    {
        
        for (int i = 0; i < ROTATION_COUNT; i++)
            if (Math.toDegrees(angle) + ANGLE_INTERVAL / 2 > rotationAngles[i])
            {
                rotation = Math.toDegrees(angle);
                snappedRotation = rotationAngles[i];
                currentSprites[currentFrame] = rotatedSprites[i][currentFrame];
            }

        //Calculate size
        int size = SPRITE_SIZE;
        final int d = size / 2;
        
        g.drawImage(currentSprites[currentFrame], location.getIntX() - d, location.getIntY() - d, size, size, frame);
        //g.drawImage(staticIdleSprite, location.getIntX() + 100, location.getIntY(), size, size, frame);
        
    }
    
    public void tick()
    {
        
        location.setX(location.getX() + velocity.getX());
        location.setY(location.getY() + velocity.getY());
        
        velocity.setX(velocity.getX() + acceleration.getX());
        velocity.setY(velocity.getY() + acceleration.getY());

        if (velocity.vectorLength() > MAX_SPEED && hasMaxSpeed) velocity.resize(MAX_SPEED);
        
        velocity.setX(velocity.getX() / FRICTION);
        velocity.setY(velocity.getY() / FRICTION);
        
    }
    
    public void destroy()
    {
        if (dead) return;
        visible = false;
        dead = true;
    }
    
    public void damage(double amount)
    {
        currentHealth -= amount;
        if (currentHealth <= 0) destroy();
    }
    
    public Vector getLocation() { return new Vector(location); }
    public Vector getVelocity() { return new Vector(velocity); }
    public Vector getAcceleration() { return new Vector(acceleration); }
    
}
