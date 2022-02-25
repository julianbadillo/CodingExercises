public class DuplicateEncoder {
	static String encode(String word) {
		word = word.toLowerCase();
		char map[] = new char[256];
		for (int i = 0; i < word.length(); i++)
			if(map[word.charAt(i)] == 0)
				map[word.charAt(i)] = '(';
			else if(map[word.charAt(i)] == '(')
				map[word.charAt(i)] = ')';
		
		char[] res = new char[word.length()];
		for (int i = 0; i < word.length(); i++)
			res[i] = map[word.charAt(i)];
		return new String(res);
		
	}
}
