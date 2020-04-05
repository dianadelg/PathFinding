/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envandalgo;

/**
 *
 * @author Malabooyah
 */
public class coordinate {
    
     int x,y;
    
    public coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString(){
        return ("(" + x + "," + y + ")");
    }
    
}
