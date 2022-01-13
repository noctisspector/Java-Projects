
package tankgame;

public class Collision 
{
    
    public enum CollisionType { BOX, CIRCLE };
    
    private final Vector location;
    private CollisionData collisionData;
    private CollisionType collisionType;
    
    public Collision(Vector location, CollisionType collisionType, CollisionData collisionData)
    {
        //Create a link to parent location
        this.location = location;
        this.collisionData = new CollisionData(collisionData);
        this.collisionType = collisionType;
    }   
    
    public Collision(Collision collision)
    {
        //transfer link to location
        location = collision.location;
        collisionData = new CollisionData(collision.collisionData);
        collisionType = collision.collisionType;
    }
    
    public boolean testOverlap(Collision collision1, Collision collision2)
    {
        
        //Circle on Circle
        if (collision1.isType(CollisionType.CIRCLE) && collision1.isType(CollisionType.CIRCLE))
            return Engine.distanceBetween(collision1.location, collision2.location) <= collisionData.radius;

        return false;
                    
    }
    
    private boolean isType(CollisionType type)
    {
        
        return type == collisionType;
        
    }
    
}
