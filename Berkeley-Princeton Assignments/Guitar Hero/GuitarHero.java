public class GuitarHero {
	public static void main(String[] args) {
		String kb = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
		GuitarString[] strings = new GuitarString[kb.length()];

		int numplots = 2000; // i'll be plotting the last 1000 samples
        
        StdDraw.setXscale(0, numplots); // sets the scale
        StdDraw.setYscale(-0.5, 0.5);	// for x and y coords

        double[] y = new double[numplots]; // array of y coords
        double[] time = new double[numplots]; // array of x coords
        int timestep = 0;
		

		for (int i=0; i<strings.length; i++) { // sets up each string
			double freq = 440.0 * Math.pow(2.0,(i - 24.0)/12.0);
			strings[i] = new GuitarString(freq);
		}

		for(int i = 0; i < numplots; i++) 
            time[i] = i;

		while (true) {

			if (StdDraw.hasNextKeyTyped()) {
				char string = StdDraw.nextKeyTyped();

				// plucks the corresponding string
				if (kb.indexOf(string) != -1)
					strings[kb.indexOf(string)].pluck();
				else
					System.out.println("That's not a string.");
			}

			double sample = 0.0;
			for (GuitarString g : strings) {
				sample+=g.sample();
				g.tic();
			}

			StdAudio.play(sample);

			if(timestep == numplots) { // plots the graph if the last 
                StdDraw.clear();	   // 1000 samples were collected
                StdDraw.polygon(time, y);
                timestep = 0;
            }
            y[timestep] = sample;
            timestep++;
		}

	}
	
}