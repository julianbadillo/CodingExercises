import java.util.Scanner;

class SolutionF {

	public static void main(String[] args) {
		Folding.main(args);

	}

}

class Folding {
	static int u = 1, r = 1, d = 1, l = 1;
	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String f = in.nextLine();
        String s = in.nextLine();
        
        for(char c: f.toCharArray())
			if(c == 'U'){
				d += u;
				l *= 2;
				r *= 2;
				u = 1;
			}
			else if(c == 'D'){
				u += d;
				l *= 2;
				r *= 2;
				d = 1;
			}
			else if(c == 'R')
			{
				l += r;
				u *= 2;
				d *= 2;
				r = 1;
			}
			else if(c == 'L'){
				r += l;
				u *= 2;
				d *= 2;
				l = 1;
			}
       
        if(s.equals("U"))
        	 System.out.println(u);
		if(s.equals("R"))
			System.out.println(r);
		if(s.equals("D"))
			System.out.println(d);
		if(s.equals("L"))
			System.out.println(l);
    }


	int side(String side) {
		if(side.equals("U"))
			return u;
		if(side.equals("R"))
			return r;
		if(side.equals("D"))
			return d;
		return l;
	}


	void fold(String order) {
		
				
	}
	
}
