import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class PlayerH {

    public static void main(String args[]) {
        HyperSonic.main(args);
    }
}

public class HyperSonic {	
	char [][] map;
	int id;
	int x, y, w, h;
	// destiny sqare
	int ex, ey;
	
	Bomb bomb;
	int nBombs = 1;
	int bombRange = 3;
	
	DIRECTION dir;
	
	enum STATUS {
		GO_TO_BOMB, DROP_BOMB, WAITING_BOMB, WAIT
	}

	enum DIRECTION {
		UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), SAME(0, 0);
		final int dx, dy;
		private DIRECTION(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	}
	
	STATUS st;
	
	class Bomb {
		int x, y, timer, reach;
		Bomb(int x, int y, int r, int t){
			this.x = x; this.y = y; this.reach = r; this.timer = t;
		}
	}
	
	HyperSonic(int id, int w, int h){
		this.id = id;
		this.w = w;
		this.h = h;
		map = new char[h][];
	}
	
	void decideNextMove(){
		// go to the closest best place
		calculateNextBestPlace();	
		// if we reached the destiny
		if(x == ex && y == ey && bomb == null){
			st = STATUS.DROP_BOMB;
		}
		else{
			st = STATUS.GO_TO_BOMB;
		}
			
	}
	
	class Sq{
		int x, y;
		boolean v = false;
		DIRECTION from;
		Sq prev;
		public Sq(int x, int y, Sq p, DIRECTION f) {
			this.x = x; this.y = y;
			this.from = f;
			this.prev = p;
		}
	}
	
	void calculateNextBestPlace(){
		// all the map -- mark the incidence of each square
		// TODO change of strategy -> look for a safe place, drop a bomb
		// TODO run away
		int [][] inc = new int[h][w];
		int [][] dist = new int[h][w];
		for (int y = 0; y < dist.length; y++) 
			Arrays.fill(dist[y], h*w);
		
		for (int x2 = 0; x2 < w; x2++) {
			for (int y2 = 0; y2 < h; y2++) {
				// only blank space
				if(map[y2][x2] != '.')
					continue;
				// all directions
				for(DIRECTION d : DIRECTION.values()){
					if(d == DIRECTION.SAME)
						break;
					// bomb power
					for (int i = 1; i < bombRange; i++){
						// borders
						if(x2+i*d.dx < 0 || w <= x2+i*d.dx)
							break;
						if(y2+i*d.dy < 0 || h <= y2+i*d.dy)
							break;
						// the target square
						if( '0' <= map[y2+i*d.dy][x2+i*d.dx] && map[y2+i*d.dy][x2+i*d.dx] <= '9'){
							inc[y2][x2]++;
							break;
						}
					}
				}
			}
		}
		// if current place is ok, and we havent place the bomb, or 
		// we placed somwehere else
		if(inc[y][x] >= 2)
		{
			if(bomb == null || bomb.x != x || bomb.y != y){
				System.err.println("Best Place: HERE "+x+","+y);
				ex = x;
				ey = y;
				dir = DIRECTION.SAME;
				return;
			}
		}
		
		int maxInc = Arrays.stream(inc).map(x -> Arrays.stream(x)).mapToInt(x -> x.max().getAsInt()).max().getAsInt();
		System.err.println("max "+maxInc);
		// dfs hit any square with 2 or more
		LinkedList<Sq> stack = new LinkedList<>();
		stack.push(new Sq(x, y, null, DIRECTION.SAME));
		Sq end = new Sq(x, y, null, DIRECTION.SAME);
		boolean[][] v = new boolean[h][w];
		while(!stack.isEmpty()){
			Sq sq = stack.peek();
			if(v[sq.y][sq.x]){
				stack.pop();
				continue;
			}
			v[sq.y][sq.x] = true;
			
			if(maxInc >= 2){
				if(inc[sq.y][sq.x] >= 2){
					// not start
					if(sq.x != x || sq.y != y){
					    System.err.println("HIT ME "+sq.x + " "+sq.y+ " = "+inc[sq.y][sq.x]);
						// set destination
						end = sq;
						break;
					}
				}
			} else {
			    System.err.println("LOW HIT ME "+sq.x + " "+sq.y+ " = "+inc[sq.y][sq.x]);
				if(inc[sq.y][sq.x] > 0){
					end = sq;
					break;
				}
			}
			
			// surroundings
			for(DIRECTION d: DIRECTION.values()){
				if(d == DIRECTION.SAME)
					continue;
				// out of bounds
				if(sq.x+d.dx < 0 || w <= sq.x + d.dx)
					continue;
				if(sq.y+d.dy < 0 || h <= sq.y + d.dy)
					continue;
				// visited
				if(v[sq.y+d.dy][sq.x+d.dx])
					continue;
				// not a space
				if(map[sq.y+d.dy][sq.x+d.dx] != '.')
					continue;
				 //nor the place of the bomb
				 if(bomb != null && bomb.x == sq.x+d.dx && bomb.y == sq.y+d.dy)
				    continue;
				stack.add(new Sq(sq.x+d.dx, sq.y+d.dy, sq, d));
			}
			// TODO set path
			// TODO not recalculate path unless map in the path has changed
		}
		
		System.err.println("Best Place: END "+end.x+","+end.y);
		
		// reverse
		Sq node = end;
		while(node.prev != null && 
		    // NOT START
		    (node.prev.x != x || node.prev.y != y))
			node = node.prev;
		// update status
		this.ex = end.x;
		this.ey = end.y;
		this.dir = end.from;
	}
	
	String action(){
		String action = "MOVE";
	
		if(st== STATUS.DROP_BOMB)
			action = "BOMB";
		// direction
		//action += " " + (x + dir.dx) + " " + (y + dir.dy);
		action += " " + ex + " " + ey;
		return action;
	}
	
	void printMap(){
		for (int y = 0; y < h; y++) 
			System.err.println(new String(map[y]));
	}
	
	static final int PLAYER = 0;
	static final int BOMB = 1;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int myId = in.nextInt();

        HyperSonic bomber = new HyperSonic(myId, width, height);    
        // game loop
        while (true) {
        	// map update
            for (int y = 0; y < height; y++)
            	bomber.map[y] = in.next().toCharArray();
            
            // bomber update
            int entities = in.nextInt();
            bomber.bomb = null;
            for (int i = 0; i < entities; i++) {
                int e = in.nextInt(),
            		id = in.nextInt(),
            		x = in.nextInt(),
            		y = in.nextInt(),
            		param1 = in.nextInt(),
            		param2 = in.nextInt();
                if(e == PLAYER && id == bomber.id){
                	bomber.x = x;
                	bomber.y = y;
                	bomber.nBombs = param1;
                	bomber.bombRange = param2;
                }
                // update the bomb
                else if(e == BOMB && id == bomber.id){                	
                	bomber.bomb = bomber.new Bomb(x, y, param2, param1);
                }
                else if(e == BOMB && id != bomber.id){
                	bomber.map[y][x] = 'B';
                }
            }
            //bomber.printMap();
            bomber.decideNextMove();
            System.out.println(bomber.action());
        }
	}
}