
package trees;

import node.Function;
import node.Node;
import node.Terminal;

/**
 * @author Joshua Dzitiro
 */
public class GrowTree extends Tree{

    public GrowTree(int depth) {
        super(depth);
    }
    
    @Override
    public void create(Function parent, int currentDepth) {
        if(currentDepth < depth){
        
            for(int i = 0; i < parent.arity; i ++){
                parent.children[i] = new Function();
                Function temp = (Function)parent.children[i];
                int rand = main.Random.getNext(Node.functionSetSize+1);
                
                if(rand == Node.functionSetSize){
                    parent.children[i] = new Terminal();
                    Terminal tmp = (Terminal)(parent.children[i]);
                    tmp.value = main.Random.getNext(Node.terminalSet);
                }
                else{
                    temp.name = rand;
                    temp.arity = Node.aritySet[rand];
                    //System.out.println(currentDepth);
                    create(temp, currentDepth+1);
                }
            }
        }
        else
            for(int i = 0; i < parent.arity; i ++){
                parent.children[i] = new Terminal();
                Terminal temp = (Terminal)parent.children[i];
                temp.value = main.Random.getNext(Node.terminalSet);
                
            }
    }
    
}
