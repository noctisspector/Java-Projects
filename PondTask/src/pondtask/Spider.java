
package pondtask;

import javax.swing.ImageIcon;

public class Spider extends Animal implements Physics
{
    
    private static final int MOVEMENT_MOD = 2;
    
    private static final int MAX_AGE = 30;
    private static final int MAX_HUNGER = 15;
    
    private static final int ADULT_AGE = 4;
    private static final int HUNGER_THRESHOLD = 4;
    
    private static final double MATING_CHANCE = 0.3;
    private static final double EATING_CHANCE = 0.8;
    private static final double EATING_UNDESIRABLE_CHANCE = 0.2;
    
    private static final int MATING_RADIUS = 2;
    
    private int maturity;
    
    public Spider(int x, int y)
    {
        
        super(x, y);
        
        name = "Spider";
        pic = new ImageIcon("spider.png"); 
        maturity = 0;
        
    }

    @Override
    public void Move() 
    {
        
        if(Control.hour % MOVEMENT_MOD != 0) return;
        
        boolean valid = false;
        int mynumber = Control.pond[row][col];   
        Control.pond[row][col] = -1;             
                
        while(!valid)
        {   
            
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

        Control.pond[row][col] = mynumber;
        
    }

    @Override
    public void Act() 
    {
        
        if (!alive) return;
        
        //Reproduction
        boolean foundMate = false;
        
        for (int r = row - MATING_RADIUS; r <= row + MATING_RADIUS; r++)
            for (int c = col - MATING_RADIUS; c <= col + MATING_RADIUS; c++)
                if (r >= 0 && r < Control.pond.length)
                    if (c >= 0 && c < Control.pond[0].length)
                        if (!foundMate && isMature() && r != row && c != col)
                        {

                            int index = Control.pond[r][c];

                            //If the space is an animal
                            if (index >= 0)
                            {

                                Animal animal = Control.critters.get(index);

                                //If the animal is same species
                                if (animal.name.equals(name))
                                {

                                    //Stop looking for more animals
                                    foundMate = true;

                                    Spider spider = (Spider)animal;

                                    //Chance of not mating
                                    //Both Spiders have this, so there is a chance of...
                                    // * Twins
                                    // * One Baby
                                    // * No Baby
                                    if (Math.random() < MATING_CHANCE)
                                    {

                                        addBaby(row, col);

                                        //Wait a couple days before reproducing again
                                        resetMaturity();
                                        spider.resetMaturity();

                                    }

                                }

                            }
                    
                }
        
        //Eating
        boolean foundEatingTarget = false;
        
        for (int r = row - 1; r <= row + 1; r++)
            for (int c = col - 1; c <= col + 1; c++)
                if (r >= 0 && r < Control.pond.length)
                    if (c >= 0 && c < Control.pond[0].length)
                        if (hunger > HUNGER_THRESHOLD && !foundEatingTarget) //If the spider is hungry
                        {

                            int index = Control.pond[r][c];

                            //If the space is an animal
                            if (index >= 0)
                            {
                                
                                Animal animal = Control.critters.get(index);

                                //If the animal is a frog
                                if (animal.name.equals("Frog"))
                                {

                                    //Chance of eating
                                    if (Math.random() < EATING_CHANCE)
                                    {
                                        
                                        //Stop looking for more animals
                                        //foundEatingTarget = true;
                                        
                                        animal.alive = false;
                                        Control.pond[r][c] = -1; 
                                        
                                        hunger -= 2;
                                        if (hunger < 0) hunger = 0;
                                        
                                    }
                                    
                                }
                            
                            }
                            
                            //If the space is Frog food
                            else if (index == -100)
                            {
                                
                                //Chance of eating
                                if (Math.random() < EATING_UNDESIRABLE_CHANCE)
                                {

                                    Control.pond[r][c] = -1; 

                                    hunger -= 1;
                                    if (hunger < 0) hunger = 0;

                                }
                                
                            }

                        }

    }

    @Override
    public void Age() 
    {
        
        if (!alive) return;
        
        hunger += 2;
        age++;
        maturity++;
        
        //-------- Statistics ----------
        if (age > Visual.oldestAge) Visual.oldestAge = age;
        //------------------------------
        
        //Death
        double deathProbability = 0;
        if (age >= MAX_AGE) deathProbability += 0.45;
        if (hunger >= MAX_HUNGER) deathProbability += 0.45;
        
        if (Math.random() < deathProbability)
        {
            
            alive = false;
            Control.pond[row][col] = -1; 
            System.out.println("A " + name + " has died...");
            
        }
        
        
    }
    
    
    //Misc Methods
    
    private boolean checkSpace(int R, int C, int val)
    {
        
        if( R < 0 || C < 0 || R >= Control.pond.length || C >= Control.pond[0].length ) return false;
        
        return Control.pond[R][C] == val;
        
    }
    
    public void resetMaturity() 
    {
        maturity = 0;
    }
    
    private boolean isMature()
    {
        return maturity >= ADULT_AGE;
    }
    
    private void addBaby(int R, int C)  
    {
        
        boolean added = false;
        
        if(checkSpace(R-1, C, -1)) 
        {
            Control.critters.add(new Spider(R-1, C));
            Control.pond[R-1][C] = Control.critters.size()-1;
            added = true;
        }    
        
        else if(checkSpace(R, C+1, -1)) 
        {
            Control.critters.add(new Spider(R, C+1));
            Control.pond[R][C+1] = Control.critters.size()-1;
            added = true;
        }   
        
        else if(checkSpace(R+1, C, -1)) 
        {
            Control.critters.add(new Spider(R+1, C));
            Control.pond[R+1][C] = Control.critters.size()-1;
            added = true;
        }   
        
        else if(checkSpace(R, C-1, -1)) 
        {
            Control.critters.add(new Spider(R, C-1));
            Control.pond[R][C-1] = Control.critters.size()-1;
            added = true;
        }    

        if(added) System.out.println("A new baby " + name + " was born!");
        
    }
    
    
    
}
