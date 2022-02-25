import java.lang.*;
import java.util.*;
/***
 *     
Limak is a little bear who loves to play.
 Today he is playing by moving some stones between two piles of stones.
 Initially, one of the piles has A and the other has B stones in it.
 
Limak has decided to perform a sequence of K operations.
 In each operation he will double the size of the currently smaller pile.
 Formally, if the current pile sizes are labeled X and Y in such a way that X <= Y,
 he will move X stones from the second pile to the first one.
 After this move the new pile sizes will be X+X and Y-X.
 
You are given the ints A, B, and K. Determine the pile sizes after Limak finishes all his operations.
 Return the size of the smaller of those piles.
 
Formally, suppose that the final pile sizes are labeled P and Q in such a way that P <= Q. Return P.
 * 
 * */
public class BearPlays{

    class Pair{
		int A;
		int B;
		Pair(int A, int B){
			this.A = A;
			this.B = B;
		}
		public boolean equals(Object o){
			Pair p = (Pair) o;
			return (p.A == this.A && p.B == this.B) 
					|| (p.A == this.B && p.B == this.A);
		}
		public int hashCode(){
			return this.A * this.B;
		}
	}

	public int pileSize(int A, int B, int K){
		int t, p, i=0;
						
		//find cycles
		ArrayList<Pair> list = new ArrayList<Pair>();
		HashSet<Pair> pairs = new HashSet<Pair>();
		for(;i<K && !pairs.contains(new Pair(A,B)); i++){
			list.add(new Pair(A,B));
			pairs.add(new Pair(A,B));
			if(A <= B){
				t = A;
				A += t;
				B -= t;
			}else{
				t = B;
				A -= t;
				B += t;
			}
			
		}
		//test if we reached already
		if( i == K )
			return A <= B ? A : B;
		//how many pairs are we ahead
		t = K - i;
		//proper pair
		Pair pair = list.get(t%list.size());
		
		return pair.A <= pair.B ? pair.A : pair.B;
	}

}