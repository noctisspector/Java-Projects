
package tankgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UnlockIcon 
{
    
    private static final int ICON_SIZE = 100;
    
    private final BufferedImage staticSprite;
    private final Vector location;
    private final int size;
    public static int phase;
    
    public static boolean phase1;
    public static boolean phase2;
    
    private boolean visibility;
    
    public static ArrayList<UnlockIcon> items;
    
    public UnlockIcon(Sprite sprite, Vector location) throws IOException
    {
        
        staticSprite = ImageIO.read(new File(sprite.image + ".png"));
        size = sprite.size;
        
        this.location = new Vector(location);
        
        visibility = false;

    }

    public void show()
    {
        visibility = true;
    }
    
    public void hide()
    {
        visibility = false;
    }
    
    public void draw(Graphics g, JFrame frame)
    {
        
        if (!visibility) return;
        
        final int d = size / 2;
        g.drawImage(staticSprite, location.getIntX() - d, location.getIntY() - d, size, size, frame);
        
    }
    
    
    
    
    //Static Content
    
    public static void init() throws IOException
    {
        
        items = new ArrayList<>();
        items.add(new UnlockIcon(new Sprite("iconAssault", ICON_SIZE), new Vector(60, 40)));
        items.add(new UnlockIcon(new Sprite("iconGunner", ICON_SIZE), new Vector(180, 40)));
        items.add(new UnlockIcon(new Sprite("iconSupport", ICON_SIZE), new Vector(60, 100)));
        items.add(new UnlockIcon(new Sprite("iconScout", ICON_SIZE), new Vector(180, 100)));
        
        phase = 0;
        
        phase1 = false;
        phase2 = false;
        
    }
    
    public static void phaseOneBegin()
    {

        phase = 1;
        
        assaultIcon().show();
        gunnerIcon().show();
        supportIcon().show();
        scoutIcon().show();
        
    }
    
    public static UnlockIcon assaultIcon() { return items.get(0); }
    public static UnlockIcon gunnerIcon() { return items.get(1); }
    public static UnlockIcon supportIcon() { return items.get(2); }
    public static UnlockIcon scoutIcon() { return items.get(3); }
    
}
