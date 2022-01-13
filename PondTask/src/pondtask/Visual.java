
package pondtask;

//**************************************************************************
//   Visual Class 			Author:  MurraySV
//   
//   Contains all of the code the allows the visual window to function.
//   Be careful with changes to this file -especially in paintComponent()!
//**************************************************************************

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Visual implements ActionListener, KeyListener, Physics 
{
    private JFrame frame;
    public DrawingPanel panel;
    private Timer visualtimer;
    public int counter;
    public boolean stillrunning;
        
    //---- Statistics ----
    public static int maxCritters = 0;
    public static int maxCrittersDay = 0;
    public static int maxCrittersHour = 0;
    
    public static int totalCritters = 0;
    
    public static int minCritters = 99999999;
    public static boolean recovering = false;
    
    public static int oldestAge = 0;
    //--------------------
    
    public Visual()
    {
        frame = new JFrame("YOUR LITTLE HAPPY POND");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        frame.getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        counter = 0;
        stillrunning = true;

        visualtimer = new Timer(20, this);
        visualtimer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        counter++;
        if(counter>PAUSE && stillrunning) //makes the operations happen once every PAUSE
        {
            Control.hour += TIMECHANGE; 
            counter = 0;
            
            //Every hour these are activated, the frog class shows various ways to slow that down.	
            Control.Movement();
	    Control.Action();
	    
            if(Control.hour >= 24)
            {//Advance to the next day, by aging everything and checking to see if the pond is alive.
                Control.AgeCritters();
                Control.AgeThePond(); 
                stillrunning = Control.PondIsAlive();
                
                //------ Statistics -------
                int currentCritters = 0;
                for (int r = 0; r < Control.pond.length; r++)  
                        for (int c = 0; c < Control.pond[0].length; c++)
                            if (Control.pond[r][c] >= 0 ) currentCritters++;

                if (currentCritters < maxCritters) recovering = true;
                if (recovering && currentCritters < minCritters) minCritters = currentCritters;
                //-------------------------
                
            }
            if(!stillrunning) 
                System.out.println("It is a sad, sad day... all of the critters are gone :(");
	}

       
        ///***PART ONE ADDITIOANL CODE***///
	///THIS IS WHERE YOU SHOULD CHANGE THE CODE!!!
	///Output statistics on the pond:
	///   How many days did your pond last?
        ///   What was the maximum number of critters at any point (and when)?
	///   How many critters (total) lived in your pond throughout history?
        ///   What was the lowest population that your pond recovered from?
	///   What was the age (in days) of the oldest critter?
	///Save the average answers somewhere, you will use these in your write-up.
	///This may/will require the addition and management of several variables.
        
        ///Once your stats work, try tweaking the physics a bit and try to get the
        ///   results indicated in PART ONE of the task.
        
        /*
        
        RECORDED STATISTICS OF BASIC POND: 
        ---------- Statistics Report -----------
        Days pond lasted: 	20

        Max critters: 		457
        Max critters day: 	19
        Max critters hour: 	21

        Total critters: 	628

        Lowest population: 	37
        Oldest age of a critter: 	20
        ---------------------------------------- 
        
        */
        
        
        //------ Statistics -------
        int currentCritters = 0;
        for (int r = 0; r < Control.pond.length; r++)
                for (int c = 0; c < Control.pond[0].length; c++)
                    if (Control.pond[r][c] >= 0 ) currentCritters++;
        
        if (currentCritters > maxCritters)
        {
            maxCritters = currentCritters;
            maxCrittersDay = Control.day;
            maxCrittersHour = Control.hour;
        }
        //-------------------------
        
        
        panel.repaint();
        
    }
    
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            
            //------------------ Statistics -------------------------
            System.out.println();
            System.out.println("---------- Statistics Report -----------");
            System.out.println("Days pond lasted: \t" + Control.day);
            System.out.println();

            System.out.println("Max critters: \t\t" + maxCritters);
            System.out.println("Max critters day: \t" + maxCrittersDay);
            System.out.println("Max critters hour: \t" + maxCrittersHour);
            System.out.println();

            System.out.println("Total critters: \t" + totalCritters); //Obtained in the animal class constructor
            System.out.println();

            if (recovering) System.out.println("Lowest population: \t" + minCritters);
            else System.out.println("There was no population decrease. ");

            System.out.println("Oldest age of a critter: \t" + (oldestAge + 1)); //Obtained in the frog/other class Age() method
            System.out.println("----------------------------------------");
            System.out.println();
            //-------------------------------------------------------
            
            System.out.println("The Pond has closed for the summer...");
            System.exit(0);
        }
    }   

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }
    

    private class DrawingPanel extends JPanel implements Physics{

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            panel.setBackground(Color.BLACK);
            final int TABX = (SCREENWIDTH - PONDWIDTH)/2;
            final int TABY = (SCREENHEIGHT - PONDHEIGHT)/2;
            
            //Painting Text at the top of the screen
            g.setFont(new Font("Times New Roman", Font.BOLD, 16));
            g.setColor(Color.YELLOW);
            if(stillrunning) g.drawString("Day "+Control.day, TABX, TABY/2);
            else g.drawString("Everyone Is Dead.", TABX, TABY/2);
                        
            //Painting the Sun and Moon for Time Purposes
            if(stillrunning)
            {
                if(Control.isDaytime()) //DayTime (draw a sun)
                {
                    g.setColor(new Color(255, 255, 0)); //yellow
                    g.fillOval(SCREENWIDTH-TABX-30, (TABY/5), (int)(TABY*.6), (int)(TABY*.6));
                }
                else //NightTime (draw a moon)
                {
                    g.setColor(new Color(170, 150, 180)); //gray
                    g.fillOval(SCREENWIDTH-TABX-30, (TABY/4), (int)(TABY*.45), (int)(TABY*.45));
                    g.setColor(Color.BLACK);
                    g.fillOval(SCREENWIDTH-TABX-20, (TABY/4), (int)(TABY*.5), (int)(TABY*.4));
                }
            }
            
            //Painting the Pond Water and its Shore Boarder
            g.setColor(BOARDER);//Boarder color
            g.fillRect(TABX-10, TABY-10, PONDWIDTH+20, PONDHEIGHT+20);
            if(Control.isDaytime())
                g.setColor(WATER_DAY);//Pond color in the day
            else
                g.setColor(WATER_NIGHT);//Pond color at night
            g.fillRect(TABX, TABY, PONDWIDTH, PONDHEIGHT);

            //Painting the Rocks and Food
            for(int r = 0; r<Control.pond.length; r++)
            {
                for(int c = 0; c < Control.pond[0].length; c++)
                {
                    if(Control.pond[r][c] == -50) //it's a rock
                    {
                        g.setColor(Color.DARK_GRAY);
                        g.fillOval(TABX+(c*PIX), TABY+(r*PIX), PIX, PIX);
                    }
                    else if(Control.pond[r][c] == -100) //it's food
                    {
                        g.setColor(new Color(0, 110, 0));
                        g.fillOval(TABX+(c*PIX)+(PIX/3), TABY+(r*PIX)+(PIX/3), PIX-2*(PIX/3), PIX-2*(PIX/3));
                    }                    
                    //else it's either open water or a critter
                }
            }
  
            //Painting the Critters
            for(int n = 0; n < Control.critters.size(); n++)
            {
                Animal temp = Control.critters.get(n);
                if(temp.alive) //if the critter is alive
                    g.drawImage(temp.pic.getImage(), TABX+temp.col*PIX, TABY+temp.row*PIX, PIX, PIX, frame);
                    //Parameters:^^^^^image^^^^^^^   ^^^^x-position^^^  ^^^^y-position^^^  ^W^  ^H^  ^links it to your drawing
            }

        }
    }
}
