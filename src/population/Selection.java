/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package population;

import main.Random;
import trees.Tree;

/**
 *
 * @author joshu
 */
public class Selection {
    
    public static Tree tournament(Population population, int size){
        Tree[] trees = new Tree[size];
        
        for(int i = 0; i < size; i++)
            trees[i] = population.population.get(Random.getNext(population.population.size()));
        
        int top  = 0;
        for(int i = 0; i < size; i++){
            //System.out.println("Tree " + (1+i) + " has a fitness of " + trees[i].rawFitness());
            if(trees[top].rawFitness() > trees[i].rawFitness()){
                top = i;
            }
        }
        
        return trees[top];
    }
    
}
