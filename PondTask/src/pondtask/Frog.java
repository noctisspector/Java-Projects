
package pondtask;

//**************************************************************************
//   Frog Class				Author:  MurraySV
//   
//   Child of Animal.  Represents a simple creature in the pond.
//**************************************************************************

import javax.swing.ImageIcon;

public class Frog extends Animal implements Physics{
    
    private static final int MINAGE = 2;
    private static final int OLDAGE = 12;

    public Frog(int x, int y)
    {
        super(x,y);	//uses the parent to contruct the animal
        name = "Frog";  //changes the name to the correct value.
        pic = new ImageIcon("babyfrog.png");      //changes the picture to a baby-frogy-looking thing.
    }

    /**
     * Move Code:  A frog moves into a random adjacent open space or sits still
     */
    @Override
    public void Move() {
        if(Control.hour%6 != 0) return;
        //This means that the frogs only moves a couple of times a day...
        //If hour isn't 0, 6, 12, 18, 24 then you return back to the Visual Class.
        //PS. it will never be 0 because of the order in which things happen.
        
        boolean valid = false;
        int mynumber = Control.pond[row][col];   //remembers what UNIQUE-number the animal is in the ArrayList
        Control.pond[row][col] = -1;             //empties the frog's current position
                
        while(!valid)
        {   //Follows the keypad 2=down, 4=left, 6=right, 8=up
            int move = (int)(Math.random()*5) * 2;
            if(move == 0) //do nothing
            {
                valid = true;
            }
            else if(move == 2 && checkSpace(row+1, col, -1)) //down
            {
                row++;
                valid = true;
            }
            else if(move == 4 && checkSpace(row, col-1, -1)) //left
            {
                col--;
                valid = true;
            }
            else if(move == 6 && checkSpace(row, col+1, -1)) //right
            {
                col++;
                valid = true;
            }
            else if(move == 8 && checkSpace(row-1, col, -1)) //up
            {
                row--;
                valid = true;
            }
        }

        riseOrLower(); 
        Control.pond[row][col] = mynumber; //puts the frog back into the pond.
    }

    private void riseOrLower()
    {
        if(gender == 0) return; //baby frogs don't change zpos;
        if(Math.random() < .05) //randomly adult frogs will submerge or rise out of the water
        {
            if(zpos == 0) 
            {
                zpos = -1;
                pic = new ImageIcon("frogunderwater.png"); //pic is now a submerged-frogy-looking thing
            }
            else
            {
                zpos = 0;
                pic = new ImageIcon("adultfrog.png"); //pic is now an adult-frogy-looking thing
            }
        }
    }
    
    
    
    /**
     * Act Code:  A frog eats one adjacent food if available, decreasing it's hunger 
     *            A frog checks for adjacent frogs to make frog babies
     */
    @Override
    public void Act() { 
        
        if(Control.isDaytime()) return;
        //This means that frogs only act at night -- most species of frogs are nocturnal.
        //(If it's day, you skip this method by returning back to the Visual Class)
                

        //looks for food:
        boolean looking = true;
        int a=0, b=0, count=2;
        while(looking && count < 10)  //Starts looking around the frog for food.
        {
            if(count == 2)       { a=1;  b=0;  } //looking down
            else if(count == 4)  { a=0;  b=-1; } //looking left
            else if(count == 6)  { a=0;  b=1;  } //looking right
            else /*(count == 8)*/{ a=-1; b=0;  } //looking up
            if(checkSpace(row+a, col+b, -100))
            {
                Control.pond[row+a][col+b] = -1; //frog eats food and water becomes empty
                hunger--;
                looking = false;
                if(hunger < 0) hunger=0;
            }
            count += 2;
        }//If it eats food, it's done looking... if it looks all around itself and doesn't find any, it's done
        
        
        //females look for males:
        if(gender != 2) return; //If not a female, return - you're done acting.
        if(hunger > 8) return;  //If a female is too hungry, return - it won't make froglet-babies.
        
        count=2;
        looking = true;
        boolean found = false;
        while(looking && count < 10 && !found)
        {
            if(count == 2)       { a=1;  b=0;  } //looking down
            else if(count == 4)  { a=0;  b=-1; } //looking left
            else if(count == 6)  { a=0;  b=1;  } //looking right
            else /*(count == 8)*/{ a=-1; b=0;  } //looking up
            if(checkNewBaby(row+a, col+b))
            {//she found SOMETHING if the above is true.
                int ind = Control.pond[row+a][col+b];
                if( Control.critters.get(ind).gender == 1 && Control.critters.get(ind).name.equals(this.name)) 
                {//if the thing she found is a MALE and a "FROG"
                    found = true;
                }
            }
            count += 2;
        }
        if(found) addBaby(row, col);
    }

    

    /**
     * Age Code:  Increments Age.  Compare the chances that the frog lives.
    //          High hunger and Age > AVEAGE increases the chance of death.
     */
    @Override
    public void Age() 
    {

        hunger++;
        age++;
        
        //-------- Statistics ----------
        if (age > Visual.oldestAge) Visual.oldestAge = age;
        //------------------------------

        if(gender == 0 && age > MINAGE)
        { //once a baby is MINAGE, its gender is revealed.
            gender = (int)(Math.random()*2+1); 
            pic = new ImageIcon("adultfrog.png"); //pic is now an adult-frogy-looking thing
            System.out.println("A "+name+" has grown up!");
        }
        
        ///These numbers are just made-up to make a hungry frog more likely to die
        ///  and an older frog far more likely to die...
        int deathchance = age;
        if(hunger > 4) deathchance += (int)((hunger-4) *1.5);
        if(age > OLDAGE) deathchance += (age - OLDAGE) * 2;

        if((int)(Math.random()*100) < deathchance) 
        {
            alive = false;
            Control.pond[row][col] = -1; //dead body turns to open water...
            System.out.println("A "+name+" has died...");
        }
    }


    //Helper Methods:
    private boolean checkSpace(int R, int C, int val)
    {
        if( R<0 || C<0 || R >= Control.pond.length || C >= Control.pond[0].length ) return false;
        if(Control.pond[R][C] == val) return true;
        return false;
    }
    private boolean checkNewBaby(int R, int C)  
    { 
        if( R<0 || C<0 || R >= Control.pond.length || C >= Control.pond[0].length ) return false;
        if(Control.pond[R][C] < 0) return false;
        else return true;
    }
    private void addBaby(int R, int C)  
    {
        boolean added = false;
        if(checkSpace(R-1, C, -1)) //there's an open space above
        {
            Control.critters.add(new Frog(R-1, C));
            Control.pond[R-1][C] = Control.critters.size()-1;
            added = true;
        }    
        else if(checkSpace(R, C+1, -1)) //there's an open space right
        {
            Control.critters.add(new Frog(R, C+1));
            Control.pond[R][C+1] = Control.critters.size()-1;
            added = true;
        }            
        else if(checkSpace(R+1, C, -1)) //there's an open space below
        {
            Control.critters.add(new Frog(R+1, C));
            Control.pond[R+1][C] = Control.critters.size()-1;
            added = true;
        }            
        else if(checkSpace(R, C-1, -1)) //there's an open space left
        {
            Control.critters.add(new Frog(R, C-1));
            Control.pond[R][C-1] = Control.critters.size()-1;
            added = true;
        }    

        if(added) System.out.println("A new baby " + name + " was born!");
    }
}
