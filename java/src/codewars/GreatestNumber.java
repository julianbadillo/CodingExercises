import java.util.ArrayList;
import java.util.Scanner;

public class GreatestNumber {

	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        in.nextLine();
        String input = in.nextLine();

        GreatestNumber gn = new GreatestNumber();
        System.out.println(gn.greatest(input.replace(" ", "").toCharArray()));
        in.close();
    }
	
	
	public String greatest(char[] chars){
		// sort them
		ArrayList<Character> list = new ArrayList<>();
		boolean neg = false, dot = false;
		boolean zero = true;
		for(char c: chars) 
			if(c == '.') dot = true; 
			else if(c == '-') neg = true; 
			else{
				list.add(c);
				if(c != '0') zero = false;
			}
		if(zero)
			return "0";
		
		if(neg)
			list.sort((c1, c2) -> c1 - c2 );
		else
			list.sort((c1, c2) -> c2 - c1 );
		
		if(dot)
			if(neg)
				list.add(1, '.');
			else
			{
				int i = list.size()-1;
				if(list.get(i) != '0')
					list.add(i, '.');
				else
					list.remove(i);
			}
		
		if(neg)
			list.add(0,'-');
		
		String s = list.stream().map(c -> Character.toString(c)).reduce((s1, s2)-> s1+s2).get();
		return s;
	}
	

}

