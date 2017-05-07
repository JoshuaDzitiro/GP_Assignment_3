
package node;

/**
 * @author Joshua Dzitiro
 */
public abstract class Node {
    
    public boolean isFunction = false;
    public int id;
    public int parentID;
    public static int functionSetSize = 1;
    public static int terminalSet = 3;
    public static String[] functionSet = {"move"};
    public static int[] aritySet = {2};
    
}
