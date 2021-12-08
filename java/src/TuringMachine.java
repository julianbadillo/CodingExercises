import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TuringMachine {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int S = in.nextInt();
        int T = in.nextInt();
        int x = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        String st = in.nextLine();
        
        int N = in.nextInt();
        if (in.hasNextLine())
            in.nextLine();
        
        
        int [] tape = new int[T];
        HashMap<String, Action[]>actions = new HashMap<>();
        
        for (int i = 0; i < N; i++) {
            String[] parts = in.nextLine().split(":");
            Action[] acts = new Action[S];
            actions.put(parts[0], acts);
            parts = parts[1].split(";");
            for (int j = 0; j < S; j++)
            	acts[j] = new Action(parts[j]);
        }
        
        int count = 0;
        while(!st.equals("HALT") && 0 <= x && x < T){
        	int s = tape[x];
        	Action a = actions.get(st)[s];
        	tape[x] = a.w;
        	x += a.m;
        	st = a.next;
        	count++;
        }
        System.out.println(count);
        System.out.println(x);
        Arrays.stream(tape)
        		.forEach(System.out::print);
        System.out.println();
        
	}
	
	static class Action{
		int w;
		int m;
		String next;
		public Action(String s) {
			String[] parts = s.split(" ");
			w = Integer.parseInt(parts[0]);
			m = parts[1].equals("L")? -1: 1;
			next = parts[2];
		}
	}

}
