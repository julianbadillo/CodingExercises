import java.util.Collections;
import java.util.Scanner;

import javax.print.attribute.IntegerSyntax;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class SolutionRC {

    public static void main(String args[]) {
        RollerCoaster.main(args);
    }
}

public class RollerCoaster {
	
	int inertia;
	int W, H, c;
	char [] mat;
	
	public static void main(String[] args) {
		(new RollerCoaster()).run();
	}
	
	void run(){
		Scanner in = new Scanner(System.in);
        inertia = in.nextInt();
        W = in.nextInt();
        H = in.nextInt();
        in.nextLine();
        char [][] mat2 = new char[H][];
        for (int i = 0; i < H; i++)
            mat2[i] = in.nextLine().toCharArray();
        
        // flatten
        mat = new char[W];
        for (int i = 0; i < W; i++) {
			for (int j = 0; j < H; j++)
				if(mat2[j][i] != '.') mat[i] = mat2[j][i];
		}
        
        simulate();
        System.out.println(c);
	}
	
	void simulate(){
		c = 0;
		int dir = 1;
		while(true){
			System.err.println("c = "+c+" i="+inertia);
			if(mat[c] == '_' && inertia == 0)
				break;
			if(dir == 1){
				if(mat[c] == '\\')
					inertia += 9;
				else if(mat[c] == '/'){
					if(inertia >= 10)
						inertia -= 10;
					else
						dir = - dir;
				}
				else if(mat[c] == '_')
					inertia--;
			} else if(dir == -1) {
				if(mat[c] == '/')
					inertia += 9;
				else if(mat[c] == '\\'){
					if(inertia >= 10)
						inertia -= 10;
					else
						dir = - dir;
				}
				else if(mat[c] == '_')
					inertia--;
			}
			
			if(inertia == 0){
				if(mat[c] == '\\')
					dir = 1;
				else if(mat[c] == '/')
					dir = -1;
				else if(mat[c] == '_')
					dir = 0;
			}
			
			c += dir;
			
			if(c == 0 || c == W-1)
				break;
		}
	}
	
}
