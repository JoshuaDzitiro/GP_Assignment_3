/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package population;

import java.util.ArrayList;
import main.Random;
import trees.FullTree;
import trees.GrowTree;
import trees.Tree;

/**
 *
 * @author joshu
 */
public class Population {
    
    protected int maxDepth;
    protected int size;
    public ArrayList<Tree> population;
    
    public Population(int size,int maxDepth, boolean isCreate){
        
        this.maxDepth = maxDepth;
        this.size = size;
        
        if(isCreate)
            create();
        
    }
    
    public void create(){
        
        population = new ArrayList<Tree>();
        
        int full = this.size/2;
        int grow = this.size - full;
        
        //System.out.println("Full " + full + " Grow " + grow);
        
        int[] fulldepths = new int[maxDepth - 1];
        int[] growdepths = new int[maxDepth - 1];
        
        for(int i  = 0; i < maxDepth - 1; i++){
            fulldepths[i] = full / (maxDepth - 1);
            growdepths[i] = grow / (maxDepth - 1);
        }
        
        int fullRem = full %(maxDepth-1);
        int growRem = grow %(maxDepth-1);
        
        for(int i = 0; i < fullRem; i++)
            fulldepths[Random.getNext(maxDepth-1)] ++;
        
        for(int i = 0; i < growRem; i++)
            growdepths[Random.getNext(maxDepth-1)] ++;
        
        for(int depth = fulldepths.length-1;depth >= 0; depth--){
            for(int count = growdepths[depth]; count > 0; count--){
                population.add(new GrowTree(depth));
            }
            for(int count = fulldepths[depth]; count > 0; count--){
                population.add(new FullTree(depth));
            }
        }
        
    }
    
    public void printPopulation(){
        int i = 1;
        for(Tree tree: population){
            System.out.print("Tree " + i +" ");
            tree.printPrefix();
            i++;
        }
    }
}
