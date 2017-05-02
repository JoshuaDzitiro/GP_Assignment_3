
package main;

/**
 *
 * @author Joshua Dzitiro
 */
public class Random {
    
    private static java.util.Random rand;
    
    public Random(long seed){
        if(rand == null)
            Random.rand = new java.util.Random(seed);
    }
    
    public static synchronized int getNext(int bound){
        return rand.nextInt(bound);
    }
}
