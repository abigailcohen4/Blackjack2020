
/**
 *
 * @author abigailcohen
 * January 19, 2021 
 * the Cards class defines objects that represent a playing card, including having 
 * a name, numeric value, suit, and a boolean determining whether the card is an ace
 * the methods are all get methods used in other classes to refer to a variable of a card. 
 */


public class Cards {
    public String name; 
    public int numericValue; 
    public boolean isAce; 
    public String suit;  
    
    public Cards(int n, boolean a, String t){
        name=String. valueOf(n); 
        numericValue=n; 
        isAce=a; 
        suit=t; 
    } 
    
    public Cards(String s, int n, boolean a, String t){
        name=s; 
        numericValue=n; 
        isAce=a; 
        suit=t; 
    } 
    
    public boolean isItAce(){
        return isAce; 
    }
    
    public int getNum(){
        return numericValue; 
    }
    
    public void setNum(int n){
        numericValue=n; 
    }
    
    public String getName(){
        String fullName=name+" of "+suit; 
        return fullName; 
    }
}
