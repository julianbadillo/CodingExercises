import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Bilbo.main(args);
    }
}

public class Bilbo {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        
        // naive algorithm - only one rune
        StringBuffer bf = new StringBuffer();
        char[] chars = line.toCharArray();
        char curChar = ' ';
        int curN = 0;
        int n = 0;
        for (int i = 0; i < chars.length; i++) {
        	n = chars[i] == ' '? 0: 1 + chars[i] - 'A';
			curN = curChar == ' ' ? 0: 1 + curChar - 'A';
			
			int delta = n - curN;
			if(Math.abs(delta) > 27 - Math.abs(delta))
				delta = delta > 0 ? delta - 27 : 27 + delta;
				
			if(delta > 0)
				bf.append(new String(new char[delta]).replace((char)0, '+'));
			else if(delta < 0)
				bf.append(new String(new char[-delta]).replace((char)0, '-'));
			bf.append('.');
			curChar = chars[i];
		}
        
        System.out.println(bf.toString());
	}
}
