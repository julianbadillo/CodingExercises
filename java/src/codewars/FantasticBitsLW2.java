import java.util.ArrayList;
import java.util.Scanner;

class PlayerFB {

    public static void main(String args[]) {
        FantasticBitsLW2.main(args);
    }
}

public class FantasticBitsLW2 {

	int nPlayers = 2;
	int myId;
	Goal myGoal;
	Goal opGoal;
	ArrayList<QPlayer> myPl;
	ArrayList<QPlayer> opPl;
	ArrayList<Snaffle> snfs;
	ArrayList<Bludger> blgs;
	static final int WITH_SNAFFLE = 1;
	
	public static void main(String[] args) {
		
		FantasticBitsLW2 f = new FantasticBitsLW2();
		f.gameLoop();
	}
	
	public FantasticBitsLW2() {
		myPl = new ArrayList<>();
		opPl = new ArrayList<>();
		snfs = new ArrayList<>();
		blgs = new ArrayList<>();
	}
	
	void gameLoop(){
		Scanner in = new Scanner(System.in);
        myId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left

        // decide orientation
        decideOrientation();
        
        // game loop
        while (true) {
            int entities = in.nextInt(); // number of entities still in game
            for (int i = 0; i < entities; i++)                
                updateEntity(in.nextInt(), in.next(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
            clearSnaffles();
            decideMove();
            System.err.println(">>"+myPl.size());
            // print moves of my players
            for (QPlayer pl : myPl) 
            	System.out.println(pl.move());
        }

	}
	
	void decideOrientation(){
		//The coordinates of the center of each goal are (X=0, Y=3750) for team 0 and (X=16000, Y=3750) for team 1.
		if(myId == 0){
			myGoal = new Goal(0, 3750);
			opGoal = new Goal(16000, 3750);
		} else {
			myGoal = new Goal(16000, 3750);
			opGoal = new Goal(0, 3750);
		}
	}
	
	// "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
	void updateEntity(int id, String entType, int x, int y, int vx, int vy, int state) {
		if(entType.equals("WIZARD")){
			// get or create
			QPlayer pl = myPl.stream()
					.filter(pl2 -> pl2.id == id)
					.findAny()
					.orElse(null);
			if(pl == null)
				myPl.add(new QPlayer(id, x, y, vx, vy, state));
			else
				pl.update(x, y, vx, vy, state);
		}
		else if(entType.equals("OPPONENT_WIZARD")){
			// get or create
			QPlayer pl = opPl.stream()
					.filter(pl2 -> pl2.id == id)
					.findAny()
					.orElse(null);
			if(pl == null)
				opPl.add(new QPlayer(id, x, y, vx, vy, state));
			else
				pl.update(x, y, vx, vy, state);
		}
		else if(entType.equals("SNAFFLE")){
			// get or create
			Snaffle sn = snfs.stream()
					.filter(s -> s.id == id)
					.findAny()
					.orElse(null);
			if(sn == null)
				snfs.add(new Snaffle(id, x, y, vx, vy));
			else
				sn.update(x, y, vx, vy);
		}
		else if(entType.equals("BLUDGER")){
			// get or create
			Bludger bl = blgs.stream()
					.filter(b -> b.id == id)
					.findAny()
					.orElse(null);
			if(bl == null)
				blgs.add(new Bludger(id, x, y, vx, vy));
			else
				bl.update(x, y, vx, vy);
		}
		
	}
	
	void clearSnaffles(){
		snfs.removeIf(sn -> !sn.ok);
		// mark as uncheckeds
		snfs.stream()
			.forEach(sn -> sn.ok = false);
	}
	
	static final int SAFE_BLG_DIST = 4000; 
	
	void decideMove(){
		
		for(QPlayer pl: myPl){
			
			if(pl.state == WITH_SNAFFLE){
				// point to goal
				pl.tx = opGoal.x;
				pl.ty = opGoal.y;
			}
			else{
				// closest snaffle and closest bludger
				Snaffle minSn = snfs.stream()
						// TODO pick different that already picked by the other player
						.min((sn1, sn2) -> (int)pl.distance(sn1) - (int)pl.distance(sn2))
						.orElse(null);
				
				Bludger minBl = blgs.stream()
						.min((bl1, bl2) -> (int)pl.distance(bl1) - (int)pl.distance(bl2))
						.orElse(null);
				
				System.err.println("Closest Snaffle: " + minSn);
				System.err.println("Closest Bludger: " + minBl);
				
				if(minBl != null && pl.distance(minBl) < SAFE_BLG_DIST)
				{
					// calculate an avoid vector
					// middle point
					int mpx = (pl.x + minBl.x)/2, mpy = (pl.y + minBl.y) / 2;
					int dx = (minBl.x - pl.x), dy = (minBl.y - pl.y);
					double a1 = Math.atan2(dy, dx) + Math.PI / 2; // a = ArcTan(dy/dx) + pi/2
					// destination point = mp + dev
					int tx = mpx + (int)(SAFE_BLG_DIST*Math.cos(a1)), ty = mpy + (int)(SAFE_BLG_DIST*Math.sin(a1));
					System.err.println("Avoid bludger: "+ tx +", "+ty);
					pl.tx = tx;
					pl.ty = ty;
				}
				else if(minSn != null){
					// point to snaffle
					pl.tx = minSn.x;
					pl.ty = minSn.y;
				}
				else{
					// go back to goal
					pl.tx = myGoal.x;
					pl.ty = myGoal.y;
				}
			}
		}
	}
	
	
}

class _2DThing{
	int x, y, vx, vy;
	void update(int x, int y, int vx, int vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}
	
	double distance(_2DThing th){
		return Math.sqrt((x - th.x) * (x - th.x) + (y - th.y) * (y - th.y));
	}
}

class QPlayer extends _2DThing {
	int id;
	int tx, ty; // target
	int state;
	int thrust = 100;
	int power = 500;
	QPlayer(int id, int x, int y, int vx, int vy, int state) {
		this.id = id;
		super.update(x, y, vx, vy);
		this.state = state;
	}
	
	void update(int x, int y, int vx, int vy, int state){
		super.update(x, y, vx, vy);
		this.state = state;
	}

	String move(){
		// Edit this line to indicate the action for each wizard (0 <= thrust <= 150, 0 <= power <= 500)
        // i.e.: "MOVE x y thrust" or "THROW x y power"	
		if(state == FantasticBitsLW2.WITH_SNAFFLE)
			return "THROW "+tx+" "+ty+" "+power;
		else
			return "MOVE "+tx+" "+ty+" "+thrust;
	}
}

class Snaffle extends _2DThing {
	int id;
	boolean ok;
	Snaffle(int id, int x, int y, int vx, int vy) {
		this.id = id;
		super.update(x, y, vx, vy);
		this.ok = true;
	}
	
	void update(int x, int y, int vx, int vy) {
		super.update(x, y, vx, vy);
		this.ok = true;
	}
	
	public String toString() {
		return id+":("+x+", "+y+")";
	}
}

class Bludger extends _2DThing{
	int id;
	Bludger(int id, int x, int y, int vx, int vy){
		this.id = id;
		super.update(x, y, vx, vy);
	}
	
	public String toString() {
		return id+":("+x+", "+y+")";
	}
}

class Goal extends _2DThing {

	public Goal(int x, int y) {
		super();
		super.update(x, y, 0, 0);
	}
	
}
