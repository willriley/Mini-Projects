public class TextGenerator {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]); 
		String txt = StdIn.readAll();

		MarkovModel m = new MarkovModel(txt,k);
		String firstKgram = txt.substring(0,k);



		System.out.println(m.gen(firstKgram,T));
	}
}