import java.util.Arrays;
import java.util.LinkedList;

public class OverlappingRectangles {
	
	/**
	 * Wrong solution , we can think that we have more
	 * overlapping than we already to
	 * @param recs
	 * @return
	 */
	int maxOverlapping2(Rectangle[] recs){
		
		if(recs.length == 0)
			return 0;
		// sort them by x coordinate, then by y coordinate
		Arrays.sort(recs, (r1, r2) -> r1.x != r2.x? r1.x - r2.x: r1.y - r2.y);
		
		// traverse them and count overlappings
		int n = recs.length;
		for (int i = 0; i < n; i++) {
			Rectangle r1 = recs[i];
			for (int j = i + 1; j < n; j++) {
				// rec[i] and rec[j] overlapp
				Rectangle r2 = recs[j];
				// x overlap
				if(r1.x <= r2.x && r2.x < r1.x + r1.w){
					// y overlap
					if( (r1.y <= r2.y && r2.y < r1.y + r1.h) || // top edge
						(r1.y < r2.y && r2.y <= r2.y + r2.h))
					{
						r1.over ++;
						r2.over ++;
					}
				}
				// out of reach
				else
					break;
			}
		}
		
		// get the max
		return Arrays.stream(recs)
				.mapToInt(r -> r.over)
				.max()
				.getAsInt() + 1;
	}
	
	// also O(n2) just for double checking answers
	int maxOverlapping(Rectangle[] recs){
		// create a list of all points
		LinkedList<Point> points = new LinkedList<>();
		for(Rectangle r: recs){
			points.add(new Point(r.x, r.y, true, true));
			points.add(new Point(r.x + r.w, r.y, false, true));
			points.add(new Point(r.x, r.y + r.h, true, false));
			points.add(new Point(r.x + r.w, r.y + r.h, false, false));
		}
		
		for(Point p: points){
			for(Rectangle r: recs){
				if(p.left && r.x <= p.x && p.x < r.x + r.w){
					if(p.top && r.y <= p.y && p.y < r.y + r.h){
						p.recs ++;
					}else if(p.top && r.y < p.y && p.y <= r.y + r.h){
						p.recs ++;
					}
				}
				else if(!p.left && r.x < p.x && p.x <= r.x + r.w){
					if(p.top && r.y <= p.y && p.y < r.y + r.h){
						p.recs ++;
					}else if(p.top && r.y < p.y && p.y <= r.y + r.h){
						p.recs ++;
					}
				}
			}
		}
		
		return points.stream().mapToInt(p -> p.recs).max().orElse(0);
	}
	
	class Point{
		int x, y;
		boolean top, left;
		int recs = 0;
		public Point(int x, int y, boolean top, boolean left) {
			this.x = x;
			this.y = y;
			this.top = top;
			this.left = left;
		}
	}
	
	
}

class Rectangle{
	int x, y;
	int w, h;
	int over = 0;
	
	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
}
