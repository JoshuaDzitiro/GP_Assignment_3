
package oparator;

import main.Random;
import node.Function;
import node.Node;
import node.Terminal;
import population.Population;
import population.Selection;
import trees.Tree;

/**
 *
 * @author Joshua Dzitiro
 */
public class Oparators {

    public static Tree reproduction(Population population, int size) {
        return Selection.tournament(population, size).clone();
    }

    public static Tree mutation(Population population, int size, int maxdepth,
            int maxSubTree) {
        Tree tree = Selection.tournament(population, size).clone();
        //tree.printPrefix();
        tree.createIDs();
        //tree.printPrefixIDs();
        int id = Random.getNext(tree.numberOfNodes);
        
        while(id < 1)
            id = Random.getNext(tree.numberOfNodes);
        
        OparationData data = tree.getChildAndParent(id);
        //System.out.println(id);
        //System.out.println(data);
        Node subRoot;
        int rand = main.Random.getNext(Node.functionSetSize + 1);
        
        if (rand == Node.functionSetSize) {
            subRoot = new Terminal();
            ((Terminal)subRoot).value = main.Random.getNext(Node.terminalSet);
        } else {
            subRoot = new Function();
            ((Function)subRoot).name = rand;
            ((Function)subRoot).arity = Node.aritySet[rand];
            makeSubTree(((Function)subRoot), 1, maxSubTree);
        }
        
        data.parent.children[data.childPosition] = subRoot;
        tree.trim(maxdepth);
        return tree;
    }

    public static void makeSubTree(Function parent, int currentDepth, int depth)
    {
        if (currentDepth < depth) {

            for (int i = 0; i < parent.arity; i++) {
                int rand = main.Random.getNext(Node.functionSetSize + 1);

                if (rand == Node.functionSetSize) {
                    
                    parent.children[i] = new Terminal();
                    Terminal tmp = (Terminal) (parent.children[i]);
                    tmp.value = main.Random.getNext(Node.terminalSet);
                    
                } else {
                    
                    parent.children[i] = new Function();
                    Function temp = (Function) parent.children[i];
                    temp.name = rand;
                    temp.arity = Node.aritySet[rand];
                    //System.out.println(currentDepth);
                    makeSubTree(temp, currentDepth + 1, depth);
                }
            }
        } else {
            for (int i = 0; i < parent.arity; i++) {
                
                parent.children[i] = new Terminal();
                Terminal temp = (Terminal) parent.children[i];
                temp.value = main.Random.getNext(Node.terminalSet);

            }
        }
    }
    
    public static Tree[] crossover(Population population, int size, int maxdepth){
        
        Tree[] trees = new Tree[2];
        
        trees[0] = Selection.tournament(population, size).clone();
        trees[1] = Selection.tournament(population, size).clone();
        
        //trees[0].printPrefix();
        //trees[1].printPrefix();
        
        trees[0].createIDs();
        trees[1].createIDs();
        
        int rand1 = Random.getNext(trees[0].numberOfNodes);
        int rand2 = Random.getNext(trees[1].numberOfNodes);
        
        while(rand1 < 1 || rand2 < 1){
            rand1 = Random.getNext(trees[0].numberOfNodes);
            rand2 = Random.getNext(trees[1].numberOfNodes);
        }
        
        OparationData data1 = trees[0].getChildAndParent(rand1);
        OparationData data2 = trees[1].getChildAndParent(rand2);
        
        Node temp = data1.parent.children[data1.childPosition];
        //System.out.println(data1);
        //System.out.println(data2);
        data1.parent.children[data1.childPosition] = data2.parent.children[
                data2.childPosition];
        
        data2.parent.children[data2.childPosition] = temp;
        
        trees[0].trim(maxdepth);
        trees[1].trim(maxdepth);
        return trees;
    }
}
