/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generation;

import java.util.ArrayList;
import oparator.Oparators;
import population.Population;
import trees.Tree;

/**
 *
 * @author joshu
 */
public class Runner {

    public static Tree Run(Population population, int size, int maxDepth, 
            int maxSubTree, int generations, int crossover,
            int reproduction, int mutation) {

        Tree winner = null;
        for (int g = 1; g <= generations; g++) {
            System.out.println(g+"th Generation");
            ArrayList<Tree> generation = population.population;
            winner = generation.get(0);
            
            //System.gc();
            
            //check for winner
            for (int i = 0; i < generation.size(); i++) {
                if(winner.hitsRatio() > generation.get(i).hitsRatio()){
                    winner = generation.get(i);
                }
            }
            
            if(winner.hitsRatio() == 0){
                return winner;
            }
            else{
                System.out.println("best individual has a hits ratio of: " 
                        + winner.hitsRatio());
                winner.printPrefix();
            }
            
            ArrayList<Tree> newGen = new ArrayList<>();
            
            for(int i = 0; i < reproduction; i++){
                newGen.add(Oparators.reproduction(population, size));
            }
            
            for(int i = 0; i < mutation; i++){
                newGen.add(Oparators.mutation(population, size, maxDepth, 
                        maxSubTree));
            }
            
            for(int i = 0; i < crossover; i++){
                Tree[] trees = Oparators.crossover(population, size, maxDepth); 
                newGen.add(trees[0]);
                newGen.add(trees[1]);
            }
            //System.out.println(newGen.size());
            population.population = newGen;
            //create a new generation
            System.gc();
        }

        return winner;
    }
}
