import java.lang.*;
import java.util.*;

/**
 * You are given ints n and m. Construct any tree with exactly n nodes (labeled 0 through n-1) and exactly m leaves. For the given constraints on n and m a solution will always exist.  The return value should be a int[] containing a sequence of 2*(n-1) integers. If your tree has edges (a[0], b[0]), (a[1], b[1]), etc., the correct return value is the sequence {a[0], b[0], a[1], b[1], ...}. If there are multiple correct return values, you may return any of them.
 * */

public class ExactTreeEasy{

	public int[] getTree(int n, int m){
	
		//number of nodes in each level of the tree
		int[] levels = new int[n/m + (n%m==0?0:1)];
		int [] edges = new int[2*(n-1)];
		//m nodes per level, from bottom to top
		int k =0;
		for(k=0; k< levels.length; k++)
			levels[k] = (k+1)*m <= n ? m: n % m;
		
		//now create the edges
		int i = 0, child, father;
		//for each level to the next upper one
		for(k=0; k+1<levels.length;k++){
			//link each one with his father
			int ite1 = 0;
			while( ite1 < levels[k+1]){
				edges[i] = ite1 + k*m;
				edges[i+1] = ite1 + (k+1)*m;
				ite1++;
				//add the edge
				i+=2;
			}
			//add the remaining on the lower level to the first of the upper level
			while(ite1 < levels[k]){
				//add the edge
				edges[i]  = ite1 + k*m;
				edges[i+1] = (k+1)*m;
				ite1++;
				i+=2;
			}
		}
		//add the remaining edges to link the nodes in the last level
		int ite = 0;
		while( ite+1 < levels[k] ){
			edges[i] = ite+k*m;
			edges[i+1] = ite+1+k*m;
			ite++;
			i+=2;
		}
		
		return edges;
		
	
	}



}