import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RailFenceCipher {

    static int [] permutationTable (int n, int l){
        List<Integer>[] rails = new List[n];
        for (int i = 0; i < rails.length; i++)
            rails[i] = new LinkedList<>();
        // fill the rail
        int d = 1;
        for(int i = 0, row=0; i < l; i++){
            rails[row].add(i);
            row += d;
            // flip direction
            if(row == 0 || row == n - 1)
                d *= -1;
        }
        // collect
        var c = Arrays.stream(rails)
                .flatMapToInt(line -> line.stream().mapToInt(x -> x))
                .toArray();
        return c;
    }

    static String encode(String s, int n) {
        if(s.equals("")) return "";
        var t = permutationTable(n, s.length());
        char[] array = new char[s.length()];
        for(int i = 0; i < s.length(); i++)
            array[i] = s.charAt(t[i]);
        return new String(array);
    }


    static String decode(String s, int n) {
        if(s.equals("")) return "";
        var t = permutationTable(n, s.length());
        char[] array = new char[s.length()];
        for(int i = 0; i < s.length(); i++)
            array[t[i]] = s.charAt(i);
        return new String(array);
    }
}