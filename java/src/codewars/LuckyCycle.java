import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/***
 * 
    
This problem is about trees. A tree consists of some special points (called nodes), and some lines (called edges) that connect those points. Each edge connects exactly two nodes. If there are N nodes in a tree, there are exactly N-1 edges. The edges of a tree must connect the nodes in such a way that the tree is connected: it must be possible to get from any node to any other node by traversing some sequence of edges. Note that this implies that a tree never contains a cycle: for each pair of nodes there is exactly one way to reach one from the other without using the same edge twice.
Dog has a tree. The edges in Dog's tree have weights. As Dog likes the numbers 4 and 7, the weight of each edge is either 4 or 7.
Cat loves modifying trees. Cat is now going to modify Dog's tree by adding one new edge. The new edge will also have a weight that is either 4 or 7. The new edge will connect two nodes that don't already have an edge between them. Note that adding any such edge will create exactly one cycle somewhere in the tree. (A cycle is a sequence of consecutive edges that starts and ends in the same node.)
A cycle is balanced if the number of edges on the cycle is even, and among them the number of edges with weight 4 is the same as the number of edges with weight 7. Cat would like to add the new edge in such a way that the cycle it creates will be balanced.
You are given the description of Dog's current tree in int[]s edge1, edge2, and weight. Each of these int[]s will have exactly n-1 elements, where n is the number of nodes in Dog's tree. The nodes in Dog's tree are labeled 1 through n. For each valid i, Dog's tree contains an edge that connects the nodes edge1[i] and edge2[i], and the weight of this edge is weight[i].
Return a int[] with exactly three elements: {P,Q,W}. Here, P and Q should be the nodes connected by the new edge, and W should be the weight of the new edge. (Note that P and Q must be between 1 and N, inclusive, and W must be either 4 or 7.) If there are multiple solutions, return any of them. If there are no solutions, return an empty int[] instead.
 * @author jbadillo
 *
 */
class LuckyCycle{

    public static void main(String[] args) {
		int[] e1, e2, w, r;

		LuckyCycle l = new LuckyCycle();
		e1 = new int[]{1, 3, 2, 4};
		e2 = new int[]{2, 2, 4, 5};
		w = new int[]{4, 7, 4, 7};
		r = l.getEdge(e1, e2, w);
		System.out.println(Arrays.toString(r));

		e1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		e2 = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		w = new int[]{4, 4, 4, 4, 4, 4, 7, 7, 7, 7, 7, 7};
		r = l.getEdge(e1, e2, w);
		System.out.println(Arrays.toString(r));

	}

	class Edge{
		Node n1;
		Node n2;
		int w;
	}
	class Node{
		int id;
		List<Edge> edges = new LinkedList<>();
		public Node(int id) {
			this.id = id;
		}
		public boolean equals(Object obj) {
			return this.id == ((Node)obj).id;
		}
		boolean isAdjacent(Node n){
			for (Edge e : edges)
				if(n.id == e.n1.id || n.id == e.n2.id)
					return true;
			return false;
		}
	}

	public int [] getEdge(int[] edge1, int[] edge2, int[] weight){
		HashMap<Integer, Node> graph = new HashMap<>();

		for(int i = 0; i< edge1.length; i++){
			//get or create the nodes
			Node n1 = graph.get(edge1[i]);
			if(n1 == null){ 
				n1 = new Node(edge1[i]);
				graph.put(n1.id, n1);
			}
			Node n2 = graph.get(edge2[i]);
			if(n2 == null){ 
				n2 = new Node(edge2[i]);
				graph.put(n2.id, n2);
			}

			//create edge
			Edge e = new Edge();
			e.w = weight[i]==4?1:1000;
			//link all
			e.n1 = n1;
			e.n2 = n2;
			n1.edges.add(e);
			n2.edges.add(e);
		}
		HashSet<Node> visited = new HashSet<>();
		int total, fours, sevens;
		//try all combinations of edges
		for (int i=1; i<= edge1.length+1; i++) {
			for(int j=i+1; j<= edge1.length+1; j++){
				Node n1 = graph.get(i);
				Node n2 = graph.get(j);
				if(n1 == null || n2 == null) continue;
				//only non-existing edges
				if(!n1.isAdjacent(n2)){
					visited.clear();
					total = dfsCycle(n1,graph,n2, visited);
					fours = total%1000;
					sevens = total/1000;
					//if cycle imbalanced
					if(fours == sevens+1)
						return new int[]{n1.id, n2.id, 7};
					if(sevens == fours+1)
						return new int[]{n1.id, n2.id, 4};
				}

			}
		}
		//empty if no solution
		return new int[]{};
	}


	int dfsCycle(Node start, HashMap<Integer, Node> graph, Node end, Set<Node> visited) {
		int res;
		visited.add(start);
		for(Edge e: start.edges){
			//to avoid going back from the same
			Node next = start == e.n1? e.n2 : e.n1;
			if(visited.contains(next))
				continue;
			//if this is the end;
			if(next.id == end.id){
				return e.w;
			}
			else{
				res = dfsCycle(next, graph, end, visited);
				//if found add the weight
				if( res != 0) return res + e.w;
			}
		}
		//not found, return 0
		return 0;
	}

}