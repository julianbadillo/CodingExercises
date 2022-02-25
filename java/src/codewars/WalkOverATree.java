import java.util.HashSet;
import java.util.LinkedList;

public class WalkOverATree {


	class Node{
		LinkedList<Node> children = new LinkedList<>();
		int h = 1;
		int id = 0;
		int visited = 0;
		@Override
		public String toString() {
			return Integer.toString(id);
		}
	}

	/**
	 * Ideas:
	 * - if H(tree) >= L+1 -> you can reach L+1 nodes -> easy check
	 * - With 2*(L-1) - 1 you can fill all nodes! -> easy check 
	 * - with less than N-1 you cannot fill all the nodes -> ?
	 * - generate all paths that have each node at most twice?
	 *    < 2^N
	 * @param parent
	 * @param L
	 * @return
	 */
	
	public int maxNodesVisited(int [] parent, int L){
		Node[] tree = new Node[parent.length+1];
		//build the tree
		for (int i = 0; i < tree.length; i++) {
			tree[i] = new Node();
			tree[i].id = i;
		}
		
		//fill the height behind
		for (int i = tree.length-1; i > 0; i--)
			//height
			tree[parent[i-1]].h = Math.max(1+tree[i].h, tree[parent[i-1]].h);
		System.out.println(tree[0].h+" ===== ");
		//easy case, if L fits max height
		if(L+1 <= tree[0].h)
			return L+1;

		//build the graph
		for (int i = 0; i < tree.length; i++) {
			if(i > 0){ //add edges
				tree[parent[i-1]].children.add(tree[i]);
				tree[i].children.add(tree[parent[i-1]]);
			}
		}
		
		
		//recursive version
//		this.max = 0;
//		HashSet<Node> visited = new HashSet<>();
//		getMaxReach(tree[0], L, visited);
//		return max;
		
//		//dynamic loop
		int[][] maxReach = new int[tree.length][L+1];
		
		// L = 0 (first column) then maxReach = 1
		for (int i = 0; i < maxReach.length; i++) 
			maxReach[i][0] = 1;
		
		// L > 0
		for (int l = 1; l <= L; l++) {
			//from back to front
			for (int i = maxReach.length - 1; i >= 0 ; i--) {
				//get the nodes childs
				int x=0, t;
				for(Node child: tree[i].children){
					t = maxReach[child.id][l-1];
					if(i < child.id) //if comes from lower, is new (moving forward)
						t++;
					//trim, cannot pass the tree length
					if(t > tree.length) t = tree.length;
					if(x < t) x = t;
				}
				maxReach[i][l] = x;
			}
		}
		
//		for(int []arr: maxReach)
//			System.out.println(Arrays.toString(arr));
		
		return maxReach[0][L];
	}
	
	int max;
	
	void getMaxReach(Node n, int L, HashSet<Node> visited){
		visited.add(n);
		n.visited++;
		max = Math.max(visited.size(), max);
//		System.out.println(n+","+L);
		if( L > 0)
			for(Node child: n.children){
				if(child.visited < 2){ //no more than 2 passes
					getMaxReach(child, L-1, visited);
				}
			}
		n.visited--;
		if(n.visited == 0)
			visited.remove(n);
	}


}