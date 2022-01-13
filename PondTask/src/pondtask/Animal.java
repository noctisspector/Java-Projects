
package pondtask;

//**************************************************************************
//   Abstract Animal Class		Author:  MurraySV
//   
//   The Parent Creature Class that defines how all critters will 
//   behave/interact with the pond and each other.
//**************************************************************************

import javax.swing.ImageIcon;

public abstract class Animal implements Physics {
    public String name;
    public int row;   //stores the vertical address of the critter in the pond
    public int col;   //stores the horizontal address of the critter in the pond
    //[row][col]:(0,0) is topleft; as row increases, position moves right, as col increases position moves down

    public int zpos;   //Basic info: -1 = underwater, 0 = on the water, 1 = above the water
    //You can use other int values to assign special movement behavior, just document it!
    
    public int age;    //0 = newly born
    public int hunger; //0 = no hunger, increases each day.
    public int gender; //Basic gender: 0 = no gender, 1 = Male, 2 = Female, 
    //You can use other int values to assign special behavior, just document those values.

    public ImageIcon pic;
    public boolean alive;

    public Animal(int r, int c)
    {
        name = "Abstract Animal";
        row = r;
        col = c;

        zpos = 0;
        age = 0;
        hunger = 0;
        gender = 0;
        
        pic = null;
        alive = true;
        
        //----- Statistics ------
        Visual.totalCritters++;
        //-----------------------
        
    }


    // EVERY ANIMAL CHILD YOU DESIGN NEEDS TO IMPLEMENT CODE FOR THREE METHODS BELOW!
    // Also remember that every time an Animal's position is updated, both its (x,y)position
    //   has to be updated in the ArrayList AND the values in the 2D pond matrix need to be changed
    abstract public void Move();
    abstract public void Act();
    abstract public void Age();
    
    
}
