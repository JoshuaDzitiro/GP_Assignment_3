/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import generation.Runner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.Node;
import node.Towers;
import population.Population;
import trees.FullTree;
import trees.GrowTree;
import trees.Tree;

/**
 *
 * @author Joshua Dzitiro 214548557
 */
public class Main {
    
    private static long seed;
    private static int populationSize;
    private static int maxDepth;
    private static int maxSubTree;
    private static int tournamentSize;
    private static int crossover;
    private static int mutation;
    private static int reproduction;
    private static int numberOfGenerations;
    private static int numberOfDisks;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        load();
        
        Node.terminalSet = 3;
        Towers.numberOfDisk = numberOfDisks;
        Random rand = new Random(154585);
        Population population = new Population(populationSize, maxDepth, true);
        
        mutation = (int)(mutation*(populationSize/100.0));
        crossover = (int)(crossover*(populationSize/100.0));
        reproduction = populationSize-(mutation + crossover);
        Tree tree = Runner.Run(population, tournamentSize, maxDepth, maxSubTree,
                numberOfGenerations, reproduction, mutation, crossover);
        System.out.println(tree.hitsRatio());
        tree.printPrefix();
        tree.printTower();
        
    }
    
    private static void load(){
        
        Scanner s = null;
        
        try {
            System.out.print("Enter the loader's path: ");
            Scanner key = new Scanner(System.in);
            String path = key.next();
            s = new Scanner(new BufferedReader(new FileReader(path)));

            while (s.hasNext()) {
                
                String input = s.next();
                if(input.equalsIgnoreCase("seed")){
                    //System.out.println(s.nextLong());
                    seed = s.nextLong();
                }
                else if(input.equalsIgnoreCase("populationSize")){
                    populationSize = s.nextInt();
                }
                else if(input.equalsIgnoreCase("maxDepth")){
                    maxDepth = s.nextInt();
                }
                else if(input.equalsIgnoreCase("maxSubTree")){
                    maxSubTree = s.nextInt();
                }
                else if(input.equalsIgnoreCase("tournamentSize")){
                    tournamentSize = s.nextInt();
                }
                else if(input.equalsIgnoreCase("operators")){
                    reproduction = s.nextInt();
                    mutation = s.nextInt();
                    crossover = s.nextInt();
                }
                
                else if(input.equalsIgnoreCase("numberOfGenerations")){
                    numberOfGenerations = s.nextInt();
                }
                
                else if(input.equalsIgnoreCase("numberofdisks")){
                    numberOfDisks = s.nextInt();
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Input file or format error exiting the application");
            System.exit(-1);
        } finally {
            if (s != null) {
                s.close();
            }
        }
        System.out.println(seed);
        System.out.println(populationSize);
        System.out.println(maxDepth);
        System.out.println(maxSubTree);
        System.out.println(tournamentSize);
        System.out.println(reproduction + " " + mutation + " " + crossover );
        
        if(seed == 0 || populationSize == 0 || maxDepth == 0 || maxSubTree == 0
                || tournamentSize == 0 || (reproduction+mutation+crossover) 
                != 100){
            System.out.println("Incomplete input data error exiting the application");
            System.exit(-2);
        }
    }
}
