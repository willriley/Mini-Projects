import java.util.*;
public class Roll {
    private int dice1 = (int)(Math.random()*5 + 1);
    private int dice2 = (int)(Math.random()*5 + 1);
    private int point = 0;
    private boolean done = false;
    
    public int getSum() {
        return dice1+dice2;
    }
    
    public boolean getStatus() {
        return done;
    }
     
    public void reRoll() {
        dice1 = (int)(Math.random()*5 + 1);
        dice2 = (int)(Math.random()*5 + 1);
    }
    
    public void print() {
        System.out.println("You rolled " + dice1 + " + "
                + dice2 + " = " + getSum());
    }
    
    public void firstEval(int sum) {
        switch(sum) {
            case 2:
            case 3:
            case 12:
                System.out.println("You lose");
                done = true;
                break;
            case 7:
            case 11:
                System.out.println("You win");
                done = true;
                break;
            default:
                System.out.println("point is " + sum);
                point = sum;
                break;
        }
    }
    
    public void eval(int sum) {
        if (sum==7) {
            done = true;
            System.out.println("You lose");
        } else if (sum==point) {
            done = true;
            System.out.println("You win");
        } else {
            System.out.println("point is " + sum);
            point = sum;
        } 
    }
    
    
    
}
