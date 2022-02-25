import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class PlayerHSSL {

    public static void main(String args[]) {
        HyperSonicSL.main(args);
    }
}

public class HyperSonicSL {
	char[][] map;
	int[][] inc;
	int[][] dist;
	int id;
	int x, y, w, h;
	// destiny sqare
	int ex, ey;
	int reach;

	ArrayList<Bomb> bombs = new ArrayList<>();
	int nBombs = 1;
	int myBombs = 0;
	int bombRange = 3;

	DIRECTION dir;

	enum DIRECTION {
		UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);
		final int dx, dy;

		private DIRECTION(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	}

	ST st;
	boolean dropBomb = false;

	enum ST {
		BREAK_FREE, FLY_AWAY, GO_TO_BOMB, RUN_AND_BOMB, WAIT1, WAIT2, WAIT3
	}

	HyperSonicSL(int id, int w, int h) {
		this.id = id;
		this.w = w;
		this.h = h;
		this.st = ST.BREAK_FREE;
		map = new char[h][];
		inc = new int[h][w];
		dist = new int[h][w];
	}

	void decideNextMove() {
		calculateExplosions();
		bfsMap();
		if (st == ST.BREAK_FREE) {
			// look for the closest box
			findClosestBox();

			// if destination already reached drop the bomb
			if (ex == x && ey == y) {
				dropBomb = true;
				st = ST.FLY_AWAY;
			}
		}
		// cascading to fly away
		else if (st == ST.FLY_AWAY) {
			findClosestSafe();
			System.err.println("2. fly to: " + ex + " " + ey);
			// if my bombs are gone
			if (ex == x && ey == y && myBombs == 0) {
				st = ST.GO_TO_BOMB;
			}

		}
		// cascading to go to bomb
		else if (st == ST.GO_TO_BOMB) {
			findClosestSafeBox();
			System.err.println("3. go to: " + ex + " " + ey);
			if (ex == x && ey == y && myBombs < nBombs) {
				dropBomb = true;
				//st = ST.RUN_AND_BOMB;
			}
			// if very few boxes, just go aggressive
		}
		else if (st == ST.RUN_AND_BOMB) {
			
		}

		// TODO calculate where to drop de bomb

	}
	
	
	private void bfsMap(){
		
		Square start = new Square(x, y);
		Arrays.stream(dist).forEach(r -> Arrays.fill(r, h * w));// max
																// distance
		dist[y][x] = 0;
		boolean[][] visited = new boolean[h][w];
		LinkedList<Square> queue = new LinkedList<>();
		queue.add(start);
		// BFS reachable empty spaces looking for a safe square
		int reach = 0;
		while (!queue.isEmpty()) {
			Square sq = queue.poll();
			if (visited[sq.y][sq.x])
				continue;
			visited[sq.y][sq.x] = true;
			reach++;
			int ds = dist[sq.y][sq.x];

			for (DIRECTION dir : DIRECTION.values()) {
				// inside borders
				if (sq.x + dir.dx < 0 || w <= sq.x + dir.dx
					|| sq.y + dir.dy < 0 || h <= sq.y + dir.dy)
					continue;
				// not visited
				if (visited[sq.y + dir.dy][sq.x + dir.dx])
					continue;

				// can pass only through empty space
				if (map[sq.y + dir.dy][sq.x + dir.dx] != '.')
					continue;
				// no need for better path validation - simple DFS
				dist[sq.y + dir.dy][sq.x + dir.dx] = ds + 1;
				queue.add(new Square(sq.x + dir.dx, sq.y + dir.dy));	
			}
		}
		this.reach = reach;
	}

	private void findClosestBox() {
		int minx = x, miny = y;
		int mind = w*h;
		for (int y1 = 0; y1 < h; y1++) {
			for (int x1 = 0; x1 < w; x1++) {
				//is it near a box?
				if(hasOneBoxAround(x1, y1) && dist[y1][x1] < mind){
					minx = x1;
					miny = y1;
					mind = dist[y1][x1];
				}
			}
		}
		ex = minx;
		ey = miny;
	}
	
	boolean hasOneBoxAround(int x, int y){
		for(DIRECTION dir: DIRECTION.values()){
			// inside borders
			if (x + dir.dx < 0 || w <= x + dir.dx
				|| y + dir.dy < 0 || h <= y + dir.dy)
				continue;
			// if its a box, go for it
			if ('0' <= map[y + dir.dy][x + dir.dx] && map[y + dir.dy][x + dir.dx] <= '9')
				return true;
		}
		return false;
	}
	
	private void findClosestSafe() {
		int minx = x, miny = y;
		int mind = w*h;
		for (int y1 = 0; y1 < h; y1++) {
			for (int x1 = 0; x1 < w; x1++) {
				//is it safe?
				if(inc[y1][x1] == 0 && dist[y1][x1] < mind){
					minx = x1;
					miny = y1;
					mind = dist[y1][x1];
				}
			}
		}
		ex = minx;
		ey = miny;
	}
	
	private void findClosestSafeBox() {
		int minx = x, miny = y;
		int mind = w*h;
		for (int y1 = 0; y1 < h; y1++) {
			for (int x1 = 0; x1 < w; x1++) {
				//is it safe and has a box
				if(inc[y1][x1] == 0 && hasOneBoxAround(x1, y1) 
						&& dist[y1][x1] < mind){
					minx = x1;
					miny = y1;
					mind = dist[y1][x1];
				}
			}
		}
		ex = minx;
		ey = miny;
	}
	
	void calculateExplosions() {
		// zeros
		Arrays.stream(inc).forEach(r -> Arrays.fill(r, 0));
		for (Bomb b : bombs) {
			// calculate explosion
			for (DIRECTION d : DIRECTION.values()) {
				for (int i = 0; i < b.reach && 0 <= b.x + i * d.dx && b.x + i * d.dx < w && 0 <= b.y + i * d.dy
						&& b.y + i * d.dy < h && map[b.y + i * d.dy][b.x + i * d.dx] == '.'; i++)
					// score based on inverse timer
					inc[b.y + i * d.dy][b.x + i * d.dx] += (9 - b.timer);
			}
		}
		
	}

	String action() {
		String action = "MOVE";
		if (dropBomb) {
			action = "BOMB";
			dropBomb = false;
		}
		action += " " + ex + " " + ey;
		return action;
	}

	void printMap() {
		for (int y = 0; y < h; y++)
			System.err.println(new String(map[y]));
	}
	
	void printIncidence() {
		Arrays.stream(inc).forEach(r -> System.err.println(Arrays.toString(r)));
	}

	void updateStatus(Scanner in){
		// map update
		for (int y = 0; y < h; y++)
			map[y] = in.next().toCharArray();

		// bomber update
		int entities = in.nextInt();
		myBombs = 0;
		bombs.clear();
		for (int i = 0; i < entities; i++) {
			int e = in.nextInt(), eid = in.nextInt(), x = in.nextInt(), y = in.nextInt(), param1 = in.nextInt(),
					param2 = in.nextInt();
			if (e == PLAYER && eid == this.id) {
				this.x = x;
				this.y = y;
				this.nBombs = param1;
				this.bombRange = param2;
			}
			// update the bomb
			else if (e == BOMB) {
				bombs.add(new Bomb(eid, x, y, param1, param2));
				if(eid == this.id)
					myBombs ++;
			}
		}
	}
	
	static final int PLAYER = 0;
	static final int BOMB = 1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		int myId = in.nextInt();

		HyperSonicSL bomber = new HyperSonicSL(myId, width, height);
		// game loop
		while (true) {
			bomber.updateStatus(in);
			bomber.decideNextMove();
			System.out.println(bomber.action());
		}
	}
	
	class Square {
		int x, y;

		Square(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class Bomb {
		int id, x, y, timer, reach;

		Bomb(int id, int x, int y, int t, int r) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.reach = r;
			this.timer = t;
		}
	}
}


