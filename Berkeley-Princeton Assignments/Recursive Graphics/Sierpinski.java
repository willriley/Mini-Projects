public class Sierpinski {


	// height of equilat Δ w/ sides of specified length
	private static double height(double length) { // works
		return (Math.sqrt(3.0)/2.0)*length;
	}

	// draws equilat Δ w/ bottom vertex (x,y) of specified side length
	private static void fillΔ(double x, double y, double length) { // works
		double[] xCoords = {x, x+(length/2), x-(length/2)};
		double[] yCoords = {y, y+height(length), y+height(length)};
		StdDraw.filledPolygon(xCoords,yCoords);
	}

	// draws sierpinski Δ of order n, s.t. the largest filled Δ 
	// has bottom vertex (x,y) and sides of the specified length
	private static void sierpinski(int n, double x, double y, double length) {
			if (n==0) return;


			fillΔ(x,y,length);
			sierpinski(n-1, x-length/2, y, length/2);
			sierpinski(n-1, x+length/2, y, length/2);
			sierpinski(n-1, x, y+height(length),length/2);
	}



	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		
		StdDraw.line(0,0,1,0);
		StdDraw.line(0,0,0.5,Math.sqrt(3.0)/2.0);
		StdDraw.line(1,0,0.5,Math.sqrt(3.0)/2.0);
		
		sierpinski(N,0.5,0,0.5);
	}
		
}