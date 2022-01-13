
package pondtask;

//**************************************************************************
//   Control Class			Author:  MurraySV
//   
//   Contains all of the methods that loop through the critters in the pond
//   and move, act, and age them.  BE CAREFUL WITH CHANGES TO THIS CODE!
//**************************************************************************


import java.util.ArrayList;

public class Control implements Physics {

    /**
     * The ArrayList that tracks all critters ever (alive and dead).
     */
    public static ArrayList<Animal> critters;
    
    /**
     * The representation of the pond as a 2D Matrix.
     * -1 is open water, -50 is a rock, -100 is food.
     * Any positive number is the index of a LIVE critter in the ArrayList
     */
    public static int[][] pond;
    
    /**
     * A simple integer that tracks what "day" it is in the pond.
     * Based on the Frogs in the pond, a "day" is more like a year in reality.
     */
    public static int day;

    /**
     * A simple integer that tracks what "hour" it is in the pond (24 hour day).
     */
    public static int hour;
    
    /**
     * Calls the Move Method of every critter in the Pond.
     * It is called every hour of the day... 
     *   but critters may not move each time (it depends on the critter's Move code).
     */
    public static void Movement()
    {
        
        for(int c = 0; c < critters.size(); c++)
        {
            if(critters.get(c).alive) critters.get(c).Move();
        }
    }
    
    /**
     * Calls the Act Method of every critter in the Pond.
     * It is always called after each time MOVE is called (24 times a day).
     * Again, not all critters will act at all times (it depends on their Act code).
     */
    public static void Action()
    {
        for(int c = 0; c < critters.size(); c++)
        {
            if(critters.get(c).alive) critters.get(c).Act();
        }
    }

    /**
     * Calls the Age Method of every critter in the Pond.
     * This happens at the last hour of each day for all critters.
     * It is only called once and all the critters will age.
     */
    public static void AgeCritters()
    {
        for(int c = 0; c < critters.size(); c++)
        {
            if(critters.get(c).alive) critters.get(c).Age();
        }
    }
    
    /**
     * The actual last call each "day".
     * This tries to spawn new food in empty spaces.
     */
    public static void AgeThePond()
    {
        boolean valid;
        int food = (int)(Math.random()*100);
        while(food < CHANCENEWFOOD)
        {
            valid = false;
            if(numOpenSpaces() == 0) { valid = true; food = 100; }
            //The above checks to make sure there is open space to add food.
            
            while(!valid)
            {
                int r = (int)(Math.random()*Control.pond.length);
                int c = (int)(Math.random()*Control.pond[0].length);
                if(pond[r][c] == -1) //open water
                {
                    pond[r][c] = -100; //makes it food!
                    valid = true;
                }
            }
            food += 10;
        }
        hour = 0;
        day++;
        System.out.println("\n\tDAY #"+Control.day);
    }
    
    /**
     * Checks if there are live critters in the pond
     * @return True is there is at least one live animal. False otherwise.
     */
    public static boolean PondIsAlive()
    {
        boolean ret = false;
        for(int c = 0; c < critters.size(); c++)
        {
            if(critters.get(c).alive) ret = true;
            //if it finds one critter alive, the pond is alive
        }
        return ret;
        //if NO critters are alive, the pond is dead and the simulation is over
    }
    
    /**
     * Counts the number of open water spaces in the pond
     * @return the integer value of the number of empty "squares".
     */
    public static int numOpenSpaces()
    {
        int count = 0;
        for(int r = 0; r<pond.length; r++)
            for(int c = 0; c <pond[0].length; c++) 
                if(pond[r][c]==-1) count++;
        return count;
    }

    /**
     * Checks if it's daytime
     * @return true if it's daytime, false if it's night.
     */
    public static boolean isDaytime()
    {
        boolean ret = false;
        if(hour > 7 && hour < 20) 
            ret = true;
        return ret;
    }

}
