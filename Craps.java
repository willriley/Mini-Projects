
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
