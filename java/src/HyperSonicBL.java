import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class PlayerHS {

    public static void main(String args[]) {
        HyperSonicBL.main(args);
    }
}

public class HyperSonicBL {
	char[][] map;
	int[][] inc;
	int id;
	int x, y, w, h;
	// destiny sqare
	int ex, ey;

	ArrayList<Bomb> bombs = new ArrayList<>();
	int nBombs = 1;
	int myBombs = 0;
	int bombRange = 3;

	DIRECTION dir;

	enum DIRECTION {
		UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0), SAME(0, 0);
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

	HyperSonicBL(int id, int w, int h) {
		this.id = id;
		this.w = w;
		this.h = h;
		this.st = ST.BREAK_FREE;
		map = new char[h][];
		inc = new int[h][w];
	}

	void decideNextMove() {
		calculateExplosions();
		if (st == ST.BREAK_FREE) {
			// look for the closest box
			findClosestBox();
			System.err.println("1. go to: " + ex + " " + ey);

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

	private void findClosestSafe() {
		// look for the closest safe square
		Square start = new Square(x, y);
		int[][] dist = new int[h][w];
		Arrays.stream(dist).forEach(r -> Arrays.fill(r, h * w));// max
																// distance
		dist[y][x] = 0;
		boolean[][] visited = new boolean[h][w];
		LinkedList<Square> queue = new LinkedList<>();
		queue.add(start);

		boolean carryon = true;
		// BFS reachable empty spaces looking for a safe square
		while (!queue.isEmpty() && carryon) {
			Square sq = queue.poll();
			if (visited[sq.y][sq.x])
				continue;
			visited[sq.y][sq.x] = true;
			int ds = dist[sq.y][sq.x];
			// safe found
			if (inc[sq.y][sq.x] == 0) {
				carryon = false;
				ex = sq.x;
				ey = sq.y;
			}

			for (DIRECTION dir : DIRECTION.values()) {
				if (dir == DIRECTION.SAME)
					continue;
				// inside borders
				if (sq.x + dir.dx < 0 || w <= sq.x + dir.dx)
					continue;
				if (sq.y + dir.dy < 0 || h <= sq.y + dir.dy)
					continue;
				// not visited
				if (visited[sq.y + dir.dy][sq.x + dir.dx])
					continue;

				// can pass only through empty space
				if (map[sq.y + dir.dy][sq.x + dir.dx] != '.')
					continue;
				// better path
				if (ds + 1 < dist[sq.y + dir.dy][sq.x + dir.dx]) {
					dist[sq.y + dir.dy][sq.x + dir.dx] = ds + 1;
					queue.add(new Square(sq.x + dir.dx, sq.y + dir.dy));
				}
			}
		}
	}

	private void findClosestBox() {
		Square start = new Square(x, y);
		int[][] dist = new int[h][w];
		Arrays.stream(dist).forEach(r -> Arrays.fill(r, h * w));// max
																// distance
		dist[y][x] = 0;
		boolean[][] visited = new boolean[h][w];
		LinkedList<Square> queue = new LinkedList<>();
		queue.add(start);

		boolean carryon = true;
		// BFS reachable empty spaces looking for a box
		while (!queue.isEmpty() && carryon) {
			Square sq = queue.poll();
			if (visited[sq.y][sq.x])
				continue;
			visited[sq.y][sq.x] = true;
			int ds = dist[sq.y][sq.x];
			for (DIRECTION dir : DIRECTION.values()) {
				if (dir == DIRECTION.SAME)
					continue;
				// inside borders
				if (sq.x + dir.dx < 0 || w <= sq.x + dir.dx)
					continue;
				if (sq.y + dir.dy < 0 || h <= sq.y + dir.dy)
					continue;
				// not visited
				if (visited[sq.y + dir.dy][sq.x + dir.dx])
					continue;
				// if its a box, go for it
				if ('0' <= map[sq.y + dir.dy][sq.x + dir.dx] && map[sq.y + dir.dy][sq.x + dir.dx] <= '9') {
					ex = sq.x;
					ey = sq.y;
					carryon = false;
				}

				// can pass only through empty space
				if (map[sq.y + dir.dy][sq.x + dir.dx] != '.')
					continue;
				// better path
				if (ds + 1 < dist[sq.y + dir.dy][sq.x + dir.dx]) {
					dist[sq.y + dir.dy][sq.x + dir.dx] = ds + 1;
					queue.add(new Square(sq.x + dir.dx, sq.y + dir.dy));
				}
			}
		}
	}

	
	private void findClosestSafeBox() {
		//printMap();
		Square start = new Square(x, y);
		int[][] dist = new int[h][w];
		Arrays.stream(dist).forEach(r -> Arrays.fill(r, h * w));// max
																// distance
		dist[y][x] = 0;
		boolean[][] visited = new boolean[h][w];
		LinkedList<Square> queue = new LinkedList<>();
		queue.add(start);

		boolean carryon = true;
		// BFS reachable empty spaces looking for a box
		Square safe = null;
		while (!queue.isEmpty() && carryon) {
			Square sq = queue.poll();
			System.err.println("findClSfBx "+sq.x+" "+sq.y);
			if (visited[sq.y][sq.x])
				continue;
			visited[sq.y][sq.x] = true;
			int ds = dist[sq.y][sq.x];
			// safe found
			if (inc[sq.y][sq.x] == 0 && safe == null) 
				safe = sq;
			
			for (DIRECTION dir : DIRECTION.values()) {
				if (dir == DIRECTION.SAME)
					continue;
				// inside borders
				if (sq.x + dir.dx < 0 || w <= sq.x + dir.dx)
					continue;
				if (sq.y + dir.dy < 0 || h <= sq.y + dir.dy)
					continue;
				// not visited
				if (visited[sq.y + dir.dy][sq.x + dir.dx])
					continue;
				
				// if its a box and next to safe place, go for it
				if (map[sq.y + dir.dy][sq.x + dir.dx] == '0' && inc[sq.y][sq.x] == 0) {
					ex = sq.x;
					ey = sq.y;
					carryon = false;
				}

				// can pass only through empty space
				if (map[sq.y + dir.dy][sq.x + dir.dx] != '.')
					continue;
				// better path
				if (ds + 1 < dist[sq.y + dir.dy][sq.x + dir.dx]) {
					dist[sq.y + dir.dy][sq.x + dir.dx] = ds + 1;
					queue.add(new Square(sq.x + dir.dx, sq.y + dir.dy));
				}
			}
		}
		// not a good box found -> go to safe square
		if(carryon){
			ex = safe.x;
			ey = safe.y;
		}
	}
	
	void calculateExplosions() {
		// zeros
		Arrays.stream(inc).forEach(r -> Arrays.fill(r, 0));
		for (Bomb b : bombs) {
			// calculate explosion
			for (DIRECTION d : DIRECTION.values()) {
				if (d == DIRECTION.SAME)
					continue;
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

	static final int PLAYER = 0;
	static final int BOMB = 1;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int width = in.nextInt();
		int height = in.nextInt();
		int myId = in.nextInt();

		HyperSonicBL bomber = new HyperSonicBL(myId, width, height);
		// game loop
		while (true) {
			// map update
			for (int y = 0; y < height; y++)
				bomber.map[y] = in.next().toCharArray();

			// bomber update
			int entities = in.nextInt();
			bomber.myBombs = 0;
			bomber.bombs.clear();
			for (int i = 0; i < entities; i++) {
				int e = in.nextInt(), id = in.nextInt(), x = in.nextInt(), y = in.nextInt(), param1 = in.nextInt(),
						param2 = in.nextInt();
				if (e == PLAYER && id == bomber.id) {
					bomber.x = x;
					bomber.y = y;
					bomber.nBombs = param1;
					bomber.bombRange = param2;
				}
				// update the bomb
				else if (e == BOMB) {
					bomber.bombs.add(new Bomb(id, x, y, param1, param2));
					if(id == bomber.id)
						bomber.myBombs ++;
				}
			}
			// bomber.printMap();
			bomber.decideNextMove();
			System.out.println(bomber.action());
		}
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
