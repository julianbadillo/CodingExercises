import java.util.Scanner;

public class Pyramids {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int a=1, b = n;
		boolean found = false;
		
		while (a <= n && !found) {
			b = n;
			while (b >= a) {
				if((a+b)*(b-a+1)/2 == n){
					found = true;
					break;
				}
				b--;
			}
			if(found) break;
			a++;
		}
		
		for (int i = a; i <= b; i++)
			System.out.println(new String(new char[i]).replace((char)0, '*'));
	}

}