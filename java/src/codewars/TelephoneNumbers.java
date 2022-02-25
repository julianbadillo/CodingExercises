import java.util.Scanner;
import java.util.TreeSet;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class TelephoneNumbers {

    static class Trie implements Comparable<Trie>{
        char v;
        TreeSet<Trie> children = new TreeSet<>();
        Trie(char v){
            this.v = v;
        }
        public int compareTo(Trie o) {
            return o.v - this.v;

        }

        void addWord(String w, int i){
            //assumes at least one char in common
            if(w.charAt(i) != v)
                throw new RuntimeException("Error on validation");
            //word finished
            if( i == w.length() -1 )
                return;

            //looks which one shares the next letter
            for(Trie t: children){
                if(w.charAt(i + 1) == t.v){
                    //recursion
                    t.addWord(w, i + 1);
                    return;
                }
            }

            //add new trie if not found
            Trie  t = new Trie(w.charAt(i+1));
            children.add(t);
            //recursion
            t.addWord(w, i + 1);
        }

        int totalNodes(){
            int total = 0;
            for(Trie t: children)
                total += t.totalNodes();
            return 1 + total;
        }
    }


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        TreeSet<String> phones = new TreeSet<String>();
        for (int i = 0; i < N; i++)
            phones.add(in.next());

        TreeSet<Trie> trie = new TreeSet<>();

        for(String s: phones){
            //looks which one shares the next letter
            Trie start = null;
            for(Trie t: trie){
                if(s.charAt(0) == t.v){
                    start = t;
                    break;
                }
            }

            //add new trie if not found
            if(start == null) {
                start = new Trie(s.charAt(0));
                trie.add(start);
            }
            //recursion
            start.addWord(s, 0);
        }

        //calculate trie size
        int total = 0;
        for(Trie t: trie)
            total += t.totalNodes();
        System.out.println(total); // The number of elements (referencing a number) stored in the structure.
    }
}
