import java.util.Scanner;

public class DroppingEggs {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        System.out.println(eggs(in.nextInt()));        
	}
	
	static int eggs(int N){
		if(N <= 1) return 0;
		return (int) Math.floor(0.5 + Math.sqrt(8*N-7.0)/2.0);
		//return s - 1;
		/*
		int s = 2;
		while(true){
			int f = (s-2)*(s-1)/2 + s;			
			if(f >= N)
				return s - 1;
			s++;
		}*/
	}
}
