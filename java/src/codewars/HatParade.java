import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author jbadillo
 *
 *Problem Statement
    
    Mirko is a magician. He has decided to perform a trick that he wants you to solve. The trick consists of three steps:
	Mirko takes some mutually distinct hats and arranges them into a line.
	He picks up a red marker and uses it to write a positive integer onto each hat.
	Then, he picks up a blue marker and uses it to write another positive integer onto each hat. For each hat, the blue number is either the prefix sum or the suffix sum of all red numbers up to, and including, that hat.
	For example, suppose he picked up four hats. Let's call them A, B, C, and D, in their order from step 1. In step 2, he wrote the red number 10 onto hat A, 20 onto B, 30 onto C, and 40 onto D. Here is one possibility what he could write in step 3:
	For hat A, he chose the prefix sum, i.e., the sum of all red numbers from the beginning to this hat, inclusive. In this case, the sum is 10.
	For hat B, he chose the suffix sum, i.e., the sum of all red numbers from this hat to the end, inclusive. In this case, the sum is 20+30+40 = 90.
	For hat C, he also chose the suffix sum: 30+40 = 70.
	For hat D, he chose the prefix sum: 10+20+30+40 = 100.
	After step 3 he now has four hats, each with one red and one blue number. For example, hat C now has the red number 30 and the blue number 70.  
	Mirko has shown you some shuffled hats, each with a red and a blue number. Then he has asked you the following question: "How many different orders of these hats are consistent with the trick described above?"

	You are given the red numbers on all hats in the int[] value, and all the blue numbers in the int[] sum. For each valid i, the red number value[i] is on the same hat as the blue number sum[i]. Let X be the total number of orders that are consistent with the trick. Return the value (X modulo 1,000,000,007). Note that all hats are distinct, even if they have both the same red and the same blue number.
	Definition
	    
	Class: HatParade
	Method: getPermutation
	Parameters: int[], int[]
	Returns: int
	Method signature: int getPermutation(int[] value, int[] sum)
	(be sure your method is public)
	Limits
	    
	Time limit (s): 2.000
	Memory limit (MB): 256
	Stack limit (MB): 256
	Constraints
	- value will contain between 1 and 500 elements, inclusive.
	- sum will contain the same number of elements as value.
	- Each element of value will be between 1 and 10^9, inclusive.
	- Each element of sum will be between 1 and 10^9, inclusive.
 *
 */
// FACTS:
// if value(i) == sum(i) i can only be at the start of end
//   - no more than 2 values can have this criteria (easy discard)
// if sum(i) == sum(all values) then i can only be at start or end
//	- no more than 2 values can jave this criteria (easy discard)
// if sum(i) > sum(all values) then sum(i) cannot be build
//   - easy discard
//  sum(i) - value(i) = some permutation with other values
//IDEAS
//0 -> determine if a permutation can be built, if so, the number of permutations
//		by the combinatorial way
//   Perm = r1! r2! r3! (r1 = occurrence of value,sum)
//        x2 if odd
// How to determine if a permutation can be build
// 0. Build one permutation (heuristically cannot be done)
//   Ex  1 2 3
//       1 3 6 - can be done
//		 1 2 1
//       4 3 4 - cannot be done
//1. Calculate all possible sums between numbers
// if a sum cannot be build from numbers then false
//   --> infeasible (2^500 sums)
//2. Decide if a sum can be build from other numbers
//    
public class HatParade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HatParade h = new HatParade();

		int[] v = {2, 1, 3};
		int[] s = {3, 1, 6};
		System.out.println(h.getPermutation(v, s));

		v = new int[]{4, 4, 4, 4};
		s = new int[]{16, 16, 8, 8};
		System.out.println(h.getPermutation(v, s));
		v = new int[]{1, 2, 3, 4, 5, 6, 7};
		s = new int[]{1, 27, 6, 22, 18, 21, 7};
		System.out.println(h.getPermutation(v, s));
		
		v = new int[]{3, 3};
		s = new int[]{3, 3};
		System.out.println(h.getPermutation(v, s));
		
		v = new int[]{1, 1};
		s = new int[]{2, 2};
		System.out.println(h.getPermutation(v, s));

		v = new int[]{1, 1, 1, 1};
		s = new int[]{9, 1, 2, 3};
		System.out.println(h.getPermutation(v, s));

		v = new int[]{1, 1};
		s = new int[]{3, 3};
		System.out.println(h.getPermutation(v, s));

	}

	class Hat{
		int v;
		int s;
		public Hat(int v, int s) {
			this.v = v;
			this.s = s;
		}
		public boolean equals(Object obj) {
			Hat h = (Hat) obj;
			return this.v == h.v && this.s == h.s;
		}
		public String toString() {
			return String.format("<%d,%d>", this.v, this.s);
		}
	}

	class Place{
		int sumLeft;
		int sumRight;
		Hat hat;
		public String toString() {
			return hat.toString();
		}
	}


	public int getPermutation(int[] value, int[] sum){
		//easy case: 1 hat
		if(value.length == 1 )
			return value[0] == sum[0] ? 1 : 0;
		//permutations
		Place[] perm = new Place[value.length];
		LinkedList<Hat> hats = new LinkedList<>();
		int total = 0;
		for (int i = 0; i < perm.length; i++) {
			//easy discard
			if(value[i] > sum[i]) return 0;
			hats.add(new Hat(value[i], sum[i]));
			perm[i] = new Place();
			total += value[i];
		}

		Hat left=null, right=null;
		//find if any hay has equal sum and value or equal than total sum
		for (Iterator<Hat> ite = hats.iterator(); ite.hasNext();) {
			Hat hat = ite.next();
			if(hat.v == hat.s || hat.s == total){
				ite.remove();
				if(left == null)
					left = hat;
				else if(right == null)
					right = hat;
				else
					//more than two, no permutations possible
					return 0;
			}
		}
		//if one of two wasn't build easy discard
		if(left == null || right == null) return 0;

		int i = 0;
		int j = perm.length -1;
		//place righ and left, calculate sums
		perm[i].hat = left;
		perm[i].sumLeft = left.v;
		perm[i].sumRight = total;

		perm[j].hat = right;
		perm[j].sumLeft = total;
		perm[j].sumRight = right.v;
		int totalPerms = 2; //at least two (exchanging last and first)
		i++; j--;
		boolean added = true;
		while(!hats.isEmpty()){
			added = false;
			for (Iterator<Hat> ite = hats.iterator(); ite.hasNext();) {
				Hat hat = ite.next();
				//can be put on the left side
				if(perm[i-1].sumLeft + hat.v == hat.s  || 
						perm[i-1].sumRight == hat.s + perm[i-1].hat.v){
					ite.remove(); added = true;
					perm[i].hat = hat;
					perm[i].sumLeft = perm[i-1].sumLeft + hat.v;
					perm[i].sumRight = perm[i-1].sumRight - perm[i-1].hat.v;
					i++;
					//iterate on remaining elements how many are equal
					int equals = 1;
					for (Hat hat2 : hats) 
						if(hat.equals(hat2)) equals++;
					//multiply permutations
					totalPerms *= equals;
				}
				//can be put on the left side
				else if(perm[j+1].sumRight + hat.v == hat.s || 
						perm[j+1].sumLeft == hat.s + perm[j+1].hat.v ){
					ite.remove(); added = true;
					perm[j].hat = hat;
					perm[j].sumLeft = perm[j+1].sumLeft - perm[i-1].hat.v;
					perm[j].sumRight = perm[j+1].sumRight + hat.v;
					j--;
					//iterate on remaining elements how many are equal
					int equals = 1;
					for (Hat hat2 : hats) 
						if(hat.equals(hat2)) equals++;
					//multiply permutations
					totalPerms *= equals;
				}
			}
			//if no hat could be added - no possible permutation
			if(!added) return 0;

		}
		return totalPerms;	
	}


	public void validatePerm(Place[] perm) {
		//validate permutation
		for (Place p : perm) {
			//if not equal to at least one sum
			if(p.hat.s != p.sumLeft && p.hat.s != p.sumRight)
				System.out.println("ERROR!!"+p.sumLeft+" - "+p+" - "+p.sumRight);
		}
	}

}
