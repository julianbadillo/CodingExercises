import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class SolutionHTR{
	public static void main(String[] args) {
		HitTheRoad.main(args);

	}
}


public class HitTheRoad {

	public static void main(String[] args) {
		HitTheRoad o = new HitTheRoad();
		o.init();
		o.solve();

	}
	
	
	int n, m, ntw;
	int s, t;
	
	Junction [] jcts;
	Road [] roads;
	
	void init(){
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		m = in.nextInt();
		ntw = in.nextInt();
		s = in.nextInt();
		t = in.nextInt();
		
		
		jcts = new Junction[n];
		for (int i = 0; i < n; i++) 
			jcts[i] = new Junction();
		
		for (int i = 0; i < ntw; i++) {
			int v = in.nextInt();
			jcts[v].b = in.nextInt();
			jcts[v].e = in.nextInt();
		}
		// roads
		roads = new Road[m];
		for (int i = 0; i < m; i++) {
			roads[i] = new Road();
			roads[i].from = in.nextInt();
			roads[i].to = in.nextInt();
			roads[i].d = in.nextInt();
			jcts[roads[i].from].adjRds.add(i);
		}
		

	}
	
	void solve(){
		
		// BFS
		boolean visited[][] = new boolean[n][60];
		LinkedList<JunctionTime> queue = new LinkedList<>();
		
		queue.add(new JunctionTime(s, 0));
		while(!queue.isEmpty()){
			JunctionTime jt = queue.pollFirst();
			if(visited[jt.j][jt.t])
				continue;
			visited[jt.j][jt.t] = true;
			
			for(int r : jcts[jt.j].adjRds){
				Road rd = roads[r];
				Junction to = jcts[rd.to];
				int time = jt.t + rd.d;
				if(to.b <= time && time <= to.e
						&& time < 60)
				{
					queue.add(new JunctionTime(rd.to, time));
				}
			}
		}
		
		boolean reached = IntStream.range(0, 60).anyMatch(i -> visited[t][i]);
		System.out.println(reached?"true":"false");
		
	}

}

class Junction{	
	int b = 0;
	int e = Integer.MAX_VALUE;
	Collection<Integer> adjRds = new LinkedList<>();
	
	
}

class JunctionTime{
	int j;
	int t;
	JunctionTime(int j, int t){
		this.j = j;
		this.t = t;
	}
}

class Road{
	int from;
	int to;
	int d;
}