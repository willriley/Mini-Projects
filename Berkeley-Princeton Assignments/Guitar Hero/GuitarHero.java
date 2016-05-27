public class GuitarHero {
	public static void main(String[] args) {

		GuitarString[] keys = new GuitarString[37];
		String kb = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

		for (int i=0; i<keys.length; i++) {
			double freq = 440.0 * Math.pow(2.0,(i - 24.0)/12.0);
			keys[i] = new GuitarString(freq);
		}

		while (true) {

			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();

				// plucks the corresponding string
				if (kb.indexOf(key) != -1)
					keys[kb.indexOf(key)].pluck();
				else
					System.out.println("That's not a key.");
			}

			double sample = 0.0;

			for (GuitarString g : keys)
				sample+=g.sample();

			StdAudio.play(sample);

			for (GuitarString g : keys)
				g.tic();
		}

	}
	
}