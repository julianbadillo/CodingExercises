import java.lang.*;
import java.util.*;
public class RectangularGrid{

	public long countRectangles(int width, int height){
		long total = 0;
		//all possible rec and square dimensions
		total = width*(width+1)*height*(height+1)/4;
		//discard all the square ones
		for(int i=1; i<=(width<height?width:height); i++)
			total -= i*i;
	
		return total;
	
	}
	
}
