
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author abigailcohen
 */


public class GameEngine {
    
    public static void main (String args[]) {
        GameEngine gameEngine=new GameEngine(); 
        Deck deck = new Deck(); 
        File savedScores = new File("savedScores.txt");
        Scanner input = new Scanner(System.in); 
        
        //the card is not actually being removed from the deck array 
        //nextNumDeck will be an int that remembers what number card should be taken 
        //anytime after player.receiveHand() occurs deck 
        int nextNumDeck=0; 

        System.out.println("               BLACKJACK"); 
        System.out.println("          Risk. Reward. Fortune."); 
        System.out.println(); 
        System.out.println(" The classic game of Blackjack originated in "); 
        System.out.println("  French casinos around 1700. The aim of the "); 
        System.out.println("game: to get as close to 21 without going over."); 
        System.out.println(); 
        
        Player user=new Player(); 
        Dealer dealer=new Dealer();  
        
        int userScore=0; 
        int dealerScore=0; 
        try{
            Scanner getSavedScores = new Scanner(savedScores);
            userScore = getSavedScores.nextInt();
            dealerScore = getSavedScores.nextInt();
            user=new Player(userScore); 
            dealer=new Dealer(dealerScore);  
        } catch(Exception e){
            System.out.println("You have no scores saved from previous games."); 
            System.out.println(); 
        }
        
        
        System.out.println("Dealer's score: "+dealer.getScore());
        System.out.println("Your score: "+user.getScore());
        System.out.println(); 
        
        boolean play=false; 
        //the while loop below asks the user if they are ready to play blackjack 
        while(!play){
            System.out.println("Are you ready to play? ");
            String response=input.nextLine().toLowerCase(); 
            if (response.equals("yes")){
                play=true; 
            } else if(response.equals("no")){ //if user says no, prints sassy comments 
                int min = 0;
                int max = 4;
                Random randomGenerator = new Random();
                int randomComment = randomGenerator.nextInt(max - min + 1) + min;
                switch(randomComment){
                    case 0: 
                        System.out.println("Then what are you doing here?");
                        break; 
                    case 1: 
                        System.out.println("Okay... guess I'll wait.");
                        break; 
                    case 2: 
                        System.out.println("You're missing out... ");
                        break; 
                    case 3: 
                        System.out.println("Fine. Let me know when you are. ");
                        break; 
                    case 4: 
                        System.out.println("Kinda lame :|");
                        break; 
                }
                
            } else{
                System.out.println("Please enter \"yes\" or \"no\". "); 
            }
        }
        while(play){
            boolean bust=false; 
            deck.shuffle();
            dealer.emptyHands();
            dealer.setNumHands(1); 
            user.emptyHands();
            user.setNumHands(1); 
            ArrayList<Integer> handTotals=new ArrayList<Integer>(); 
            System.out.println(); 

            //deals first card to dealer and user 
            nextNumDeck=dealer.receiveCard(1, deck.getDeck()[nextNumDeck], nextNumDeck); 
            //for grammar, checks if card recieved was an ace or 8 
            if(dealer.getHand(1).get(0).getNum()==8||dealer.getHand(1).get(0).getNum()==1){
                System.out.println("The dealer received an "+dealer.getHand(1).get(0).getName()+"."); 
            } else{
                System.out.println("The dealer received a "+dealer.getHand(1).get(0).getName()+"."); 
            }
            
            nextNumDeck=user.receiveCard(1, deck.getDeck()[nextNumDeck], nextNumDeck); 
            //for grammar 
            if(user.getHand(1).get(0).getNum()==8||user.getHand(1).get(0).getNum()==1){
                System.out.println("You received an "+user.getHand(1).get(0).getName()+"."); 
            } else{
                System.out.println("You received a "+user.getHand(1).get(0).getName()+"."); 
            }
            
            System.out.println(); 
            user.printHand(1); 
            dealer.printHand(1); 

            //deals second card 
            nextNumDeck=dealer.receiveCard(1, deck.getDeck()[nextNumDeck], nextNumDeck);
            System.out.println("The dealer received another card."); 

            nextNumDeck=user.receiveCard(1, deck.getDeck()[nextNumDeck], nextNumDeck); 
            //for grammar 
            if(user.getHand(1).get(1).getNum()==8||user.getHand(1).get(1).getNum()==1){
                System.out.println("You received an "+user.getHand(1).get(1).getName()+"."); 
            } else{
                System.out.println("You received a "+user.getHand(1).get(1).getName()+"."); 
            } 
            user.printHand(1); 
            
            //after dealing the first two cards, decks are checked for naturals 
            boolean naturals=false; 
            if(dealer.check21(1)){
                naturals=true; 
                System.out.println("Naturals! The dealer won."); 
                System.out.println("The dealer reveals his cards. ");
                dealer.printHand(1);
                dealer.win1(); 
            }
            else if(user.check21(1)){
                naturals=true; 
                System.out.println("Naturals! You won!"); 
                System.out.println("The dealer reveals his cards. ");
                dealer.printHand(1);
                user.win1(); 
            }
            
            //if naturals did not occur, the game continues 
            if(!naturals){
                if(user.checkSplits()){
                    boolean askSplits=true; 
                    while(askSplits){
                        System.out.println("Would you like to split decks? ");
                        String response=input.nextLine().toLowerCase(); 
                        if (response.equals("yes")){ 
                            user.splitDeck();
                            user.printHand(1); 
                            user.printHand(2); 
                            askSplits=false; 
                        } else if(response.equals("no")){
                            askSplits=false; 
                        } else{
                            System.out.println("Please enter \"yes\" or \"no\". "); 
                        }
                    }
                } 

                if (dealer.checkSplits()){
                    dealer.splitDeck(); 
                    System.out.println("The dealer split decks."); 
                }
                
                boolean askSurrender=true;
                boolean surrender=false; 
                while(askSurrender){
                    System.out.println("Would you like to surrender? ");
                    String response=input.nextLine().toLowerCase(); 
                    if (response.equals("yes")){ 
                        surrender=true; 
                        askSurrender=false; 
                    } else if(response.equals("no")){
                        askSurrender=false; 
                    } else{
                        System.out.println("Please enter \"yes\" or \"no\". "); 
                    }
                    System.out.println(); 
                }
            
                if(!surrender){
                    for(int x=0; x<user.getNumHands(); x++){
                        user.printHand(x+1); 
                    }
                    //User is offered to hit or stand, once or twice depending on hands of player 
                    for(int x=0; x<user.getNumHands(); x++){
                        boolean userStand=false; 
                        while(!userStand){
                            if(user.checkOver21(1)){ 
                                userStand=true; 
                                break; 
                            } 
                            if(user.getNumHands()==2){
                                if(x==0){
                                    System.out.println ("For your first hand: "); 
                                } else{
                                    System.out.println("For your second (\"other\") hand: "); 
                                }
                            }
                            System.out.println("Would you like to hit or stand? ");
                            String response=input.nextLine().toLowerCase(); 
                            if (response.equals("hit")){
                                nextNumDeck=user.receiveCard(x+1, deck.getDeck()[nextNumDeck], nextNumDeck); 
                                nextNumDeck+=1; 
                                System.out.println();  
                                user.printHand(x+1); 
                            } else if(response.equals("stand")){
                                userStand=true; 
                            } else{
                                System.out.println("Please enter \"hit\" or \"stand\". "); 
                            }
                            if(user.checkOver21(x+1)){ 
                                userStand=true; 
                                break; 
                            } 
                        }
                    }
                    if(user.checkOver21(1)){
                        bust=true; 
                        System.out.println("Bust! Your hand has a total greater than 21. ");
                        System.out.println("The dealer won. "); 
                        dealer.win1(); 
                    } else if(user.checkOver21(2)){
                        bust=true; 
                        System.out.println("Bust! Your other hand has a total greater than 21. ");
                        System.out.println("The dealer won. "); 
                        dealer.win1(); 
                    }
                    
                    //below asks the user to determine the value of their aces 
                    if(!bust){
                        boolean askAce=true; 
                        ArrayList<Integer> acesValues=new ArrayList<Integer>(); 
                        for(int x=0; x<user.getNumHands(); x++){
                            if(user.getNumHands()==2){
                                if(x==0){
                                    user.printHand(1); 
                                    System.out.println ("For your first hand: "); 
                                } else{
                                    user.printHand(2); 
                                    System.out.println("For your second (\"other\") hand: "); 
                                }
                            }
                            for(int r=0; r<user.getNumOfAces(x+1); r++){
                                askAce=true; 
                                while(askAce){
                                    System.out.println("Would you like ace #"+(r+1)+" to be a 1 or 11?"); 
                                    String response=input.nextLine().toLowerCase(); 
                                    if (response.equals("1")){ 
                                        acesValues.add(1); 
                                        askAce=false; 
                                    } else if(response.equals("11")){
                                        acesValues.add(11); 
                                        askAce=false; 
                                    } else{
                                        System.out.println("Please enter \"1\" or \"11\". "); 
                                    }
                                }
                            }

                            //this sets the handtotal according to what the user wants their aces to be 
                            int handTotal=0; 
                            if(user.getNumOfAces(x+1)>0){
                                for(int p=0; p<user.getHand(x+1).size(); p++){
                                    if(!user.getHand(x+1).get(p).isItAce()){
                                        handTotal+=user.getHand(x+1).get(p).getNum();
                                    } 
                                }
                                for(int p=0; p<acesValues.size(); p++){
                                    handTotal+=acesValues.get(p);
                                }
                            }
                            //this adds the handtotal to an arraylist to be referred to later 
                            handTotals.add(handTotal); 
                        }
                        System.out.println("Your hand total: "+handTotals.get(0)); 
                        if(user.getNumHands()==2){
                            System.out.println("Your other hand total: "+handTotals.get(1)); 
                        }
                    }
                    
                    if(user.getNumOfAces(1)>0){
                        if(handTotals.get(0)>21){
                            bust=true; 
                            System.out.println("Bust! Your hand has a total greater than 21. ");
                        } 
                    } else if(user.getNumOfAces(2)>0){
                        if(handTotals.get(1)>21){
                            bust=true; 
                            System.out.println("Bust! Your other hand has a total greater than 21. ");
                        }
                    }
                    
                    if(bust&&user.getNumHands()==1){
                        System.out.println("The dealer won. "); 
                        dealer.win1();
                    }
                }
                
                
                if(!bust&&!surrender){
                    for(int x=0; x<dealer.getNumHands(); x++){
                        if(user.getNumHands()==2){
                            if(x==0){
                                System.out.println ("For the dealer's first hand: "); 
                            } else{
                                System.out.println("For the dealer's (\"other\") hand: "); 
                            }
                        }
                        boolean dealerStand=false; 
                        while(!dealerStand){
                            dealerStand=dealer.hitOrStand(x+1); 
                            if(!dealerStand){
                                nextNumDeck=dealer.receiveCard(x+1, deck.getDeck()[nextNumDeck], nextNumDeck);
                                System.out.println("The dealer received another card."); 
                                nextNumDeck+=1; 
                            } else{
                                System.out.println("The dealer chose to stand."); 
                                dealerStand=true; 
                            } 
                            if(dealer.checkOver21(x+1)){
                                break; 
                            } 
                        }
                        if(dealer.checkOver21(x+1)){
                            bust=true; 
                            System.out.println("Bust! The dealer's hand has a total greater than 21. ");
                            System.out.println("You won. "); 
                            user.win1(); 
                        } 
                    }
                }

                System.out.println("The dealer reveals his cards. ");
                for(int x=0; x<dealer.getNumHands(); x++){
                    dealer.printHand(x+1);
                }
                
                if(!bust){
                    boolean dealer21=false; 
                    for(int x=0; x<dealer.getNumHands(); x++){
                        dealer21=dealer.check21(x+1);; 
                    }
                    boolean user21=false; 
                    for(int x=0; x<user.getNumHands(); x++){
                        user21=user.check21(x+1);; 
                        
                    }
                    if(dealer21){
                        System.out.println("Blackjack! The dealer's hand has a total of 21. ");
                        System.out.println("The dealer won. "); 
                        dealer.win1(); 
                    } else if(user21){
                        System.out.println("Blackjack! Your hand has a total of 21. ");
                        System.out.println("You won. "); 
                        user.win1(); 
                    } else{
                        int dealer21Dif=21-dealer.getHandTotal(1, "check21");
                        int dealer21Dif2=21-dealer.getHandTotal(2, "check21"); 
                        
                        int user21Dif=21-user.getHandTotal(1, "check21"); 
                        int user21Dif2=21-user.getHandTotal(2, "check21"); 
                        if(user.getNumOfAces(1)>0){
                            user21Dif=21-handTotals.get(0);  
                        }
                        if(user.getNumOfAces(2)>0){
                            user21Dif2=21-handTotals.get(0);  
                        }
                    
                        boolean dealerWin=false; 
                        boolean tie=false; 
                        //compares dealer's two hands against each, checks which is lower 
                        //if else statement compares each deck against each other to see which is lowest                         
                        if(dealer21Dif<=dealer21Dif2&&dealer21Dif>0){
                            if(user21Dif<=user21Dif2){
                                if(dealer21Dif<user21Dif){
                                    dealerWin=true; 
                                } else if(dealer21Dif==user21Dif){
                                    tie=true; 
                                }
                            } else{
                                if(dealer21Dif<user21Dif){
                                    dealerWin=true; 
                                } else if(dealer21Dif==user21Dif){
                                    tie=true; 
                                }
                            }
                        } else if(dealer21Dif2<=dealer21Dif&&dealer21Dif2>0){
                            if(user21Dif<=user21Dif2){
                                if(dealer21Dif2<user21Dif){
                                    dealerWin=true; 
                                } else if(dealer21Dif2==user21Dif){
                                    tie=true; 
                                }
                            } else{
                                if(dealer21Dif2<user21Dif){
                                    dealerWin=true; 
                                } else if(dealer21Dif2==user21Dif){
                                    tie=true; 
                                }
                            }
                        }
                        if(tie){
                            System.out.println("Tie! The dealer's hand and your hand have the same total. ");
                            System.out.println("The dealer won. "); 
                            dealer.win1();
                        }else if(dealerWin){
                            System.out.println("The dealer's hand is closer to 21. ");
                            System.out.println("The dealer won. "); 
                            dealer.win1();
                        } else{
                            System.out.println("Your hand is closer to 21. ");
                            System.out.println("You won. "); 
                            user.win1();
                        }
                    }
                
                }
            }
            
            try{
                PrintWriter out = new PrintWriter(savedScores);
                out.println(user.getScore());
                out.println(dealer.getScore());
                out.close();
            }catch(Exception e){
                 System.out.println("Your scores could not be saved.");
            }
            
            if(nextNumDeck>=94){
                System.out.println("There are only "+(104-nextNumDeck)+" cards left.");
                System.out.println("You really love this game, don't you? ");
                System.out.println("Sorry, you can't play again."); 
                System .out.println("See you next time.");
                System.out.println(); 
                break; 
            }
            
            boolean askPlayAgain=true; 
            while(askPlayAgain){
                System.out.println("Would you like to play again?"); 
                String response=input.nextLine().toLowerCase(); 
                if (response.equals("yes")){ 
                    System.out.println(); 
                    askPlayAgain=false; 
                } else if(response.equals("no")){
                    play=false; 
                    askPlayAgain=false; 
                } else{
                    System.out.println("Please enter \"yes\" or \"no\". "); 
                }
            }
        }
        System.out.println("Thank you for playing Blackjack."); 
    }
}
