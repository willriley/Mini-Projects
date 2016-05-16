import java.util.*;
public class Main {
    
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String[] words = {"hegemony", "eschew", "metanoia", "logos", "serendipity",
            "swoon", "currency", "scheme", "swindle", "happenstance", "jiujitsu",
            "epitomize", "rhubarb", "apropos", "tripartite"};
        
        
        Hangman h = new Hangman(words[(int)(Math.random()*words.length)]);
        run(h,kb);
    }
    
    public static void run(Hangman h, Scanner sc) {
        
        while(h.getFaults()<10 & h.displayWord().contains("*")) {
            System.out.print("(Guess) Enter a letter in word "
                    + h.displayWord() + " >> ");
            String l = sc.next();
            
            h.guess(l);
        }
        
        if (h.displayWord().contains("*"))
            System.out.println("You lost! The word was " + h.getRealWord() + ".");
        else
            System.out.println("You win! The word was " + h.getRealWord() + 
                    ". You made " + h.getFaults() + " bad guesses.");
    }
    
}
