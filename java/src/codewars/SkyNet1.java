import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class SkyNet1 {

}


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Skynet1Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        
        HashMap<Integer, LinkedList<Integer>> graph = new HashMap<Integer, LinkedList<Integer>>();
        
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            //build the graph
            LinkedList<Integer> edge1, edge2;
            if(graph.containsKey(N1))
                edge1 = graph.get(N1);
            else{
                edge1 = new LinkedList<Integer>();
                graph.put(N1, edge1);
            }
            if(graph.containsKey(N2))
                edge2 = graph.get(N2);
            else{
                edge2 = new LinkedList<Integer>();
                graph.put(N2, edge2);
            }
            edge1.add(N2);
            edge2.add(N1);
        }
        HashSet<Integer> gates = new HashSet<Integer>();
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            gates.add(EI);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            // IDEAS
            // Greedy heuristic? shutdown the links to the closest gateway?
                // BFS from agent position to gates
                // sever edge incident to gateway, that is closest.
            Integer[] dist = new Integer[N]; //distances
            //BFS
            bfs(graph, SI, dist);
            //get the closest gateway
            int closest = getClosestNode(gates, dist);
            //get the closest incident node on the closest gate
            int closest2 = getClosestNode(graph.get(closest), dist);
            // sever
            System.out.println(closest+" "+closest2);
            graph.get(closest).remove((Integer)closest2);
            graph.get(closest2).remove((Integer)closest);
            
        }
    }

	private static int getClosestNode(Collection<Integer> nodes, Integer[] dist) {
		Iterator<Integer> ite1 = nodes.iterator();
		int closest = ite1.next();
		Integer mind = dist[closest];
		//a node with not-null distance
		while(ite1.hasNext()){
			int node = ite1.next();
			if(dist[node] == null)
				continue;
			if(mind == null || dist[node] < mind){
				closest = node;
				mind = dist[node];
			}
		}
		return closest;
	}
    
    static void bfs(HashMap<Integer, LinkedList<Integer>> graph, int start, Integer[] dist){
        boolean [] visited = new boolean[dist.length];
        dist[start] = 0;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(start);
        while(!queue.isEmpty()){
            int node = queue.poll();
            if(visited[node])
            	continue;
            int d = dist[node];
            for(Integer adj: graph.get(node)){
            	if(visited[adj])
            		continue;
                if(dist[adj] == null || d+1 < dist[adj])
                    dist[adj] = d + 1;
                queue.add(adj);
            }
            visited[node] = true;
        }
    }
}