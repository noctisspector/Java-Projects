
package tankgame;

public class Engine 
{

    public static Actor spawnActorFromClass(Actor actor)
    {
        
        Visual.actors.add(actor);
        
        return actor;
        
    }
    
    public static double random(double min, double max)
    {
        return Math.random() * (max - min) + min;
    }
    
    public static double distanceBetween(Actor actor1, Actor actor2)
    {
        
        Vector actor1Location = actor1.location;
        Vector actor2Location = actor2.location;
        
        return Math.hypot(actor1Location.getX() - actor2Location.getX(), actor1Location.getY() - actor2Location.getY());
        
    }
    
    public static double distanceBetween(Vector vector1, Vector vector2)
    {
        
        return Math.hypot(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
        
    }
    
            
}
