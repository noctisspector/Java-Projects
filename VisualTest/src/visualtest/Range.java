
package visualtest;

public class Range 
{
    
    public int min;
    public int max;
    
    public Range()
    {
        min = 0;
        max = 0;
    }
    
    public Range(int min_, int max_)
    {
        min = min_;
        max = max_;
    }
    
    public int getRandom()
    {
        return (int)(Math.random() * (max - min + 1) + min);
    }
    
}
