
public class BinaryShiftedSearch {
	
	int findIndex(int[] arr, int x){
		return binarySearchShifted(arr, x, 0, arr.length);
	}
	
	int binarySearchShifted(int []arr, int x, int s, int e){
		// base case
		if(s >= e)
			return -1;
		int m = (s+e)/2;
		if(arr[m] == x) return m;
		
		//left case
		if(arr[s] <= x && x <= arr[m-1])
			return binarySearchShifted(arr, x, s, m);
		// right case
		if(arr[m] <= x && x <= arr[e-1])
			return binarySearchShifted(arr, x, m, e);
		
		//left broken case
		if(arr[s] > arr[m-1])
			return binarySearchShifted(arr, x, s, m);
		// right broken case
		if(arr[m] > arr[e-1])
			return binarySearchShifted(arr, x, m, e);
		// out of range case
		return -1;
	}

}
