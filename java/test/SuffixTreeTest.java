import static org.junit.Assert.*;

import org.junit.Test;

public class SuffixTreeTest {

	@Test
	public void testCreate() {
		SuffixTree tree = new SuffixTree("");
		tree = new SuffixTree("paosdkmasoipfqowqwasd");
		tree = new SuffixTree("cacacacababbbabbaccacaaa");
		tree = new SuffixTree("asssqqqqqsqsqqsssqqsqoqiqoiw");
		tree = new SuffixTree("qqpqpqpqpqpqpqpqppppppqpqpqpqqqq");
		tree = new SuffixTree("rrrqwwweeqqqwwweeeeoeoeoeowiwiwiwuququququwn");
		tree = new SuffixTree("qqqqqwwwwwwweeeeeeerrwrwrwwwrw");
		tree = new SuffixTree("qqqqqqqqqqqqqqqqqqqqqrrrrrqrqrqbabab");
		tree = new SuffixTree("assssssaaaaaaaaassasa");
		tree = new SuffixTree("bbababababbbbbbaaaabbbb");
		tree = new SuffixTree("abbbbaaaaabbbbbabababaaaaaaaba");
	}
	
	// TODO a test validating construction rules
	// m leaves
	// no two edges on the same node have the same beginning
	
	@Test
	public void testSize(){
		SuffixTree tree = new SuffixTree("");
		assertEquals(1, tree.size());
		tree = new SuffixTree("a");
		assertEquals(2, tree.size());
		tree = new SuffixTree("ab");
		assertEquals(4, tree.size());
		tree = new SuffixTree("abc");
		assertEquals(7, tree.size());
		tree = new SuffixTree("abcd");
		assertEquals(11, tree.size());
		
	}
	
	@Test
	public void testCompact(){
		SuffixTree tree = new SuffixTree("abcd");
		assertEquals(11, tree.size());
		tree.compact();
		assertEquals(5, tree.size());
		String word = "abbbbaaaaabbbbbabababaaaaaaaba";
		tree = new SuffixTree(word);
		assertEquals(366, tree.size());
		tree.compact();
		assertEquals(53, tree.size());
		//assertFalse(tree.contains("bbbaaaa"));
		
		word = "abcdef";
		tree = new SuffixTree(word);
		tree.compact();
		
		// Test true case after compacting
		for (int i = 0; i < word.length(); i++)
			assertTrue(word.substring(i)+" not found", tree.isSuffix(word.substring(i)));
		
		// test false case
		for (int i = 0; i < word.length() - 2; i++)
			assertFalse("case " + i + "failed", tree.isSuffix(word.substring(i, word.length() - 2)));
			
		
		// test true case of contains
		for (int i = 0; i < word.length() - 1; i++)
			for (int j = i; j <= word.length(); j++)
				assertTrue(word.substring(i, j) + " was not found", tree.contains(word.substring(i, j)));
		
		// test false case
		assertFalse(tree.contains("aaa"));
		assertFalse(tree.contains("bbb"));
		assertFalse(tree.contains("ccc"));
		assertFalse(tree.contains("abcdea"));
		assertFalse(tree.contains("bca"));
	}

	@Test
	public void testSearchSuffix() {
		String word = "abc";
		SuffixTree tree = new SuffixTree(word);
		tree.isSuffix("");
		for (int i = 0; i <= word.length(); i++)
			assertTrue(word.substring(i)+" not found", tree.isSuffix(word.substring(i)));
	}
	
	@Test
	public void testSearchSuffix2() {
		String word = "abccbdbcbcbababcbababdbabbaaaa";
		SuffixTree tree = new SuffixTree(word);
		assertTrue(tree.isSuffix(word.substring(1)));
		for (int i = 0; i <= word.length(); i++)
			assertTrue(word.substring(i)+" not found", tree.isSuffix(word.substring(i)));
	}

	@Test
	public void testSearchNoSuffix() {
		String word = "abcdefghijklmnopqrstuvwxyz";
		SuffixTree tree = new SuffixTree(word);
		for (int i = 0; i < word.length() - 2; i++)
			assertFalse(tree.isSuffix(word.substring(i, word.length() - 2)));
	}

	
	@Test
	public void testSearchSubstring() {
		String word = "abcdef";
		SuffixTree tree = new SuffixTree(word);
		for (int i = 0; i < word.length() - 1; i++)
			for (int j = i; j <= word.length(); j++)
				assertTrue(word.substring(i, j) + " was not found", tree.contains(word.substring(i, j)));

	}
	
	@Test
	public void testSearchSubstring2() {
		String word = "abccbdbcbcbababcbababdbabbaaaa";
		SuffixTree tree = new SuffixTree(word);
		for (int i = 0; i < word.length() - 1; i++)
			for (int j = i; j <= word.length(); j++)
				assertTrue(word.substring(i, j) + " was not found", tree.contains(word.substring(i, j)));

	}

	@Test
	public void testSearchNoSubstring() {
		String word = "abccbdbcbcbababcbababdbabbaaaa";
		SuffixTree tree = new SuffixTree(word);
		assertFalse(tree.contains("abccbdbcbcbababcbababdbabbaaaac"));
		assertFalse(tree.contains("aaaaaaaaa"));
		assertFalse(tree.contains("bbbbbbbbb"));
		assertFalse(tree.contains("bbaaaab"));
		assertFalse(tree.contains("bbbaaaa"));
	}
}
