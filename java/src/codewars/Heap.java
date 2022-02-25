import java.util.ArrayList;

public class Heap {

	private ArrayList<Integer> heap = new ArrayList<>();
	// always the minimum on front
	void push(int x){
		// add at the end
		heap.add(x);
		// rebalance
		int chil = heap.size()-1;
		//2n + 1 , 2n + 2
		int prev = (chil-1) / 2;
		while(chil > 0 && heap.get(prev) > heap.get(chil)){
			// swap
			int t = heap.get(prev);
			heap.set(prev, heap.get(chil));
			heap.set(chil, t);
			chil = prev;
			prev = (chil-1)/2;
		}

	}
	// the min
	int peekMin(){
		return heap.get(0);
	}
	
	// pops the min
	int pop(){
		if(heap.size() == 1)
			return heap.remove(0);
		int m = heap.get(0);
		
		// add last element to the root
		heap.set(0, heap.remove(heap.size()-1));
		
		// rebalance
		int prev = 0;
		int child = 2*prev + 1;
		while(child < heap.size()){
			if(child < heap.size()-1 && heap.get(child) > heap.get(child + 1))
				child++;
			if(heap.get(prev) < heap.get(child))
				break;
			// swap with the smallest child
			int t = heap.get(prev);
			heap.set(prev, heap.get(child));
			heap.set(child, t);
			
			prev = child;
			child = 2*prev + 1;
		}
		
		return m;
	}

}
