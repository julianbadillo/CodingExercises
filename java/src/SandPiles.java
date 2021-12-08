import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

class SolutionSP {

    public static void main(String args[]) {
    	SandPiles.main(args);
    }
}

public class SandPiles {
	enum Dir {
		U(0, -1), D(0, 1), L(-1, 0), R(1, 0);
		int dx, dy;

		Dir(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int [][] m1 = new int[n][];
        int [][] m2 = new int[n][];
        
        if (in.hasNextLine())
            in.nextLine();
        
        for (int i = 0; i < n; i++) 	
    		m1[i] = in.nextLine().chars()
    			.map(c -> c - '0')
            	.toArray();
    
        for (int i = 0; i < n; i++)
        	m2[i] = in.nextLine().chars()
        			.map(c -> c - '0')
                	.toArray();
        
        for (int i = 0; i < n; i++)
        	for (int j = 0; j < n; j++)
        		m1[i][j] += m2[i][j];
        
        boolean changed = true;
        while(changed){
        	changed = false;
            for (int i = 0; i < n; i++)
            	for (int j = 0; j < n; j++)
            		if(m1[i][j] > 3){
            			changed = true;
            			m1[i][j] -= 4;
            			for(Dir d : Dir.values()){
							if (d.dx + i < 0 || n <= d.dx + i)
								continue;
							if (d.dy + j < 0 || n <= d.dy + j)
								continue;
            				m1[d.dx + i][d.dy + j] ++;
            			}
            		}
        }
        
        for (int i = 0; i < n; i++)
        	System.out.println(Arrays.stream(m1[i]).mapToObj(Integer::toString).collect(Collectors.joining()));
        
	}
	
	

}
