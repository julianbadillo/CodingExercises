import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class SolutionSafe {

    public static void main(String args[]) {
        SafeCombination.main(args);
    }
}

public class SafeCombination {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        String msg = in.nextLine();
        SafeCombination sc = new SafeCombination();
        System.out.println(sc.decrypt(msg));
	}
	
	static HashMap<String, String> digits = new HashMap<>();
	static{
		digits.put("zero", "0");
		digits.put("one", "1");
		digits.put("two", "2");
		digits.put("three", "3");
		digits.put("four", "4");
		digits.put("five", "5");
		digits.put("six", "6");
		digits.put("seven", "7");
		digits.put("eight", "8");
		digits.put("nine", "9");
	}
	
	public String decrypt(String msg){
		// Reference message "The safe combination is:
		int plain = 'T'-'A';
		int encr = msg.charAt(0) - 'A';
		int rotation = (plain - encr + 26) % 26; // 0 - 21 + 26 = -21 % 26 = 21
		//Aol zhml jvtipuhapvu pz: zpe-mvby-zpe-mvby-aoyll
		//The safe combination is
		char[] chars = msg.split(": ")[1].toLowerCase().toCharArray();
		for (int i = 0; i < chars.length; i++)
			if(chars[i] != ' ' && chars[i] != ':' && chars[i] != '-')
				chars[i] = (char)(((chars[i] - 'a') + rotation)%26 + 'a');
		
		String[] s = new String(chars).split("-");
		String r = Arrays.stream(s).map(x -> digits.get(x)).reduce((x, y) -> x+y).get();
		
		return r;
	}
}


