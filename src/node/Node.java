
package node;

/**
 * @author Joshua Dzitiro
 */
public abstract class Node {
    
    public boolean isFunction = false;
    public int id;
    public int parentID;
    public static int functionSetSize = 0; 
    public static int terminalSet = 0;
    public static String[] functionSet;
    public static int[] aritySet;
    
}
