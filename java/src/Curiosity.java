import java.util.Base64;

public class Curiosity {

	public static void main(String[] args) {
		
		makeURL();
	}
	static void makeURL(){
		byte [] e = Base64.getEncoder().encode("curiosity".getBytes());
		String sd = new String(e);
		System.out.println("https://"+sd+".uptake.com");
		e = Base64.getEncoder().encode(sd.getBytes());
		sd = new String(e);
		System.out.println("https://"+sd+".uptake.com");
		e = Base64.getEncoder().encode("frankenstein".getBytes());
		sd = new String(e);
		System.out.println("https://"+sd+".uptake.com");
	}
	// good luck

}
