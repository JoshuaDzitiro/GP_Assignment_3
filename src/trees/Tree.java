
package trees;

import java.util.ArrayDeque;
import java.util.Deque;
import node.Node;
import node.Function;
import node.Terminal;
import main.Random;
import oparator.OparationData;

/**
 * @author Joshua Dzitiro
 */
public abstract class Tree {
    
    protected int depth;
    public final Node root;
    private int rawFitness = 0;
    private int HitsRatio = 0;
    private boolean isEvaluated = false;
    public boolean isIDGen = false;
    public int numberOfNodes = 0;
    
    public Tree(int depth){
        this.depth = depth;
        int rand = main.Random.getNext(Node.functionSetSize);
        root = new Function();
        ((Function)root).name = rand;
        ((Function)root).arity = Node.aritySet[rand];
    }
    
    public Tree(int depth, Node root){
        this.depth = depth;
        this.root = root;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public void create(){}
    
    public void CalculateDepth(){
    
    }
    
    protected abstract void create(Function parent, int currentDepth);
    
    public void printPrefix(){
        this.printPrefixHelper(root);
        System.out.println();
    }
    
    private void printPrefixHelper(Node parent){
        
        if(parent.isFunction){
            
            Function temp = (Function)parent;
            
            System.out.print(Node.functionSet[temp.name] + " ");
            
            for(int i = 0; i < temp.arity; i++){
                printPrefixHelper(temp.children[i]);
            }
 
        }
        else{
            
            System.out.print(((Terminal)parent).value + " ");
        
        }
    
    }
    
    public void printPrefixIDs(){
        if(!isIDGen)
            createIDs();
        
        printPrefixIDsHelper(root);
        System.out.println();
    }
    
    private void printPrefixIDsHelper(Node node){
        
        System.out.print(node.id +" ");
        
        if(node.isFunction){
            
            Function temp = (Function)node;
            
            for(int i = 0; i < temp.arity; i++){
                printPrefixIDsHelper(temp.children[i]);
            }
 
        }
    
    
    }
    
    public int hitsRatio(){
        return 0;
    }
    
    public int rawFitness(){
    
        int miss = 0;
        
        return miss;
    }
    
    private void check(Node node, int set,byte[] trace){
        
        
       
    }
    
    public Tree clone(){
        Function clone = new Function();
        clone.arity = ((Function)root).arity;
        clone.name = ((Function)root).name;
        
        cloneHelper((Function)root, clone);
        
        return new FullTree(this.depth, clone);
    }
    
    private void cloneHelper(Function node, Function clone){
        
        for(int i = 0; i < node.arity;i++){
            if(node.children[i].isFunction){
                Function temp = new Function();
                temp.arity = ((Function)node.children[i]).arity;
                temp.name = ((Function)node.children[i]).name;
                clone.children[i] = temp;
                cloneHelper((Function)node.children[i], temp);
            }
            else{
                Terminal temp = new Terminal();
                temp.value = ((Terminal)node.children[i]).value;
                clone.children[i] = temp;
            }
        }
    
    }
    
    public OparationData getChildAndParent(int id){
        
        if(!isIDGen)
            createIDs();
        
        OparationData data = new OparationData();
        Deque<Node> queue = new ArrayDeque<>();
        
        queue.addLast(root);
        while(!queue.isEmpty()){
        
            Node node = queue.pollFirst();
            if(node.isFunction){
                Function temp = (Function)node;
                //System.out.print("Parent " + temp.id);
                for(int i = 0; i < temp.arity; i++){
                    //System.out.print(" Child " + temp.children[i].id);
                    if(temp.children[i].id == id){
                        data.childID = id;
                        data.childPosition = i;
                        data.parent = temp;
                        //System.out.println("");
                        return data;
                    }
                    queue.addLast(temp.children[i]);
                }
                //System.out.println();
            }
        }
        return data;
    }
    
    public void createIDs(){
        
        if(isIDGen)
            return;
        isIDGen = true;
        Deque<Node> queue = new ArrayDeque<>();
        
        queue.addLast(root);
        root.parentID = -1;
        int ids = 0;
        while(!queue.isEmpty()){
        
            Node node = queue.pollFirst();
            node.id = ids;
            if(node.isFunction){
                Function temp = (Function)node;
                for(int i = 0; i < temp.arity; i++){
                    temp.children[i].parentID = ids;
                    queue.addLast(temp.children[i]);
                }
            }
            ids++;
        }
        numberOfNodes = ids;
    }
    
    public void trim(int maxDepth){
        this.isIDGen = false;
        trimHelper((Function)root,1,maxDepth);
    }
    
    public void trimHelper(Function node, int current, int maxDepth){
        if(current >= maxDepth -1){
            //System.out.println(node.id);
            for(int i = 0; i < node.arity;i++) {
                node.children[i] = new Terminal();
                ((Terminal)node.children[i]).value = Random.getNext(Node.terminalSet);
            }
        }
        else{
            //System.out.println(node.id+ " " + current);
            for(int i = 0; i < node.arity;i++) {
                if(node.children[i].isFunction){
                    trimHelper((Function)node.children[i], current+1, maxDepth);
                    
                }
            }
        }
    }
}