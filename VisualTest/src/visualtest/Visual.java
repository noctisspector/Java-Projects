package visualtest;

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
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.Timer; 
 
 
public class Visual implements ActionListener, KeyListener, MouseListener, MouseMotionListener, Constants
        
{
    
    public JFrame frame;        //REQUIRED! The outside shell of the window
    public DrawingPanel panel;  //REQUIRED! The interior window
    public Timer visualtime;    //REQUIRED! Runs/Refreshes the screen. 
    public int delaycount;      //Used to prevent errors during load&run    
    public int waitcount;
    
    public boolean nextTurn;
    public boolean paused;

    public int mouseX;
    public int mouseY;
    
    public int drawTribeIndex;

    //all of your other variables...
    public Cell[][] mainSpace;
    public Cell[][] tempSpace;

    
    public Visual()
    {
        
        frame = new JFrame("  Conway's Game of Life - Visual Work");
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


    public final void Reset()    
    {        
        
        delaycount = 0;
        waitcount = 0;
        mainSpace = new Cell[ROW][COL];
        
        nextTurn = false;
        paused = false;
        
        mouseX = 0;
        mouseY = 0;
        
        drawTribeIndex = 0;
        
        for(int r = 0; r < ROW; r++)
            for(int c = 0; c < COL; c++)
                mainSpace[r][c] = new Cell();
        
        //Generate Tribe Colors
        for (int i = 0; i < TRIBE_COUNT; i++)
        {
            
            int r = (int)(Math.random()*256);
            int g = (int)(Math.random()*256);
            int b = (int)(Math.random()*256);
            
            TRIBE_COLORS[i] = new Color(r,g,b);
            
        }
        
        //Generate Tribe Stats
        for (int i = 0; i < TRIBE_COUNT; i++) FREEDOM_LIST[i] = FREEDOM.getRandom();
        for (int i = 0; i < TRIBE_COUNT; i++) LONLINESS_LIST[i] = LONLINESS.getRandom();
        for (int i = 0; i < TRIBE_COUNT; i++) FAMILIAR_STRESS_LIST[i] = FAMILIAR_STRESS.getRandom();
        for (int i = 0; i < TRIBE_COUNT; i++) UNFAMILIAR_STRESS_LIST[i] = UNFAMILIAR_STRESS.getRandom();
        
        

    }
    
        
    @Override
    public void actionPerformed(ActionEvent e)
    {   
        
        if (paused)
        {
            panel.repaint();
            return;
        }
        
        if(delaycount < 50) delaycount++;   

        waitcount++;
        
        if (nextTurn || waitcount > WAIT)
        {
        
            waitcount = 0;
            nextTurn = false;
            
            //Re-init the temp space
            tempSpace = new Cell[ROW][COL];
            
            for(int r = 0; r < ROW; r++)
                for(int c = 0; c < COL; c++)
                    tempSpace[r][c] = new Cell(mainSpace[r][c]);

            //Update the temp space
            for (int i = 0; i < ROW; i++)
                for (int j = 0; j < COL; j++)
                {

                    boolean onlyOneTribe = true;
                    
                    int[] tribeCounts = new int[TRIBE_COUNT];
                    for (int k = 0; k < TRIBE_COUNT; k++) tribeCounts[k] = 0;
                    
                    int majorityTribe = 0;
                    int majorityTribeCount = 0;
                    
                    int freedom = FREEDOM_LIST[mainSpace[i][j].tribe.tribeIndex];

                    int occupied = 0;
                    /*
                    Count of the number of adjacent buckets -
                    not including itself - in a 3 X 3 grid
                    around the selected bucket
                    */

                    //Traverse through the 3 X 3 grid around the element
                    for (int localRow = i - 1; localRow <= i + 1; localRow++)
                        for (int localColumn = j - 1; localColumn <= j + 1; localColumn++)
                        {

                            //Determines if the element is open
                            if 
                            (
                                localRow >= 0 &&
                                localRow < ROW &&
                                localColumn >= 0 &&
                                localColumn < COL
                            )

                                //If its not the middle
                                if (!(localRow == i && localColumn == j))

                                    //Determines if the bucket is alive
                                    if (mainSpace[localRow][localColumn].state)
                                    {
                                        //Increase the number occupied
                                        occupied++;
                                        
                                        //Increase tribe counts
                                        tribeCounts[mainSpace[localRow][localColumn].tribe.tribeIndex] += 1;

                                    }

                        }
                    
                    //k = tribe index
                    for (int k = 0; k < TRIBE_COUNT; k++)
                    {
                        
                        //Determine first tribe
                        if (tribeCounts[k] != 0 && freedom > 0)
                        {
                            freedom--;
                        }
                        
                        //If another tribe exists
                        if (tribeCounts[k] != 0 && freedom <= 0)
                        {
                            onlyOneTribe = false;
                        }
                        
                        //If there is more of this tribe, make it the majority
                        if (tribeCounts[k] > majorityTribeCount) 
                        {
                            majorityTribeCount = tribeCounts[k];
                            majorityTribe = k;
                        }
                        
                    }

                    //------------------------------------------------
                    //Game rules - determines who is alive or who dies
                    //------------------------------------------------

                    //If alive
                    if (mainSpace[i][j].state)
                    {

                        //Lonely
                        if (occupied <= LONLINESS_LIST[mainSpace[i][j].tribe.tribeIndex]) 
                            tempSpace[i][j].state = false;

                        //Overcrowded in territory
                        else if (occupied >= FAMILIAR_STRESS_LIST[mainSpace[i][j].tribe.tribeIndex] && onlyOneTribe)
                            tempSpace[i][j].state = false; 
                        
                        //Overcrowded NOT in territory
                        else if (occupied >= UNFAMILIAR_STRESS_LIST[mainSpace[i][j].tribe.tribeIndex])
                            tempSpace[i][j].state = false;

                        //Content
                        else tempSpace[i][j].state = true;

                    }

                    //If dead
                    else

                        //Reproduction
                        if (occupied == REPRODUCTIVE_CONDITION && onlyOneTribe)
                        {
                            tempSpace[i][j].state = true;
                            tempSpace[i][j].tribe.tribeIndex = majorityTribe;
                        }
                        

                    //------------------------------------------------

            }

        //Copy the temp space into the main space
        for (int k = 0; k < ROW; k++)
            for (int l = 0; l < COL; l++)
                mainSpace[k][l] = tempSpace[k][l];
            
        }

        
        panel.repaint();  
        
    }
 
    @Override
    public void keyPressed(KeyEvent e)  
    {            

        if(e.getKeyCode() == KeyEvent.VK_HOME)
            Reset();
        
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
           System.exit(0); 
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
           paused = !paused;
        
        if(e.getKeyCode() == KeyEvent.VK_1)
            drawTribeIndex = 0;
        
        if(e.getKeyCode() == KeyEvent.VK_2)
            drawTribeIndex = 1;
        
        if(e.getKeyCode() == KeyEvent.VK_3)
            drawTribeIndex = 2;
        
        if(e.getKeyCode() == KeyEvent.VK_4)
            drawTribeIndex = 3;
        
        if(e.getKeyCode() == KeyEvent.VK_5)
            drawTribeIndex = 4;
        
        if(e.getKeyCode() == KeyEvent.VK_6)
            drawTribeIndex = 5;
        
        if(e.getKeyCode() == KeyEvent.VK_7)
            drawTribeIndex = 6;
        
        if(e.getKeyCode() == KeyEvent.VK_8)
            drawTribeIndex = 7;
        
        if(e.getKeyCode() == KeyEvent.VK_9)
            drawTribeIndex = 8;
        
    }

    @Override
    public void keyTyped(KeyEvent e) {} 
    
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) 
    {

            int row = (int)((e.getY() - MARGIN)/RES);
            int column = (int)((e.getX() - MARGIN)/RES);

            if (row < DRAW_RADIUS || row >= ROW - DRAW_RADIUS) return;
            if (column < DRAW_RADIUS || column >= COL - DRAW_RADIUS) return;
            if (drawTribeIndex >= TRIBE_COUNT) return;
            
            for (int i = 0; i < DRAW_RADIUS; i += 2)
            {

                draw(row + i, column);
                draw(row, column + i);
                draw(row - i, column);
                draw(row, column - i);

                draw(row + i, column - i);
                draw(row + i, column + i);
                draw(row - i, column + i);
                draw(row - i, column - i);
            
            }
 
    }
    
    public void draw(int row, int column)
    {
        mainSpace[row][column].state = true;
        mainSpace[row][column].tribe.tribeIndex = drawTribeIndex;
    }

    
    
 
    private class DrawingPanel extends JPanel implements Constants
    { 
        
        @Override
        public void paintComponent(Graphics g)         
        {
            
            super.paintComponent(g);
            panel.setBackground(Color.BLACK);

            if(delaycount < 20) return;

            for(int r = 0; r < ROW; r++)
                for(int c = 0; c < COL; c++)
                {
                    
                    if(mainSpace[r][c].state)
                    {
                        g.setColor(mainSpace[r][c].tribe.getColor());
                        g.fillRect(MARGIN + c*RES, MARGIN + r*RES, RES, RES);
                    }

                }

            g.setColor(GRID_TINT);
            for(int r = 0; r < ROW; r++)
                for(int c = 0; c < COL; c++)
                    if (GRID) g.drawRect(MARGIN + c*RES, MARGIN + r*RES, RES, RES);
            
        }
        
    }
}
