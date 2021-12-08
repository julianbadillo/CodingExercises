
import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class SolutionZ {

    public static void main(String args[]) {
        ZerglingRush.main(args);
    }
}

public class ZerglingRush {

	
	char map[][];
	int H, W;
	
	public ZerglingRush(int H, int W, char[][] map) {
		this.H = H;
		this.W = W;
		this.map = map;
	}
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int W = in.nextInt();
        int H = in.nextInt();
        in.nextLine();
        char[][] map = new char[H][];
        for (int i = 0; i < H; i++)
            map[i] = in.nextLine().toCharArray();
        
        ZerglingRush zr = new ZerglingRush(H, W, map);
        
        zr.invade();
        zr.printAnswer();
        
        
	}


	int[][] move = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	int[][] round = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 } };
	
	private void invade() {
		//from all borders mark as invaded
		for (int i = 0; i < W; i++){
			if(map[0][i] == '.') //first row
				map[0][i] = 'i';
			if(map[H-1][i] == '.') //last row
				map[H-1][i] = 'i';
		}
		
		for (int i = 0; i < H; i++){
			if(map[i][0] == '.') //first column
				map[i][0] = 'i';
			if(map[i][W-1] == '.') //last column
				map[i][W-1] = 'i';
		}
		
		printAnswer();
		System.out.println();
		
		// invade until no change
		boolean changed = true;
		while(changed){
			changed = false;
			// go to empty blocks
			for (int i = 1; i < H-1; i++) {
				for (int j = 1; j < W-1; j++) {
					for (int k = 0; k < move.length; k++) {
						if(map[i][j] == '.' 
							&& map[i+move[k][0]][j+move[k][1]] == 'i'){
							// invade
							map[i][j] = 'i';
							changed = true;
						}
					}
				}
			}
			printAnswer();
			System.out.println();
		}
		
		
		
		// go to all building blocks
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if(map[i][j] == 'B'){
					// all addjacent invaded
					for (int k = 0; k < round.length; k++) {
						if(0 <= i+round[k][0] && i+round[k][0] < H &&
							0 <= j+round[k][1] && j+round[k][1] < W &&
							map[i+round[k][0]][j+round[k][1]] == 'i')
							map[i+round[k][0]][j+round[k][1]] = 'z';
					}
				}
			}
		}

		// invaded back to blocks
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if(map[i][j] == 'i')
					map[i][j] = '.';
			}
		}
	}


	private void printAnswer() {
		for (int i = 0; i < H; i++)
			System.out.println(new String(map[i]));
	}

}
