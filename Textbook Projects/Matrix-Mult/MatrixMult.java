import java.util.Scanner;
/*
    Takes in two 3x3 matrices and 
    multiplies them.
*/
public class MatrixMult {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        double[][] m1 = new double[3][3];
        double[][] m2 = new double[3][3];
        
        System.out.print("Enter matrix 1: ");
        readInput(m1,kb);
        
        System.out.print("Enter matrix 2: ");
        readInput(m2,kb);
        
        double[][] prod = mult(m1,m2);
        
        System.out.println("The multiplication of the matrices is");
        print(m1,m2,prod);        
    }
    
    public static void print(double[][] a, double[][] b, double[][] c) {
        for (int i=0; i<a.length; i++) {
            if (i==1) {
                printRow(a,i);
                System.out.print("  *  ");
                printRow(b,i);
                System.out.print("  =  ");
                printRow(c,i);
            }
            else {
                printRow(a,i);
                printSpace();
                printRow(b,i);
                printSpace();
                printRow(c,i);
            }
            System.out.println();
        }
    }
    
    public static void readInput(double[][] matrix, Scanner sc) {
        for (int row=0; row<matrix.length; row++) {
            for (int col=0; col<matrix[row].length; col++) 
                matrix[row][col] = sc.nextDouble();
        }
    }
    
    public static double[][] mult(double[][] a, double[][] b) {
        double[][] prod = new double[a.length][a.length];
        
        for (int i=0; i<a.length; i++) {
            for (int j=0; j<a.length; j++) {
                for (int k=0; k <a.length; k++)
                    prod[i][j] += a[i][k]*b[k][j];
            }
        }
        
        return prod;
    }
    
    public static void printRow(double[][] a, int row) {
        for (double n : a[row])
            System.out.print(Math.round(n * 10d) / 10d + " ");
    }
    
    public static void printSpace() {
        System.out.print("     ");
    }
}
