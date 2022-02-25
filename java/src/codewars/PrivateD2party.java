import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;
/**
 * Hero is inviting his friends to the party.
He has n friends, numbered 0 through n-1. For each of his friends there is at most one other person the friend dislikes. You are given this information as a int[] a with n elements. For each i, a[i] is either the number of the person disliked by friend i, we have a[i]=i if friend i likes everybody else.

Hero is inviting his friends one at a time. Whenever he invites friend i, they will accept if and only if the friend a[i] didn't accept an earlier invitation. (That includes two cases: either Hero didn't invite friend a[i] yet, or he did but the friend rejected the invitation.)

Hero noticed that the order in which he invites his friends matters: different orders may produce different numbers of accepted invitations.
Find an order that will produce the most accepted invitations, and return their number.
 * @author jbadillo
 *
 */
public class PrivateD2party{
	
	public static void main(String[] args) {
		PrivateD2party p = new PrivateD2party();
		
		System.out.println(p.getsz(new int[]{0,1}));
		System.out.println(p.getsz(new int[]{1,0}));
		System.out.println(p.getsz(new int[]{1,2,0,4,5,3,0,1,2}));
	}
	
	/**
	 * The smart way, identify rings discard one per ring
	 * @param a
	 * @return
	 */
	public int getsz(int[] a){
		TreeSet<Integer> invited = new TreeSet<Integer> ();
		int rings = 0;
		for (int i = 0; i < a.length; i++) {
			//skip already marked
			if(invited.contains(i)) continue;
			//look for a ring
			TreeSet<Integer> ring = new TreeSet<>();
			ring.add(i);
			invited.add(i);
			while(true){
				//solo rings are not counted
				if(i == a[i]){
					break;
				}
				//if found a ring
				else if(ring.contains(a[i])){
					rings++;
					break;
				}
				//if invited but not in ring nowhere to go
				else if(invited.contains(a[i])){
					break;
				}
				//move
				i = a[i];
				ring.add(i);
				invited.add(i);
			}
		}
		//total - number of rings (I only need to skip one per ring)
		return a.length - rings;
	}

	
	/**
	 * The brute force way
	 * First discard the ones that like themselves
	 * Second invite the ones that have already invited enemies
	 * Third add the rest
	 * @param a
	 * @return
	 */
	public int getsz2(int[] a){
		TreeSet<Integer> invited = new TreeSet<Integer> ();
		LinkedList<Integer> order = new LinkedList<Integer>();
		LinkedList<Integer> notyet = new LinkedList<Integer>();

		//add all the guys without enemies
		for (int i = 0; i < a.length; i++) {
			if (i == a[i]) {
				order.add(i);
				invited.add(i);
			} else {
				notyet.add(i);
			}
		}
		boolean goon = true;
		while(goon){
			goon = false;
			//check all the remaining guests
			for(int j: notyet){
			//find one that has an invited enemy already
				if(invited.contains(a[j])){
					order.addFirst(j);
					invited.add(j);
				}
			}
		}
//		System.out.println(order);
//		System.out.println(invited);
		//DFS?
		Stack<Integer> stack  = new Stack<>();
		while (!notyet.isEmpty()) {
			stack.push(notyet.poll());
			while (!stack.empty()) {
				int k = stack.peek();
				if (invited.contains(k)) {
					stack.pop();
					continue;
				}
				// if the enemy is already invited
				if (invited.contains(a[k])) {
					stack.pop();
					order.addFirst(k);
					invited.add(k);
				//if this is not in the stack
				} else if(!stack.contains(a[k])) {
					stack.push(a[k]);
				}
				///ahhh no idea, invite it and pop it out
				else{
					stack.pop();
					order.addFirst(k);
					invited.add(k);
				}
			}
		}
		//score permutation
		invited.clear();
		int total = 0;
		for(int i: order){
			//if enemy was not invited yet
			if(!invited.contains(a[i])){
				total++;
				invited.add(i);
			}
		}
		System.out.println(order);
		return total;
	}

}
