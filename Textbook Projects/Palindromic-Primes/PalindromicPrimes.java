import java.util.Scanner;
/*
    Prints n amount of palindromic primes.
*/
public class PalindromicPrimes {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("How many palindromic primes do you want? ");
        int n = kb.nextInt();
        System.out.println("");
        printPalPrimes(n);
    }
    
    public static void printPalPrimes(int nPalPrimes) {
        int count = 0;
        int tester = 2;
        
        while (count < nPalPrimes) {
            if (isPalindrome(tester) & isPrime(tester)) {
                count++;
                if (count%11==0) {
                    System.out.println();
                    System.out.print(tester + " ");
                }    
                else
                    System.out.print(tester + " ");
            }    
            tester++;
        }
    }
    
    public static boolean isPalindrome(int n) {
        return reverse(n)==n;
    }
    
    public static boolean isPrime(int n) {
        for (int divisor = 2; divisor <= Math.sqrt(n); divisor++) {
            if(n%divisor==0)
                return false;
        }
        return true;
    }
    
    public static int reverse(int n) {
        String sheesh = Integer.toString(n);
        int[] digits = new int[sheesh.length()];
        String reversed = "";
        
        
        for (int i = 0; i < sheesh.length(); i++) {
            digits[i] = n%10;
            n/=10;
        }
        
        for (int i = 0; i < digits.length; i++)
            reversed+=digits[i];
        
        return Integer.parseInt(reversed);
    }
    
}
