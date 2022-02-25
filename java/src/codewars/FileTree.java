import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class SolutionFT {

    public static void main(String args[]) {
        FileTree.main(args);
    }
}

public class FileTree {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String S = in.nextLine();
		String F = in.nextLine();
		int N = in.nextInt();
		in.nextLine();
		
		// process flags
		String [] flags = F.split(",");
		boolean all = Arrays.stream(flags).anyMatch(s -> s.trim().equals("-a"));
		boolean dir = Arrays.stream(flags).anyMatch(s -> s.trim().equals("-d"));
		String level = Arrays.stream(flags).filter(s -> s.matches("-L \\d+"))
				.findAny()
				.orElse(null);
		
		Integer l = null;
		if(level != null)
			try{
				l = Integer.parseInt(level.replace("-L", "").trim());
			}catch(NumberFormatException ne){
				//ne.printStackTrace();
			}
		
		NodeF root = new NodeF(".");
		// build a tree
		for (int i = 0; i < N; i++)
			root.add(in.nextLine().split("/"), 1, all);
				
		// find the sub-node
		boolean hasRoot = S.startsWith("./") || S.equals(".");

		// fix the path
		String path = hasRoot ? S : "./" + S;
		
				NodeF n = root.find(path.split("/"), 0);
		if (n != null) {
			if(dir)
				root.pruneFiles();
			if(l != null)
				n.pruneLevel(l);
			
			int dirs = n.dirs();
			int files = n.files();
			
			String out = n.print(S);
			System.out.println(out);
			if(dir)
				System.out.println(dirs - 1 + " director" + (dirs == 2?"y":"ies"));
			else
				System.out.println(dirs - 1 + " director"+ (dirs == 2?"y":"ies")+", " + files + " file"+(files == 2?"":"s"));
		} else {
			System.out.println(S + " [error opening dir]");
			System.out.println();
			if(dir)
				System.out.println("0 directories");
			else
				System.out.println("0 directories, 0 files");
		}
	}
}

class NodeF implements Comparable<NodeF> {
	String d;
	boolean isFile = true;
	TreeSet<NodeF> children = new TreeSet<>();
	String p;
	String pL;
	NodeF(String d) {
		this.d = d;
		this.p = d.startsWith(".") ? d.substring(1): d;
		this.pL = p.toLowerCase();
	}

	public int compareTo(NodeF o) {
		// so if first letter different, just alphabetical
		if(pL.charAt(0) != o.pL.charAt(0))
			return pL.charAt(0) - o.pL.charAt(0);
		// if first letter same, lowercase first
		if('a' <= pL.charAt(0) && pL.charAt(0) <= 'z'
				&& 'A' <= o.pL.charAt(0) && o.pL.charAt(0) <= 'Z')
			return -1;
		if('A' <= pL.charAt(0) && pL.charAt(0) <= 'Z'
				&& 'a' <= o.pL.charAt(0) && o.pL.charAt(0) <= 'z')
			return 1;
		// plain alphabetical order
		return p.compareTo(o.p);
	}

	void add(String p[], int index, boolean all) {
		if (index < p.length) {
			// skip hidden files
			if(!all && p[index].startsWith("."))
				return;
			// check exists
			NodeF n = null;
			for (NodeF x : children)
				if (x.d.equals(p[index]))
					n = x;
			// create if not exits
			if (n == null) {
				isFile = false;
				n = new NodeF(p[index]);
				children.add(n);
			}
			n.add(p, index + 1, all);
		}
	}
	void pruneFiles(){
		children.removeIf(n -> n.isFile);
		for(NodeF n: children)
			n.pruneFiles();
	}
	
	void pruneLevel(int l){
		if(l == 0)
			children.clear();
		else
			for(NodeF n: children)
				n.pruneLevel(l - 1);
	}

	NodeF find(String p[], int level) {
		if (!p[level].equals(d))
			return null;
		if (level == p.length - 1)
			return this;
		for (NodeF n : children) {
			NodeF r = n.find(p, level + 1);
			if (r != null && r.children.size() > 0)
				return r;
		}
		return null;
	}

	String print(String subs){
		String out = subs + "\n";
		int i = 0;
		for (NodeF n : children)
			out += n.print(i++ == this.children.size() - 1, "");
		return out;
	}
	
	String print(boolean last, String prefix) {

		String out = prefix;
		if(last)
			out += "`-- ";
		else
			out += "|-- ";
		out += d + "\n";

		int i = 0;
		for (NodeF n : children)
			if(last)
				out += n.print(i++ == this.children.size() - 1, prefix+"    ");
			else
				out += n.print(i++ == this.children.size() - 1, prefix+"|   ");
		return out;
	}

	int dirs() {
		if (children.size() == 0)
			return isFile? 0: 1;
		int dirs = 1;
		for (NodeF n : children)
			dirs += n.dirs();
		return dirs;
	}

	int files() {
		if (children.size() == 0)
			return isFile? 1: 0;
		int files = 0;
		for (NodeF n : children)
			files += n.files();
		return files;
	}
}
