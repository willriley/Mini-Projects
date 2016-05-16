/*
    Simulates a game of craps, a popular gambling game
    that uses two dice. 
    
    Rules: Roll two dice. Check the sum of the two dice. 
    If the sum is 2, 3, or 12 (called craps), you lose; 
    if the sum is 7 or 11 (called natural), you win; 
    anything else establishes a "point". Continue rolling 
    until either a 7 or the same point value is rolled. 
    If 7 is rolled, you lose. If the point is rolled, you win.
*/
public class Craps {
    public static void main(String[] args) {
        Roll dice = new Roll();
        
        dice.print();
        dice.firstEval(dice.getSum());
        
        while(!dice.getStatus()) {
            dice.reRoll();
            dice.print();
            dice.eval(dice.getSum());
        }
        

    }
    
    
}
