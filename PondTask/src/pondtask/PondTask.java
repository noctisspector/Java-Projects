
package pondtask;

//**************************************************************************
//   Pond Main				Author:  MurraySV
//   
//   Manages the major objects in the pond, fills the pond on Day 0, and
//   launches the visual application.
//**************************************************************************

import java.util.ArrayList;


//The class name ThePond needs to be a perfect match for your program name...
//If you called your program Task711, then replace ThePond with that!
public class PondTask implements Physics{

   
    public static void main(String[] args) {

        System.out.println("Beginning A New Pond...");
        Control.day = 1;
        Control.hour = 0;
        
        final int PONDr = PONDHEIGHT/PIX;
        final int PONDc = PONDWIDTH/PIX;
        
        
        Control.critters = new ArrayList<Animal>();
        Control.pond = new int[PONDr][PONDc];
        
        ////BUILDING THE POND...     
        //Adding Rocks and Food inside the Pond
        for(int r = 0; r < PONDr; r++)
            for(int c = 0; c < PONDc; c++)
            {
                Control.pond[r][c] = -1; ///open water.
                if((int)(Math.random()*100) < PERCENTROCKS) Control.pond[r][c] = -50;      ////Adding a ROCK
                else if((int)(Math.random()*100) < PERCENTFOOD) Control.pond[r][c] = -100; ////Adding some FOOD.
            }

        //Adding Animals into empty spaces:
        int num = 0;
        while(num < NOA && Control.numOpenSpaces()!=0)
        {
            int r = (int)(Math.random()*PONDr); 
            int c = (int)(Math.random()*PONDc); 
            if(Control.pond[r][c] == -1) //if it's open - add an ANIMAL
            {
                
                double rand = Math.random();
                
                if (rand < 0.75) Control.critters.add(new Frog(r,c));  
                else Control.critters.add(new Spider(r,c));
                
                Control.pond[r][c] = num;             //changes the pond position to indicate which animal is there.
             
                num++;
                
            }
        }
        ///***PARTS TWO-FOUR ADDITIONAL CODE***///
	///THIS IS WHERE YOU SHOULD CHANGE THE CODE!!!
	///In the "Adding Animals For-Loop", you will need to add code
        ///Right now, every newly added Animal is always a Frog.
        ///Change that code to make it a random selection from 
        ///  your available Animal sub-classes that you will be coding 
        ///  in parts 2 through 4.
	

        //Once the pond array and critter list is set-up, we are ready to being the visuals:
        new Visual();

    }
}
