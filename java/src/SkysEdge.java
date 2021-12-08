import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class SolutionSkyEdge {

    public static void main(String args[]) {
        SkysEdge.main(args);
    }
}

public class SkysEdge {
	int Y, C;
	int[] ageCount;
	int MAX_AGE = 200;
	int minLEx, maxLEx;
	void init(){
		Scanner in = new Scanner(System.in);
        Y = in.nextInt();
        C = in.nextInt();
        int n = in.nextInt();
        ageCount = new int[MAX_AGE];
        
        for (int i = 0; i < n; i++) {
            int age = in.nextInt();
            int count = in.nextInt();
            ageCount[age] = count;
        }
	}
	
	void solve() {
		//
		for (int i = 40; i < MAX_AGE; i++) {
			if (survives(i)) {
				minLEx = i;
				break;
			}
		}
		for (int i = minLEx + 1; i < MAX_AGE; i++) {
			if (!survives(i)) {
				maxLEx = i - 1;
				break;
			}
		}
	}
	
	boolean survives(int lifeEx){
		// a local copy
		int[] ageCount = Arrays.copyOf(this.ageCount, this.ageCount.length);
		
		int year = 0;
		int p = IntStream.of(ageCount).sum();
		while(year < Y){
			// everyone gets older 1 year
			for (int i = ageCount.length-1; i > 0; i--)
				ageCount[i] = ageCount[i - 1];
			// everyone older than life Ex, dies
			for (int i = lifeEx + 1; i < ageCount.length; i++)
				ageCount[i] = 0;
			// babies for every ten people between 20 and lifeEx / 2
			int fert = 0;
			for(int i = 20; i <= lifeEx/2; i++)
				fert += ageCount[i];
			ageCount[0] = fert / 10;
			
			p = IntStream.of(ageCount).sum();
			if(p > C)
				break;
			year++;
		}
		if(200 <= p && p <= C && year == Y)
			return true;
		
		return false;
	}
	
	String answer(){
		return minLEx+" "+maxLEx;
	}
	
	public static void main(String[] args) {
		SkysEdge se = new SkysEdge();
		se.init();
		se.solve();
        System.out.println(se.answer());
	}
}
