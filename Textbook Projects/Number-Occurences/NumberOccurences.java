import java.util.*;
public class NumberOccurences {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter the integers between 1 and 100: ");
        ArrayList<Integer> ip = new ArrayList<>();
        readInput(ip,kb);
        countOccurences(ip);
    }
    
    public static void countOccurences(ArrayList<Integer> a) {
        int[] nums = new int[98];
        
        for (int i=0; i<a.size(); i++)
            nums[a.get(i)]++;
        
        for (int j=0; j<nums.length; j++) {
            if (nums[j]==1)
                System.out.println(j + " occurs 1 time");
            else if (nums[j]>1)
                System.out.println(j + " occurs " + nums[j] + " times");
        }
    }
    
    public static ArrayList<Integer> readInput(ArrayList<Integer> a, Scanner sc) {
        while (true) {
            int n = sc.nextInt();
            if (n>0 && n<100)
                a.add(n);
            
            if (n==0)
                break;
        }
        return a;
    }  
}
