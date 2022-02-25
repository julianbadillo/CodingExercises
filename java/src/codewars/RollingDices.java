
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

class SolutionRD {

	public static void main(String args[]) {
		RollingDices.main(args);
	}
}

public class RollingDices {
	
	
	
	// Initial state
	Die[][] board = new Die[BoardState.SIZE][BoardState.SIZE];
	int n;
	int x, y; // Rossi's position at start
	

	public static void main(String[] args) {
		
		RollingDices rd = new RollingDices();
		Scanner in = new Scanner(System.in);
		rd.n = in.nextInt();
		for (int i = 0; i < rd.n; i++) {
			int x = in.nextInt(), y = in.nextInt();
			String face = in.next();
			rd.board[x][y] = new Die(i, Face.valueOf(face));
			if (i == 0) {
				rd.x = x;
				rd.y = y;
			}
		}

		rd.solve();
	}

	void solve() {

		// Create start Board State
		BoardState start = new BoardState(this.board, x, y);

		HashSet<BoardState> visited = new HashSet<>();

		// DFS until reach a solved state
		LinkedList<BoardState> queue = new LinkedList<>();
		queue.add(start);
		BoardState bs = null;
		while (!queue.isEmpty()) {
			bs = queue.poll();
			// break when reached a solved state
			if (bs.isSolved())
				break;
			if (visited.contains(bs))
				continue;
			visited.add(bs);

			for (BoardState bs2 : bs.moves()) {
				if (visited.contains(bs2))
					continue;
				queue.add(bs2);
			}
		}

		if (bs == null)
			throw new RuntimeException("Null BS");
		if (!bs.isSolved())
			throw new RuntimeException("Not Solved");
		
		LinkedList<Move> moves = new LinkedList<>();
		while(bs.prev != null){	
			moves.addFirst(new Move(bs.board[bs.x][bs.y].id, bs.move));
			bs = bs.prev;
		}
		
		for(Move m: moves)
			System.out.println(m.id + " " + m.d);
	}

}

/***
 * After a move
 */
class BoardState {
	public static final int SIZE = 4;
	Die[][] board;
	int x, y;
	BoardState prev = null;
	Dir move = null;

	BoardState(Die[][] board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
	}

	BoardState(BoardState prev, int x, int y, Dir d) {
		this.prev = prev;
		this.move = d;
		this.x = x;
		this.y = y;
		board = new Die[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if(prev.board[i][j] != null)
					board[i][j] = prev.board[i][j].clone();
		Die die = board[this.x][this.y];
		// clear space
		board[this.x][this.y] = null;
		// move die and roll face
		this.x += d.dx;
		this.y += d.dy;
		board[this.x][this.y] = die;
		
		die.f = die.f.roll(d);
	}

	boolean isSolved() {
		// all reachable
		boolean visited[][] = new boolean[4][4];
		LinkedList<Sq> queue = new LinkedList<>();
		// DFS from current die
		queue.add(new Sq(x, y));
		while (!queue.isEmpty()) {
			Sq sq = queue.poll();
			if (visited[sq.x][sq.y])
				continue;
			visited[sq.x][sq.y] = true;
			
			if (board[sq.x][sq.y] == null)
				continue;

			// neighbours
			for (Dir d : Dir.values()) {
				if (sq.x + d.dx < 0 || 4 <= sq.x + d.dx || sq.y + d.dy < 0 || 4 <= sq.y + d.dy)
					continue;
				queue.add(new Sq(sq.x + d.dx, sq.y + d.dy));
			}
		}
		// if at least one not connected or not top
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++){
				if (board[i][j] != null && !visited[i][j])
					return false;
				if(board[i][j] != null && board[i][j].f != Face.IRON && board[i][j].f != Face.TOP )
					return false;
			}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		// same states have same value
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (board[i][j] != null)
					hash += (i + 10 * j) * board[i][j].f.hashCode();
		return hash;
	}

	@Override
	/***
	 * Boards with same faces in same positions are considered equal
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardState))
			return false;
		BoardState bs = (BoardState) obj;
		// same faces on same position
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == null && bs.board[i][j] == null)
					continue;
				if (board[i][j] == null)
					return false;
				if (bs.board[i][j] == null)
					return false;
				if (board[i][j].f != bs.board[i][j].f)
					return false;
			}
		}
		return true;
	}

	/***
	 * All allowed moves from this state
	 * 
	 * @return
	 */
	Collection<BoardState> moves() {
		// verify grouped dices
		HashSet<Die> grouped = getDieGroup();
		HashSet<BoardState> states = new HashSet<>();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null)
					continue;
				// cannot roll Iron
				if (board[i][j].f == Face.IRON)
					continue;
				if (!grouped.contains(board[i][j]))
					continue;

				// explore moves
				for (Dir d : Dir.values()) {
					// out of borders
					if (i + d.dx < 0 || 4 <= i + d.dx || j + d.dy < 0 || 4 <= j + d.dy)
						continue;
					// occupied slot
					if (board[i + d.dx][j + d.dy] != null)
						continue;

					states.add(new BoardState(this, i, j, d));
				}
			}
		}
		return states;
	}

	HashSet<Die> getDieGroup() {
		HashSet<Die> grouped = new HashSet<>();
		boolean visited[][] = new boolean[4][4];
		LinkedList<Sq> queue = new LinkedList<>();
		// DFS from current die
		queue.add(new Sq(x, y));
		while (!queue.isEmpty()) {
			Sq sq = queue.poll();
			if (visited[sq.x][sq.y])
				continue;
			visited[sq.x][sq.y] = true;

			if (board[sq.x][sq.y] == null)
				continue;
			// add die to group
			grouped.add(board[sq.x][sq.y]);

			// neighbours
			for (Dir d : Dir.values()) {
				if (sq.x + d.dx < 0 || 4 <= sq.x + d.dx || sq.y + d.dy < 0 || 4 <= sq.y + d.dy)
					continue;
				queue.add(new Sq(sq.x + d.dx, sq.y + d.dy));
			}
		}
		return grouped;
	}
}

class Sq {
	int x, y;
	Sq(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Die {
	int id;
	Face f;

	Die(int id, Face f) {
		this.id = id;
		this.f = f;
	}

	protected Die clone() {
		return new Die(id, f);
	}
}

class Move{
	Dir d;
	int id;
	public Move(int id, Dir d) {
		this.d = d;
		this.id = id;
	}
}

enum Dir {
	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);
	int dx, dy;

	Dir(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
}

enum Face {
	TOP {
		Face roll(Dir d) {
			switch (d) {
			case UP: return NORTH;
			case DOWN: return SOUTH;
			case LEFT: return WEST;
			case RIGHT: return EAST;
			default: return this;
			}
		}
	},
	BOTTOM {
		Face roll(Dir d) {
			switch (d) {
			case UP: return SOUTH;
			case DOWN: return NORTH;
			case LEFT: return EAST;
			case RIGHT: return WEST;
			default: return this;
			}
		}
	},
	NORTH {
		Face roll(Dir d) {
			switch (d) {
			case UP:
				return BOTTOM;
			case DOWN:
				return TOP;
			default:
				return this;
			}
		}
	},
	SOUTH {
		Face roll(Dir d) {
			switch (d) {
			case UP:
				return TOP;
			case DOWN:
				return BOTTOM;
			default:
				return this;
			}
		}
	},
	EAST {
		Face roll(Dir d) {
			switch (d) {
			case LEFT:
				return TOP;
			case RIGHT:
				return BOTTOM;
			default:
				return this;
			}
		}
	},
	WEST {
		Face roll(Dir d) {
			switch (d) {
			case LEFT:
				return BOTTOM;
			case RIGHT:
				return TOP;
			default:
				return this;
			}
		}
	},
	IRON {
		Face roll(Dir d) {
			return this;
		}
	};
	abstract Face roll(Dir d);
}