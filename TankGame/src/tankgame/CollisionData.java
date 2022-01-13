
package tankgame;

public class CollisionData
    {
        
        public CollisionData(double radius, double d1, double d2)
        {
            this.radius = radius;
            this.d1 = d1;
            this.d2 = d2;
        }
        
        public CollisionData(CollisionData collisionData)
        {
            this.radius = collisionData.radius;
            this.d1 = collisionData.d1;
            this.d2 = collisionData.d2;
        }
        
        public double radius;
        public double d1;
        public double d2;
        
    }
