
import java.util.Random;

/**
 *
 * @author abigailcohen 
 * January 19, 2021 
 * The Deck class represents the deck of cards. 
 * The constructor populates an array with 104 cards (two decks). 
 * In populating the array, the appropriate number of cards of a suit and number are created 
 */
public class Deck {
    public Cards deck[]=new Cards[104]; 
    
    public Deck(){
        //every two loops creates two sets of one suit 
        for (int t=0; t<8; t++){ 
            String suit="spades"; 
            if(t>5){
                suit="diamonds"; 
            }
            else if(t>3){
                suit="hearts"; 
            }
            else if(t>1){
                suit="clubs"; 
            }
            //every loop creates a card of a number, all of the same suit 
            for(int x=0; x<13; x++){
                if (x==0){
                    deck[x+(13*t)]=new Cards("ace", x+1, true, suit); 
                }
                else if(x>=10){ //face cards 
                    String name="10"; 
                    if(x==10){
                        name="jack"; 
                    } else if(x==11){
                        name="queen"; 
                    } else{ //x==12 
                        name="king"; 
                    }
                    deck[x+(13*t)]=new Cards(name, 10, false, suit); 
                }
                else{
                    deck[x+(13*t)]=new Cards(x+1, false, suit); 
                }
            }
        }
    }
    
    //shuffle() randomizes the position of cards in the deck[] array 
    public void shuffle(){
        Cards newDeck[]=new Cards[104]; 
        int checkOldDeck[]=new int[104]; 
        int checkNewDeck[]=new int[104]; 

        //creates two arrays of numbers 0-103
        //will be used to mark which cards have already been shuffled 
        for (int t=0; t<104; t++){
            checkOldDeck[t]=t;   
            checkNewDeck[t]=t; 
            //makes an identical deck of the game deck 
            newDeck[t]=deck[t];
        }
         
        //randomizes position of each card 
        for (int t=0; t<104; t++){
            int min=0; 
            int max=103; 
            Random randomGenerator = new Random(); 
            
            //random position in array generated 
            //if statement checks that that position in the array has not been shuffled already 
            int randomOldCard = randomGenerator.nextInt(max - min + 1) + min;
            while (checkOldDeck[randomOldCard]==200){
                randomOldCard = randomGenerator.nextInt(max - min + 1) + min;       
            }
            
            int randomNewCard = randomGenerator.nextInt(max - min + 1) + min;
            while (checkNewDeck[randomNewCard]==200){
                randomNewCard = randomGenerator.nextInt(max - min + 1) + min;       
            }
            
            //a card of a random position in the deck is chosen to have 
            //a random position in the new shuffled deck 
            newDeck[randomNewCard]=deck[randomOldCard]; 
            //through assigning the checkDeck arrays as 200, 
            //notes that that position number has already been used 
            checkOldDeck[randomOldCard]=200; 
            checkNewDeck[randomNewCard]=200; 
        }
        
        //the newDeck[] is shuffled deck[]
        //for loop sets deck[] equal to newDeck[]
        for (int t=0; t<104; t++){
            deck[t]=newDeck[t];
        }
    }
    
    public Cards[] getDeck(){
        return deck; 
    }
}
