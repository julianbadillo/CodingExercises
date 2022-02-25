import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SkyNet2 {

	public static void main(String[] args) {
		Skynet2Player.main(args);
	}
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * Strategies tried:
 *   - Closest gate (NO)
 *   - Most dangerous node, then closest (NO)
 * Other ideas
 *   - Define a measurement of danger
 *   - Backtrackng? try the path that keeps the most gates open
 **/
class Skynet2Player {

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
            
            //select closest incident nodes in which it has the greatest danger
            int [] edge = getMostDangerousEdge(gates, graph, dist);                  
            
            // sever
            System.out.println(edge[0]+" "+edge[1]);
            graph.get(edge[0]).remove((Integer)edge[1]);
            graph.get(edge[1]).remove((Integer)edge[0]);
            
        }
    }

	private static int[] getMostDangerousEdge(Collection<Integer> gates, HashMap<Integer, LinkedList<Integer>> graph, Integer [] dist) {
		
		// validate zero distance node is incident to gate
		for(int i=0; i < dist.length; i++){
			if(dist[i] != null && dist[i]== 0){
				for(int n: graph.get(i)){
					if(gates.contains(n)){
						return new int[]{i, n};
					}
				}
			}
		}
		
		int [] edge = new int[2];
		
		// calculate gate number
		Integer[] gateN = new Integer[dist.length];
		for(int n : graph.keySet())
			gateN[n] = 0;
		
		for(int g: gates)
			for(int n: graph.get(g))
				gateN[n]++;
		
		System.err.println("Gate N:"+Arrays.toString(gateN));
		
		// filter the ones with the most incident gates
		int maxg = Arrays.stream(gateN)
				.mapToInt(gn -> gn)
				.max().getAsInt();
		
		List<Integer> list = graph.keySet().stream()
				.filter(n -> gateN[n] == maxg)
				.collect(Collectors.toList());
		
		// get mindistance
		int mind = list.stream().
				map(n -> dist[n]).
				filter(d -> d!= null).
				mapToInt(d -> d).min().getAsInt();
		System.err.println("Dist: "+Arrays.toString(dist));
		System.err.println("Mind: "+mind);
		
		// filter only by mind
		list = list.stream().filter(x -> dist[x] == mind).collect(Collectors.toList());
		System.err.println("Filtered: "+list.stream().map( m-> m.toString()).collect(Collectors.joining(",")));
		
		edge[0] = list.get(0);
		System.err.println("Less danger: "+edge[0]);
		edge[1] = graph.get(edge[0]).stream()
				.filter(n -> gates.contains(n))
				.findFirst().get();
		return edge;
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