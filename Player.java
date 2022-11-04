
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author abigailcohen
 * January 19, 2021 
 * The Player class contains the methods and variables of a computer generated player in blackjack 
 * (in this game, the dealer) 
 */
public class Player{
    public ArrayList<Cards> hand=new ArrayList<Cards>(); 
    public ArrayList<Cards> hand2=new ArrayList<Cards>(); 
    int score; 
    int numHands=1; 
    
    public Player(){ 
        score=0; 
    }
    
    public Player(int s){ 
        score=s; 
    }
    
    public int receiveCard(int h, Cards c, int n){
        if(h==2){
            hand2.add(c); 
            return n+1; 
        } else{
            hand.add(c); 
            return n+1; 
        }
    }
    
    public ArrayList<Cards> getHand(int n){
        if(n==1){
            return hand; 
        } else{
            return hand2; 
        }
    }
    
    public int getNumHands(){
        return numHands; 
    }
    
    public void setNumHands(int n){
        numHands=n; 
    }
    
    public void emptyHands(){
        hand.clear();
        hand2.clear();
    }
    
    public int getScore(){
        return score; 
    }
    
    public void printHand(int n){
        if(n==1){
            System.out.print("Your hand:    "); 
            for(int x=0; x<hand.size(); x++){
                System.out.print(hand.get(x).getName()+"      ");
            }
            System.out.println(); 
            System.out.println(); 
        } else{
            System.out.print("Your other hand:    "); 
            for(int x=0; x<hand2.size(); x++){
                System.out.print(hand2.get(x).getName()+"      ");
            }
            System.out.println(); 
            System.out.println(); 
        }
    }
    
    public boolean checkSplits(){
        if(hand.get(0)==hand.get(1)){
            return true; 
        } else{
            return false; 
        }
    }
    
    public void splitDeck(){
        //adds the card to hand #2 
        hand2.add(hand.get(0)); 
        //removes one of the cards from hand #1 
        hand.remove(0);
        numHands++; 
    }
    
    //checks if handTotal=21 
    public boolean check21(int h){
        if(getHandTotal(h, "check21")==21){
            return true; 
        } else{
            return false; 
        }
    }
    
    //checks for a bust 
    public boolean checkOver21(int h){
        return 21-getHandTotal(h, "bustCheck")<0;
    }
    
    //calculates handTotal, accounts for aces, and returns total closest but not over 21 
    public int getHandTotal(int h, String m){
        ArrayList<Cards> testHand=hand; 
        if(h==2){ 
            testHand=hand2; 
        }
        int handTotal=0; 
        ArrayList<Integer> aceValues = new ArrayList<>();
        //for loop calculates total of cards in hands, excluding any aces 
        //all aces are added to an arraylist 
        for(int x=0; x<testHand.size(); x++){
            if(!testHand.get(x).isItAce()){
                handTotal+=testHand.get(x).getNum();
            } else{
                aceValues.add(1); 
            }
        }
        
        //handTotalWAces (with aces) is calculated - all aces are considered 1s and added to hand total
        int handTotalWAces1=handTotal; 
        for(int x=0; x<aceValues.size(); x++){
            handTotalWAces1+=aceValues.get(x);
        }
        
        int handTotalWAces11=handTotal; 
        //may need a try/catch for this 
        try{
            aceValues.set(0, 11); //sets first ace = 11 
            //add all values of aces to hand total (excluding aces) with one ace as an 11 
            for(int r=0; r<aceValues.size(); r++){
                handTotalWAces11+=aceValues.get(r);
            }
        } catch(Exception e){
            //uh???
        }
        
        int dif1=21-handTotalWAces1; 
        int dif11=21-handTotalWAces11; 
        if(dif1==0){
            handTotal=handTotalWAces1; 
            return handTotal; 
        } else if(dif1==0){
            handTotal=handTotalWAces11; 
            return handTotal; 
        }
        
        if(m.equals("bustCheck")){
            if(dif1>=0&&dif11<0){
                handTotal=handTotalWAces1; 
            } else if(dif11>=0&&dif1<0){
                handTotal=handTotalWAces11; 
            }
            else{
                handTotal=handTotalWAces1; 
            }
        } else{
            if(dif1<dif11&&dif1>=0){
                handTotal=handTotalWAces1; 
            } else{
                handTotal=handTotalWAces11; 
            }
        }
        return handTotal; 
    }
    
    
    public void win1(){
        score++; 
    }
    
    //returns number of aces - used when asking user to determine the value of their aces 
    public int getNumOfAces(int n){
        ArrayList<Cards>testHand=new ArrayList<Cards>();
        if(n==1){
            testHand=hand; 
        } else{
            testHand=hand2; 
        }
        int aceCounter=0;
        //for loop calculates total of cards in hands, excluding any aces 
        //all aces are added to an arraylist 
        for(int x=0; x<testHand.size(); x++){
            if(testHand.get(x).isItAce()){
                aceCounter++;
            } 
        }
        return aceCounter; 
    }
}
