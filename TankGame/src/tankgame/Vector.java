
package tankgame;

public class Vector 
{
    
    protected enum Quadrant { I, II, III, IV, pX, nX, pY, nY, unspecified }
    
    private double x; 
    private double y;
    
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector(Vector vector)
    {
        this.x = vector.getX();
        this.y = vector.getY();
    }
    
    public double vectorLength()
    {
        return Math.hypot(x, y);
    }
    
    public void normalize()
    {
        double length = vectorLength();
        x /= length;
        y /= length;
    }
    
    public void scale(double scalar)
    {
        x *= scalar;
        y *= scalar;
    }
    
    public void resize(double length)
    {
        normalize();
        scale(length);
    }
    
    public int getIntX() { return (int)x; }
    public int getIntY() { return (int)y; }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    
    public static Vector add(Vector vector1, Vector vector2)
    {
        return new Vector(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }
    
    public static Vector invert(Vector vector)
    {
        return new Vector(vector.getX() * -1, vector.getY() * -1);
    }
    
    public void setAngle(double angle)
    {
        
        double length = vectorLength();
        
        x = length * Math.cos(Math.toRadians(angle));
        y = length * Math.sin(Math.toRadians(angle));
        
    }
    
    public void addAngle(double angle)
    {
        
        double newAngle = angle + Math.toDegrees(getAngle());
        
        setAngle(newAngle);
        
    }
    
    public double getAngle()
    {
        
        double tangent = Math.atan(Math.abs(y / x));
        double angle = 0;
        Quadrant quadrant = getQuadrant();
        
        switch (quadrant)
        {
            case I: angle = tangent;
                break;
            case II: angle = Math.PI - tangent;
                break;
            case III: angle = Math.PI + tangent;
                break;
            case IV: angle = Math.PI * 2 - tangent;
                break;
            case pX: angle = 0;
                break;
            case pY: angle = Math.PI / 2;
                break;
            case nX: angle = Math.PI;
                break;
            case nY: angle = Math.PI * 3 / 4.0;
                break;
            case unspecified: angle = 0;
        }
        
        return angle;
        
    }
    
    private Quadrant getQuadrant()
    {
        
        if (x > 0)
        {
            
            if (y > 0) return Quadrant.I;
            else if (y < 0) return Quadrant.IV;
            else return Quadrant.pX;
            
        }
        
        else if (x < 0)
        {
            
            if (y > 0) return Quadrant.II;
            else if (y < 0) return Quadrant.III;
            else return Quadrant.nX;
            
        }
        
        else if (y > 0) return Quadrant.pY;
        else if (y < 0) return Quadrant.nY;
        else return Quadrant.unspecified;
        
    }
    
    @Override
    public String toString()
    {
        
        return "(" + x + ", " + y + ")";
        
    }
    
}
