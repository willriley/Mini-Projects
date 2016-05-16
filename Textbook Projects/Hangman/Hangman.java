import java.util.*;
public class Hangman {
    private String word;
    private int faults;
    private String guessedLetters;
    
    public Hangman(String newWord) {
        word = newWord.toLowerCase();
        faults = 0;
        guessedLetters = "";
    }
    
    public int getFaults() {
        return faults;
    }
    
    public String guessedLetters() {
        return guessedLetters;
    }
    
    public String getRealWord() {
        return word;
    }
    
    public void guess(String letter) {
        if (!(guessedLetters.contains(letter))) { 
            guessedLetters += letter;        
            if (!(word.contains(letter))) {
                faults++;
                System.out.println(letter + " is not in the word");
            }    
        } 
        else 
            System.out.println("You already guessed " + letter);
    }
    
    public String displayWord() {
        String hidden = "";
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.contains("" + word.charAt(i))) 
                hidden+=word.charAt(i);
            else
                hidden+="*";
        }
        return hidden;
    }
    
}
