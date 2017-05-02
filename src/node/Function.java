
package node;

/**
 * @author Joshua Dzitiro
 */
public class Function extends Node{
  
    public Function(){
        super.isFunction = true;
    }
    public  int name;
    public int arity;
    public Node[] children = new Node[4];
    
}
