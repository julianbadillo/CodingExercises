import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by jbadillo on 1/31/2016.
 */
public class Scrabble {


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        in.nextLine();
        LinkedList<String> dictionary = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            String W = in.nextLine();
            // only useful words
            if(W.length() <= 7)
                dictionary.add(W);
        }
        String letters = in.nextLine();
        String bestW = "";
        int bestS = 0;
        // Now we go thorough the words
        for(String w: dictionary){
            int s = getScore(w, letters);
            if(s > bestS){
                bestS = s;
                bestW = w;
            }
        }
        System.out.println(bestW);
    }

    static int[] points = {
    1,//a
    3,//b
    3,//c
    2,//d
    1,//e
    4,//f
    2,//g
    4,//h
    1,//i
    8,//j
    5,//k
    1,//l
    3,//m
    1,//n
    1,//o
    3,//p
    10,//q
    1,//r
    1,//s
    1,//t
    1,//u
    4,//v
    4,//w
    8,//x
    4,//y
    10,};//z

/*    e, a, i, o, n, r, t, l, s, u 	1
    d, g 	2
    b, c, m, p 	3
    f, h, v, w, y 	4
    k 	5
    j, x 	8
    q, z 	10
*/
    static int getScore(String w, String letters){
        boolean[] used = new boolean[letters.length()];
        boolean found;
        int score = 0;
        // Quadratic (7*7)
        for (int i = 0; i < w.length(); i++) {
            found = false;
            for (int j = 0; j < letters.length(); j++) {
                if (w.charAt(i) == letters.charAt(j) && !used[j]){
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if(!found)
                return 0;
            score += points[w.charAt(i)-'a'];
        }
        return score;
    }
}
