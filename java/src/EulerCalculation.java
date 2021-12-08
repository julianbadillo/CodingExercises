
public class EulerCalculation {

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			double e = Math.pow(1 / 2.0/(double)i + 1, 2.0*i);
			System.out.println(e);
			
		}
		System.out.println();
		double e2 = 0.0;
		double fact = 1;
		for(int k = 0; k < 10; k++){
			e2 += 1 / fact;
			fact *= k == 0? 1: k;
			System.out.println(e2);
		}
		
		
		System.out.println();
		System.out.println(Math.E);
	}

}
