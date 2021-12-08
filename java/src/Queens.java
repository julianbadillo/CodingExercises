import java.util.ArrayList;
import static java.util.Collections.swap;
import static java.util.Collections.sort;

import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class Queens {

	/**
	// Ideas - try all brute-force combinations?
	// choose(11^2, 11) = 10^15 too many * 11*11 = time to evaluate if a combination is good.
	// We know:
	 * - One queen must be on every row
	 * - one queen most be on every column
	 * - less combinations
	 * 11! = 10^7 * 11*11 (evaluate if an arrange is good, diagonals) doable
	*/
	
	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(solution(n));
    }
	
	
	static int solution(int n){
		q = (ArrayList<Integer>)range(0,n).mapToObj(i->i).collect(Collectors.toList());
		
		boolean[][] board = new boolean[n][n];
		int count = 0;
		int countn = 0;
		// get all permutations of n elements
		while(permutate()){
			if(!consecutives())
				count++;
		}
		return count;
	}
	
	static boolean consecutives(){
		// increasing
		for (int i = 0; i < q.size() - 1; i++) {
			for (int j = 1; i + j < q.size(); j++) {
				if (q.get(i) + j == q.get(i + j) || q.get(i) - j == q.get(i + j))
					return true;
			}
		}
		return false;
	}
	
	static boolean collision(boolean b[][]){
		int n = b.length;
		int r = 0, c = 0;
		// top right triangle
		for (r = 0, c = 0; c < n; c++) {
			int k = 0;
			for (int dr = 0, dc = 0; true; dr++, dc++) {
				if (r + dr >= n || c + dc >= n)
					break;
				if (b[r + dr][c + dc])
					k++;
			}
			if (k > 1)
				return true;
		}
		// bottom left triangle
		for (r = 0, c = 0; r < n; r++) {
			int k = 0;
			for (int dr = 0, dc = 0; true; dr++, dc++) {
				if (r + dr >= n || c + dc >= n)
					break;
				if (b[r + dr][c + dc])
					k++;
			}
			if (k > 1)
				return true;
		}
		// top left
		for (r = 0, c = 0; c < n; c++) {
			int k = 0;
			for (int dr = 0, dc = 0; true; dr++, dc--) {
				if (r + dr >= n || c + dc < 0)
					break;
				if (b[r + dr][c + dc])
					k++;
			}
			if (k > 1)
				return true;
		}
		
		// bottom right
		for (r = 0, c = n - 1; r < n; r++) {
			int k = 0;
			for (int dr = 0, dc = 0; true; dr++, dc--) {
				if (r + dr >= n || c + dc < 0)
					break;
				if (b[r + dr][c + dc])
					k++;
			}
			if (k > 1)
				return true;
		}
		return false;
	}
	
	static ArrayList<Integer> q;
	
	static boolean permutate(){
		
		// look how far is it inverted
		int i = q.size() - 1;
		for (; i > 0 && q.get(i - 1) > q.get(i); i--);
		
		// if everything inverted, break
		if(i == 0) return false;
		
		// if none inverted, easy swap
		if(i == q.size() - 1)
			swap(q, i, i-1);
		else{
			// the next element
			int f = q.get(i-1) + 1;
			int j;
			// needs to be the min greater the
			for(j = q.indexOf(f); j < i - 1; f++)
				j = q.indexOf(f+1);
			// swap it to first position
			swap(q, i - 1, j);
			// sort only sublist
			sort(q.subList(i, q.size()));
		}
		return true;
	}
	/*
	static void swap(int [] v, int i, int j){
		int temp = v[i];
		v[i] = v[j];
		v[j] = temp;
	}*/
	
	static ArrayList<String> perms = new ArrayList<>();
	static ArrayList<Integer> a;
	
	static ArrayList<String> permR(){
		
		// base cases
		if(a.size() == 1)
			perms.add(a.get(0)+"");
		else if(a.size() == 2){
			perms.add(a.get(0)+""+a.get(1));
			perms.add(a.get(1)+""+a.get(0));
		}
		// recursive cases
		else{
			for (int i = 0; i < a.size(); i++) {
				// delete
				int first = a.remove(i);
				// permutate the remaining ones
				permR();
				// add to results
				for (int j = 0; j < perms.size(); j++)
					if(perms.get(j).length() == a.size())
						perms.set(j, first + perms.get(j));
				// add back
				a.add(i, first);
			}
		}
		return perms;
	}
	
}
