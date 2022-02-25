import java.util.Arrays;
import java.util.LinkedList;

public class LuckySum{
	
	public static void main(String[] args) {
		LuckySum l = new LuckySum();
		//System.out.println(l.construct("???"));
		//System.out.println(l.construct("?"));
		//System.out.println(l.construct("2?"));
		//System.out.println(l.construct("4?8"));
		//System.out.println(l.construct("??????????"));
		long t = System.currentTimeMillis();
		System.out.println(l.construct("??????????????")); //11888888888888
		t = System.currentTimeMillis() - t;
		System.out.format("t = %dns", t);
		
	}

    boolean match(long sum, char[] digits){
		char[] sumc = Long.toString(sum).toCharArray();
		if(sumc.length != digits.length)
			return false;
		for(int i=0; i<digits.length; i++)
			if(digits[i] != '?' && sumc[i] != digits[i])
				return false;
		return true;
	
	}

	boolean increaseLuckyDigit(char[] lucky){
		int i = lucky.length-1;
		while(i >= 0){
			if(lucky[i]=='0'){
				lucky[i] = '4';
				return true;
			}else if(lucky[i]=='4'){
				lucky[i] = '7';
				return true;
			}else if(lucky[i]=='7'){
				lucky[i] = '4';
				//continue to next digit
				i--;
			}
		}
		return false;
	}

	/**
	 * Return the position of the array or the position
	 * inmediately left
	 * @param arr
	 * @param a
	 * @param b
	 * @param x
	 * @return
	 */
	int search(Long[] arr, int a, int b, long x){
		//not found but closest
		if(a == b-1) return a;
		
		int m = (a+b)/2;
		if(arr[m] == x) return m;
		//left
		if(x < arr[m]) return search(arr,a,m,x);
		//right
		return search(arr,m,b,x);
		
	}
	
	long getMinSum(char[] digits){
		char [] d = Arrays.copyOf(digits, digits.length);
		for (int i = 0; i < d.length; i++)
			if(d[i] == '?')
				d[i] = i!=0?'0':'1';//fill with zeros
		
		return Long.parseLong(new String(d));
	}
	
	//Time of trying all possible
	//  fillings of notes = 10^1 - 10^14
	//TIme of trying all possible lucky sums
	//  (2^14)^2 = 2^28 ~ 64*10^7
	public long construct(String note){
		//find the smallest lucky sum that fixes note
		//try brute force
		char [] digits = note.toCharArray();
		long min = getMinSum(digits);
		//calculate all lucky numbers
		LinkedList<Long> lucky = new LinkedList<Long>();
		char[] ld = new char[digits.length];
		Arrays.fill(ld, '0'); //fill with zero
		//add all lucky digits
		while(increaseLuckyDigit(ld)){
			//add it
			Long l = Long.parseLong(new String(ld));
			lucky.add(l);
		}
		//System.out.println(lucky);
		Long[] arr = lucky.toArray(new Long[lucky.size()]);
		
		//start from smallest to largest
		int p1 = 0;
		int p2;
		long n1, n2;
		//triangle
		for(; p1 < arr.length; p1++){
			n1 = arr[p1];
			//search the one that could produce this min sum
			p2 = search(arr, 0, arr.length, min-n1);
			//triangle
			for (; p2 <= p1; p2++) {
				n2 = arr[p2];
				System.out.println(n1+","+n2);
				if(match(n1+n2, digits))
					return n1+n2;
			}
		}
		return -1;
	
	}


}