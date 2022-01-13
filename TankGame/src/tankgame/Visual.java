
package tankgame;

import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Graphics;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.Timer; 
 
 
public class Visual implements ActionListener, KeyListener, MouseListener, MouseMotionListener, Constants  
{
    
    public JFrame frame;        
    public DrawingPanel panel;  
    public Timer visualtime;    
    public int delaycount; 
    
    public static ArrayList<Actor> actors;
    
    public Tank player;
    
    public int tankIndex;
    
    public Visual() throws IOException
    {
        
        frame = new JFrame(APPLICATION_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(WIDE, HIGH)); 
        frame.getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        Reset();

        visualtime = new Timer(20, this);     
        visualtime.start();
        
    } 

    public void switchPlayer(Tank tank)
    {
        
        Vector tempLocation = new Vector(player.getLocation());
        Vector tempVelocity = new Vector(player.getVelocity());
        Vector tempAcceleration = new Vector(player.getAcceleration());
        Vector tempMovementKeyState = new Vector(player.getMovementKeyState());
        boolean[] tempMovementPressingState = player.getMovementPressingState();
        player.releaseFire();
        
        actors.remove(tankIndex);
        
        player = tank;
        actors.add(player);
        
        tankIndex = actors.size() - 1;
        
        player.setLocation(tempLocation);
        player.setVelocity(tempVelocity);
        player.setAcceleration(tempAcceleration);
        player.setMovementKeyState(tempMovementKeyState);
        player.setMovementPressingState(tempMovementPressingState);
        
    }

    public final void Reset() throws IOException    
    {        
        
        actors = new ArrayList<>();
        
        player = Tank.newTankHunter();
        
        actors.add(player);
        
        UnlockIcon.init();
        UnlockIcon.phaseOneBegin();
        
        tankIndex = 0;
        
    }
    
        
    @Override
    public void actionPerformed(ActionEvent e)
    {   
        
        if(delaycount < 50) delaycount++;
        
        //Tick
        for (int i = actors.size() - 1; i >= 0; i--)
        {
            actors.get(i).tick();
            PowerCube.staticTick();
        }


        panel.repaint();
        
    }
 
    @Override
    public void keyPressed(KeyEvent e)  
    {            
        if (e.getKeyCode() == KeyEvent.VK_HOME);

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
           System.exit(0); 
        
        if (e.getKeyCode() == KeyEvent.VK_W) player.movementInput(new Vector(0, -1), 0);
        if (e.getKeyCode() == KeyEvent.VK_S) player.movementInput(new Vector(0, 1), 1);
        if (e.getKeyCode() == KeyEvent.VK_A) player.movementInput(new Vector(-1, 0), 2);
        if (e.getKeyCode() == KeyEvent.VK_D) player.movementInput(new Vector(1, 0), 3);
        
        if (e.getKeyCode() == KeyEvent.VK_1) 
        {
            tankIconClickP1(1);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_2) 
        {
            tankIconClickP1(2);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_3) 
        {
            tankIconClickP1(3);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_4) 
        {
            tankIconClickP1(4);
        }
        
    }
    
    private void tankIconClickP1(int selected)
    {
        
        if (UnlockIcon.phase != 1) return;
        if (UnlockIcon.phase1) return;
        
        UnlockIcon.phase1 = true;
        
        try 
        {

            UnlockIcon.assaultIcon().hide();
            UnlockIcon.gunnerIcon().hide();
            UnlockIcon.supportIcon().hide();
            UnlockIcon.scoutIcon().hide();

            switch (selected)
            {
                case 1: switchPlayer(Tank.newTankAssault()); break;
                case 2: switchPlayer(Tank.newTankGunner()); break;
                case 3: switchPlayer(Tank.newTankSupport()); break;
                case 4: switchPlayer(Tank.newTankScout()); break;
            }

        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Visual.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) 
    {
    
        if (e.getKeyCode() == KeyEvent.VK_W) player.movementReleaseInput(new Vector(0, 1), 0);
        if (e.getKeyCode() == KeyEvent.VK_S) player.movementReleaseInput(new Vector(0, -1), 1);
        if (e.getKeyCode() == KeyEvent.VK_A) player.movementReleaseInput(new Vector(1, 0), 2);
        if (e.getKeyCode() == KeyEvent.VK_D) player.movementReleaseInput(new Vector(-1, 0), 3);
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) 
    {
        
        if (e.getButton() == MouseEvent.BUTTON1) player.pressFire();
        
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        
        player.releaseFire();
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) 
    {
    
       if(delaycount < 20) return;
       player.setMouseLocation(new Vector(e.getX(), e.getY()));
    
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        
        if(delaycount < 20) return;
        player.setMouseLocation(new Vector(e.getX(), e.getY()));
        
    }
 
    private class DrawingPanel extends JPanel implements Constants { 
               
        @Override
        public void paintComponent(Graphics g)         
        {
            
            super.paintComponent(g);
            panel.setBackground(Color.darkGray);

            if(delaycount < 20) return;

            for (int z = Actor.Z_MIN; z <= Actor.Z_MAX; z++)
                for (Actor actor : actors)
                    if (actor.zEqual(z))
                        actor.draw(g, frame);
            
            for (UnlockIcon unlockIcon : UnlockIcon.items)
                unlockIcon.draw(g, frame);

        }
        
    }
}
