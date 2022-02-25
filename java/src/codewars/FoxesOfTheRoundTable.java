import java.util.*;
import java.lang.*;
/*
Problem Statement
    
There are n foxes, numbered 0 through n-1. You are given a int[] h with n elements. The elements of h are the heights of those foxes. Your task is to arrange all foxes around a round table.  Given an arrangement of foxes, let D be the largest height difference between adjacent foxes. For example, suppose that four foxes with heights { 10, 30, 20, 40 } sit around the table in this order. The height differences are |10-30|=20, |30-20|=10, |20-40|=20, and |40-10|=30. (Note that the last fox is also adjacent to the first one, as this is a round table.) Then, the value D is max(20,10,20,30) = 30.  Find an arrangement of the given foxes for which the value D is as small as possible. Return a permutation of foxes that describes your arrangement. I.e., return a int[] with n elements: the numbers of foxes in order around the table. If there are multiple optimal solutions, you may return any of them.
Definition
    
Time limit (s):
2.000
Memory limit (MB):
256
Stack limit (MB):
256
Constraints
-
h will contain between 3 and 50 elements, inclusive.
-
Each element in h will be between 1 and 1,000, inclusive.
Examples
0)
{1,99,50,50}
Returns: {0, 3, 1, 2 }
In the optimal solution the foxes with heights 1 and 99 mustn't be adjacent. Hence, the heights of foxes have to be 1, 50, 99, 50, in this cyclic order, and the optimal value of D is 49. One permutation that produces this order of foxes is 0, 3, 1, 2.
1)
{123,456,789}
Returns: {0, 1, 2 }
Whatever we do, the result will always be 789-123.
2)
{10,30,40,50,60}
Returns: {0, 1, 4, 3, 2 }
The permutation {0, 1, 4, 3, 2 } specifies that the heights of foxes are in the following order: 10, 30, 60, 50, 40.
3)
{1,2,3,4,8,12,13,14 }
Returns: {0, 1, 2, 3, 5, 6, 7, 4 }

4)
{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 }
Returns: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 }
*/

public class FoxesOfTheRoundTable{

	private static class Fox implements Comparable<Fox>{
		int i;
		int h;
		Fox(int i, int h){
			this.i = i;
			this.h = h;
		}
		public int compareTo(Fox f){
			return this.h - f.h;
		}
	}
	public int[] minimalDifference(int[] h){
		
		Fox[] foxes = new Fox[h.length];
		for(int i=0; i<h.length; i++) foxes[i] = new Fox(i, h[i]);
		//sort them in increasing order
		Arrays.sort(foxes);		
		
		LinkedList<Fox> result = new LinkedList<Fox>();
		//fist three elements
		result.add(foxes[0]);
		result.add(foxes[1]);
		result.add(foxes[2]);
		
		for(int k=3; k<foxes.length; k++){
			Fox f = foxes[k];
			//insert in th best place
			int best = 0;
			//difference to insert in the middle
			int minDif = Math.abs(result.getFirst().h - f.h) +
						 Math.abs(result.getLast().h - f.h);
						 
			int i = 1, diff;
			Iterator<Fox> ite = result.iterator();
			//search the best place to put our fox
			Fox f1 = ite.next();
			while(ite.hasNext()){
				Fox f2 = ite.next();
				diff = Math.abs(f1.h - f.h) + Math.abs(f2.h-f.h);
				if( diff < minDif){
					minDif = diff;
					best = i;
				}
				f1 = f2;
				i++;
			}
			//insert in best place
			result.add(best, f);
			
		}
		
		int[] res = new int[h.length];
		int i=0;
		for(Fox f: result){
			res[i] = f.i;
			i++;
		}
		return res;
		
	}
	

}
