import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Scanner;

public class Buildings {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();

		LinkedList<Rectangle2D> list = new LinkedList<>();
		for (int i = 0; i < n; i++){
			int h = in.nextInt(), x1 = in.nextInt(), x2 = in.nextInt();
			list.add(new Rectangle2D.Double(x1, 0, x2 - x1, h));
		}
		
		Area a = new Area();
		for(Rectangle2D r : list)
			a.add(new Area(r));
		
		int sides = 0;
		
		PathIterator ite = a.getPathIterator(null);
		double d[] = new double[2];
		double t[] = new double[2];
		ite.currentSegment(d);
		while(!ite.isDone()){
			ite.currentSegment(t);
			if(d[0]!= t[0] || d[1]!= t[1])
			{
				d[0] = t[0];
				d[1] = t[1];
				sides++;	
			}
			ite.next();
		}
		// minus the bottom side
		System.out.println(sides);
	}
}
