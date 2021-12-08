import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Implementation of a suffix Tree.
 * Can be compacted to be an explicit tree
 * @author jbadillo
 *
 */
public class SuffixTree {

	private TrieNode root;
	private final char[] word;
	
	/**
	 * 
	 * @param word
	 */
	public SuffixTree(String word) {
		this.word = word.toCharArray();
		root = new TrieNode();
		root.isFinal = true;
	
		// quadratic approach (from longest to shortest)
		for (int i = 0; i < this.word.length; i++)
			root.addWord(this.word, i, this.word.length);
		
	}
	
	public int size(){
		return root.size();
	}
	
	public void compact(){
		root.compact();
	}

	public boolean isSuffix(String suff) {
		return root.searchSuffix(suff.toCharArray(), 0);
	}

	public boolean contains(String patt) {
		return root.searchAll(patt.toCharArray(), 0);
	}
	
	public String getWord(){
		return new String(word);
	}
	
	class Edge{
		int i, j;
		char[] word;
		public Edge(int i, int j, char[] word) {
			this.i = i;
			this.j = j;
			this.word = word;
		}
		
		public int hashCode() {
			// hash based on substring
			int h = 0;
			for (int k = i; k < j; k++) 
				h += Character.hashCode(word[k]);
			return h;
		}
		public boolean equals(Object o) {
			Edge e = (Edge) o;
			// same
			if(i == e.i && j == e.j)
				return true;
			// different size
			if (j - i != e.j - e.i)
				return false;
			boolean eq = true;
			// compare char by char
			for (int k = 0; i + k < j && eq; k++)
				eq &= word[i + k] == e.word[e.i + k];
			return eq;
		}
		
		boolean startsWith(Edge e) {
			// contained and pointing so same word
			if(i == e.i && e.j <= j && word == e.word)
				return true;
			// impossible size
			if (j - i < e.j - e.i)
				return false;
			boolean eq = true;
			// compare char by char, end on e.j
			for (int k = 0; e.i + k < e.j && eq; k++)
				eq &= word[i + k] == e.word[e.i + k];
			return eq;
		}
		
		public String toString() { 
			return new String(word, i, j - i);
		}
	}
	
	class TrieNode {
		HashMap<Edge, TrieNode> child = new HashMap<>();
		int index = -1;
		boolean isFinal = false;
		
		int size(){
			return 1 + child.values().stream()
					.mapToInt(c -> c.size())
					.sum();
		}
		
		void addWord(char[] newWord, int i, int end) {
			if (i == end){
				isFinal = true;
			}else {
				Edge e = new Edge(i, i+1, newWord);
				if (child.get(e) == null)
					child.put(e, new TrieNode());
				child.get(e).addWord(word, i + 1, end);
			}
		}

		/**
		 * Compacts the 1-node-per character tree into an implicit
		 * suffix tree. Useful when built on Quadratic approach
		 */
		public void compact() {
			// compacting is Quadratic
			for (Edge key : new LinkedList<Edge>(child.keySet())) {
				TrieNode node = child.get(key);
				Edge newKey = new Edge(key.i, key.j, key.word);

				// build the longest sequence with one child of non-final nodes
				while (node.child.size() == 1 && !node.isFinal) {
					Entry<Edge, TrieNode> e = node.child.entrySet().iterator().next();
					// TODO extend needs a more delicate calculation
					newKey.j += (e.getKey().j - e.getKey().i); // extend new key
					node = e.getValue();
				}

				child.remove(key);
				child.put(newKey, node);
				node.compact();
			}
		}

		public boolean searchSuffix(char[] suff, int i) {
			if (suff.length == i)
				return isFinal;
			else {
				Edge e = new Edge(i, i, suff);
				// look all prefixes of word
				for (e.j = i + 1; e.j <= word.length && e.j <= suff.length; e.j++) {
					TrieNode n = child.get(e);
					if (n != null)
						return n.searchSuffix(suff, e.j);
				}
				return false;
			}
		}

		public boolean searchAll(char[] patt, int i) {
			if (patt.length == i)
				return true;
			else {
				Edge e = new Edge(i, i, patt);
				for (e.j = i + 1; e.j <= word.length && e.j <= patt.length; e.j++) {
					TrieNode n = child.get(e);
					if (n != null)
						return n.searchAll(patt, e.j);
				}
				
				// not found any piece and still longer than the end
				if(patt.length >= word.length)
					return false;
				
				// if the remaining is contained on any
				e.j = patt.length;
				return child.keySet().stream()
						.anyMatch(e1 -> e1.startsWith(e));
			}
		}
	}

}
