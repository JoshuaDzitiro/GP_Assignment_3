/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.util.ArrayDeque;

/**
 *
 * @author 214548557
 */
public class Towers {
    
    public static int numberOfDisk = 3;
    
    public ArrayDeque<Integer>[] tower = new ArrayDeque[3];
    
    public Towers(){
        for(int i = 0; i < 3; i++)
            tower[i] = new ArrayDeque<Integer>();
        
        for(int i = numberOfDisk; i > 0;i--){
            tower[0].push(i);
        }
    }
    public void move(int from, int to){
        //if tower[from] is not empty pop the top disk
        if(!tower[from].isEmpty()){
            
            int disk1 = tower[from].pop();
            
            //if tower[to] is empty push the disk
            if(tower[to].isEmpty()){
                tower[to].push(disk1);
            }
            //if tower[to] is not empty check if the top disk is larger than the
            //the disk to be moved to the tower
            else{
                int disk2 = tower[to].pop();
                if(disk2 > disk1){
                    tower[to].push(disk2);
                    tower[to].push(disk1);
                }
                else{
                    tower[to].push(disk2);
                    tower[from].push(disk1);
                }
            }
            
        }
        //print();
    }
    public void print(){
        for(int i = 0; i < 3; i++){
            System.out.print("Size "+ tower[i].size()+" ");
            for(int temp:tower[i]){
                System.out.print(temp);
            }
            System.out.println();
        }
    }
    
}
