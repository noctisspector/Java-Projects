
package spritetask;

import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.util.ArrayList;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.Timer; 
 
 
public class Visual implements ActionListener, KeyListener, Constants {
 
    private JFrame frame;       //REQUIRED! The outside shell of the window
    public DrawingPanel panel;  //REQUIRED! The interior window
    private Timer visualtime;   //REQUIRED! Runs/Refreshes the screen. 
   
    //any other variables needed, go here.
    public ArrayList<Sprite> effects;
   
 
    public Visual()
    {
        //This block of code is fairly constant for all visual applications
        //Just change the "Visual Java Goodness" text and change the Dimensions (if you want)

        frame = new JFrame("Sprite Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(WIDE, HIGH)); //width, height in #pixels.
        frame.getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
 
        
        //Initialize all global variables here:  
        Initialize();
 
        
        //This block of code is fairly constant too -- always have it.
        visualtime = new Timer(20, this);     
        visualtime.start();   //actionperformed() begins and repeats every 20 clicks
    } 
 
    public final void Initialize()
    {
        //Initialize all global variables here:  
        effects = new ArrayList<Sprite>();

    }
  
    @Override
    public void actionPerformed(ActionEvent e)
    {    
        
        panel.repaint();  //REQUIRED: refreshes the output screen
        
        
        for (int i = 0; i < effects.size(); i++) effects.get(i).act(effects);
        
    }
 
    @Override
    public void keyPressed(KeyEvent e)  //This are commands that execute anytime 
    {                                   //  you press a key.  You can put more of these
                                        //  in your code, if you want them - just follow the patter
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            
        }

        if(e.getKeyCode() == KeyEvent.VK_HOME)
        {
            Initialize();
	}    

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0); 
        }
        
        int x = (int)(Math.random()*WIDE);
        int y = (int)(Math.random()*HIGH);
        
        if(e.getKeyCode() == KeyEvent.VK_1)
            effects.add(new Sprite(x, y, 10, 10, Color.GREEN));
        
        
        if(e.getKeyCode() == KeyEvent.VK_2)
            effects.add(new BouncerSprite(x, y, 10, 10, Color.YELLOW));
        
        if(e.getKeyCode() == KeyEvent.VK_3)
            effects.add(new ExplodingSprite(x, y, 20, 20, Color.WHITE));

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            for(int i = 0; i < effects.size(); i++)
                effects.get(i).change(effects);
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {  }   //not used
    @Override
    public void keyReleased(KeyEvent e) {  }//not used
        
//BIG NOTE:  The coordinate system for the output screen is as follows:     
//  (x,y) = (0, 0) is the TOP LEFT corner of the output screen;    
//  (x,y) = (WIDE, 0) is the TOP RIGHT corner of the output screen;     
//  (x,y) = (0, HIGH) is the BOTTOM LEFT corner of the screen;   
//  (x,y) = (WIDE, HIGH) is the BOTTOM RIGHT corner of the screen;


//REMEMBER::
// Strings are referenced from their BOTTOM LEFT corner.
// Virtually all other objects (Rectangles, Ovals, Images...) are referenced from their TOP LEFT corner.


    private class DrawingPanel extends JPanel implements Constants { 
        @Override
        public void paintComponent(Graphics g)         
        {
            super.paintComponent(g);
            panel.setBackground(Color.black);
            
            for (int i = 0; i < effects.size(); i++) effects.get(i).draw(g);

            //governs all of the drawing on the screen
            //called everytime repaint is called.
            
	    //g.setColor(Color.GREEN);
	    //g.drawString("word", xpos, ypos);
	    //g.drawRect(xpos, ypos, width, height);
	    //g.fillRect(xpos, ypos, width, height);
            
        }
    }
}
