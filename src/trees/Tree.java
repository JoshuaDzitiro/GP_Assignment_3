
package trees;

import java.util.ArrayDeque;
import java.util.Deque;
import node.Node;
import node.Function;
import node.Terminal;
import main.Random;
import node.Towers;
import oparator.OparationData;

/**
 * @author Joshua Dzitiro
 */
public abstract class Tree {
    
    protected int depth;
    public final Node root;
    private int rawFitness = 0;
    private int hitsRatio = 0;
    public boolean isIDGen = false;
    public int numberOfNodes = 0;
    public Towers towers;
    
    public Tree(int depth){
        towers = new Towers();
        this.depth = depth;
        int rand = 0;
        root = new Function();
        ((Function)root).name = rand;
        ((Function)root).arity = Node.aritySet[rand];
        create((Function)root, 1);
        solve((Function)root);
        
        int raw = towers.numberOfDisk - towers.tower[2].size();
        rawFitness = raw * 2 * towers.numberOfDisk;
        
        hitsRatio = raw;
        
        //towers.print();
        
    }
    
    public Tree(int depth, Node root){
        towers = new Towers();
        this.depth = depth;
        this.root = root;
        solve((Function)root);
        
        int raw = towers.numberOfDisk - towers.tower[2].size();
        rawFitness = raw * 2 * towers.numberOfDisk;
        
        hitsRatio = raw;
    }
    
    public int getDepth() {
        return depth;
    }
    
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
        return hitsRatio;
    }
    
    public int rawFitness(){
        return rawFitness;
    }
    
    protected void solve(Function function){
        
        //check if the left child is a terminal
        if(!function.children[0].isFunction){
            int from = ((Terminal)function.children[0]).value;
            //check if the right child is a terminal the get the value
            if(!function.children[1].isFunction){
                int to = ((Terminal)function.children[1]).value;
                move(from, to);
            }
            else{
                solve((Function)function.children[1]);
                int to = getLeftMost((Function)function.children[1]);
                move(from, to);
            }
            
        }
        //check if the right child is a terminal
        else if(!function.children[1].isFunction){
            int to = ((Terminal)function.children[1]).value;
            //check if the right child is a terminal the get the value
            if(!function.children[0].isFunction){
                int from = ((Terminal)function.children[0]).value;
                move(from, to);
            }
            else{
                solve((Function)function.children[0]);
                int from = getRightMost((Function)function.children[0]);
                move(from, to);
            }
        }
        else{
            solve((Function)(function.children[0]));
            solve((Function)(function.children[1]));
        }
       
    }
    
    public void printTower(){
        towers.print();
    }
    
    private void move(int from, int to){
        //System.out.println(from+" "+to);
        towers.move(from, to);
    }
    
    private int getLeftMost(Function function){
        if(!function.children[0].isFunction){
            return ((Terminal)function.children[0]).value;
        }
        else{
            return getLeftMost((Function)function.children[0]);
        }
    }
    private int getRightMost(Function function){
        if(!function.children[1].isFunction){
            return ((Terminal)function.children[1]).value;
        }
        else{
            return getRightMost((Function)function.children[1]);
        }
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