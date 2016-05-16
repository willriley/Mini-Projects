import java.util.*;
public class StringSort {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String ip = kb.next();
        System.out.println(sort(ip));
    }
    
    public static String sort(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        
        return String.valueOf(arr);
    }
    
}
