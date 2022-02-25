
public class Coversta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Coversta c = new Coversta();
		c.place(new String[]{"11","11"},
				new int[]{0,0},
				new int[]{0,1});

		c.place(new String[]{"11",
		"11"},
				new int[]{0,1},
				new int[]{0,1});

		c.place(new String[]
				{"151",
				"655",
				"661"},
				new int[]{0,0,-1},
				new int[]{0,1,0});

	}

	public int place(String[] a, int[] x, int[] y){
		int n = a.length, m = a[0].length();
		boolean [][] marked = new boolean[n][m];

		////ideas
		//try all positions of first tower, pick the largest
		int maxv=0, maxx=0, maxy=0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				int v=0;
				//add the value of all areas reached by the antena
				for (int k = 0; k < x.length; k++) {
					//if inside boundaries
					if(0 <= r+x[k] && r+x[k] < n &&
							0 <= c+y[k] && c+y[k] < m)
						v += (a[r+x[k]].charAt(c+y[k])-'0');
				}
				if(v > maxv){
					maxv = v;
					maxx = r;
					maxy = c;
				}
			}
		}

		//System.out.println("Best tower at "+maxx+","+maxy+"="+maxv);
		//mark covered
		//add the value of all areas reached by the antena
		for (int k = 0; k < x.length; k++) {
			//if inside boundaries
			if(0 <= maxx+x[k] && maxx+x[k] < n &&
					0 <= maxy+y[k] && maxy+y[k] < m)
				marked[maxx+x[k]][maxy+y[k]] = true;
		}


		//try all positions of second tower, pick the largest
		//mark covered
		int maxx2=0, maxy2=0;
		int maxv2=0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				int v=0;
				//add the value of all areas reached by the antena
				for (int k = 0; k < x.length; k++) {
					//if inside boundaries
					if(0 <= r+x[k] && r+x[k] < n &&
							0 <= c+y[k] && c+y[k] < m &&
							!marked[r+x[k]][c+y[k]] ) //check not marked before
						v += (a[r+x[k]].charAt(c+y[k])-'0');
				}
				if(v > maxv2){
					maxv2 = v;
					maxx2 = r;
					maxy2 = c;
				}
			}
		}
		//System.out.println("Best tower at "+maxx2+","+maxy2+"="+maxv2);
		//add
		//System.out.println(maxv+maxv2);
		return maxv+maxv2;
	}

}
