/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abigailcohen
 */
public class Dealer extends Player{
    
    public Dealer(){
        super(); 
    }
    
    public Dealer(int s){
        super(s); 
    }
    
    //determines whether play should hit or stand based on handTotal 
    public boolean hitOrStand(int h){
        boolean stand=false; 
        if(getHandTotal(h, "bustCheck")>=17){ 
            stand=true; 
            return stand; 
        } else{
            return stand; 
        }
    }
    
    public void printHand(int n){
        if(n==1){
            System.out.print("Dealer's hand:    "); 
            for(int x=0; x<hand.size(); x++){
                System.out.print(hand.get(x).getName()+"      ");
            }
            System.out.println(); 
            System.out.println(); 
        } else{
            System.out.print("Dealer's other hand:    "); 
            for(int x=0; x<hand2.size(); x++){
                System.out.print(hand2.get(x).getName()+"      ");
            }
            System.out.println(); 
            System.out.println(); 
        }
    }
}
