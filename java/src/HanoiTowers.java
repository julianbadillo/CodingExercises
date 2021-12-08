import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

class SolutionHT {

    public static void main(String args[]) {
        HanoiTowers.main(args);
    }
}

public class HanoiTowers {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int T = in.nextInt();

        HanoiTowers ht = new HanoiTowers(N, T);
        
        ht.solve();
        
	}
	
	int N, T;
	int t;
	ArrayList<Integer> left = new ArrayList<>();
	ArrayList<Integer> center = new ArrayList<>();
	ArrayList<Integer> right = new ArrayList<>();
	
	public HanoiTowers(int N, int T) {
		this.N = N;
		this.T = T;
		t = 0;
		IntStream.iterate(N, i -> i-1)
			.limit(N)
			.forEach(i -> left.add(i));
	}
	
	void solve(){
		move(this.N, left, center, right);
		System.out.println(t);
	}
	
	void move(int n, List<Integer> from, List<Integer> middle, List<Integer> to){
		
		// Base case
		if(n == 1){
			to.add(from.remove(from.size()-1));
			if(++t == T) print();
		}
		else{
			// move n-1 disk to the middle
			move(n - 1, from, to, middle);
			
			// move larger disk to end
			to.add(from.remove(from.size() - 1));
			if(++t == T) print();
			
			// move n-1 disk from middle to end
			move(n - 1, middle, from, to);
		}
	}
	
	void print(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			int d = left.size() >= N - i ? left.get(N -1 - i): 0;
			line(sb, d, true);
			sb.append(' ');
			d = center.size() >= N - i ? center.get(N -1 - i): 0;
			line(sb, d, true);
			sb.append(' ');
			d = right.size() >= N - i ? right.get(N -1 - i): 0;
			line(sb, d, false);
			sb.append('\n');
		}
		System.out.print(sb.toString());
	}

	private void line(StringBuilder sb, int d, boolean trail) {
		IntStream.range(0, N - d)
			.forEach(i -> sb.append(' '));
		IntStream.range(0, d)
			.forEach(i -> sb.append('#'));
		sb.append(d == 0? '|' : '#');
		IntStream.range(0, d)
			.forEach(i -> sb.append('#'));
		if(trail)
			IntStream.range(0, N - d)
				.forEach(i -> sb.append(' '));
	}
	
}
