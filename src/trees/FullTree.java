
package trees;
import node.Node;
import node.Function;
import node.Terminal;
/**
 * @author Joshua Dzitiro
 */
public class FullTree extends Tree{
    
    
    public FullTree(int depth){
        super(depth);
    }
    public FullTree(int depth, Node root){
        super(depth, root);
    }
    
    public void create(Function parent, int currentDepth){
        
        if(currentDepth < depth){
        
            for(int i = 0; i < parent.arity; i ++){
                parent.children[i] = new Function();
                Function temp = (Function)parent.children[i];
                int rand = 0;
                
                temp.name = rand;
                temp.arity = Node.aritySet[rand];
                //System.out.println(currentDepth);
                create(temp, currentDepth+1);
                
            }
        }
        else
            for(int i = 0; i < parent.arity; i ++){
                parent.children[i] = new Terminal();
                Terminal temp = (Terminal)parent.children[i];
                //System.out.println(Node.terminalSet);
                temp.value = main.Random.getNext(Node.terminalSet);
                
            }
    
    }
}
