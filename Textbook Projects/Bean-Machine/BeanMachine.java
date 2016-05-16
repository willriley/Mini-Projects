/*
    Simulates a bean machine when given the number of balls
    to drop and the number of slots in the machine. It randomly 
    generates a path string, counts the # of R's in the string,
    and textually displays how many balls landed in 
    each slot (kind of like a histogram).
*/

import java.util.*;
public class BeanMachine {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter the number of balls to drop: ");
        int balls = kb.nextInt();
        String[] paths = new String[balls];
        
        System.out.print("Enter the number of slots in the bean machine: ");
        int sNum = kb.nextInt();
        int[] slots = new int[sNum];
        
        System.out.println();
        
        for (int i=0; i<paths.length; i++) { 
            paths[i] = pathGen();
            System.out.println(paths[i]);
        }    
        
        System.out.println();
        slots = Arrays.copyOf(slotFreq(paths), sNum);
        printSlots(slots);
    }
    
    public static int[] slotFreq(String[] paths) {
        int[] arr = new int[paths.length];
        
        for (String s : paths) {
            int rCount = 0;
            // b/c the # of Rs per path gives
            // the slot where the ball falls.
            // e.g RRLLLLL falls into slots[2]
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i)=='R')
                    rCount++;
            }
            arr[rCount]++;
        }
        
        return arr;
    }
    
    public static String pathGen() { 
        String path = "";
        
        for(int i=0; i<7; i++) {
            int rand = (int)(Math.round(Math.random()));
            
            if(rand==0)
                path+="L";
            else
                path+="R";
        }
        return path;     
    }
    
    public static void printSlots(int[] arr) {
        for (int i=max(arr); i>0; i--) { // line number
            
            for (int j : arr) {
                if(j>=i && j!=0)
                    System.out.print("0");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    public static int max(int[] arr) {
        int max = 0;
        for (int i : arr) {
            if(max<i)
                max = i;
        }
        
        return max;
    }
}
