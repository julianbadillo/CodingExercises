import java.util.stream.IntStream;

public class Conversion {

	class Num {
		char I, V, X;
		Num(char i, char v, char x) {
			this.I = i; this.V = v; this.X = x;
		}
		String convert(int n){
			StringBuilder s = new StringBuilder();
			if(n <= 3)
				for (int i = 0; i < n; i++)
					s.append(I);
			else if(n == 4)
				s.append(I).append(V);
			else if(n <= 8){
				s.append(V);
				for (int i = 0; i < n - 5; i++)
					s.append(I);
			}
			else if(n == 9)
				s.append(I).append(X);
			return s.toString();	
		}
	}
	
	Num[] v = {
			new Num('I', 'V', 'X'),
			new Num('X', 'L', 'C'),
			new Num('C', 'D', 'M'),
	};
	
    public String solution(int n) {
    	StringBuilder bf = new StringBuilder();
    	
    	bf.insert(0,v[0].convert(n%10));
    	n /= 10;
    	if(n > 0)
    		bf.insert(0, v[1].convert(n%10));
    	n /= 10;
    	if(n > 0)
    		bf.insert(0, v[2].convert(n%10));
    	n /= 10;
    	if(n > 0){
    		for (int i = 0; i < n; i++) 
    			bf.insert(0, v[2].X);
    	}	
    	return bf.toString();
    }
}