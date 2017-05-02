/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oparator;

import node.Function;

/**
 *
 * @author joshu
 */
public class OparationData {
    public int childID;
    public int childPosition;
    public Function parent;
    
    @Override
    public String toString(){
        return "ChildID = " + childID +" parentID "+parent.id+ 
                " Child Position "+ childPosition;
    }
}
