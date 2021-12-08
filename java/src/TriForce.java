import java.util.Collections;
import java.util.Scanner;

public class TriForce {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for (int i = 0; i < n; i++)
			System.out.println((i>0?r(2*n-i-1, " "):("."+r(2*n-i-2, " "))) + r(2*i+1, "*"));
		
		for (int i = 0; i < n; i++)
			System.out.println( r(n-i-1, " ") + r(2*i+1, "*") + r(2*n-2*i-1, " ") + r(2*i+1, "*"));
		in.close();
	}
	static String r(int n, String s){
		return String.join("", Collections.nCopies(n, s));
	}
}
