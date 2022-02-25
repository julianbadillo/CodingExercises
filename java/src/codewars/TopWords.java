import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;

public class TopWords {

    public static List<String> top3(String s) {
        var mat = Pattern.compile("([a-zA-Z]'?)+").matcher(s);
        var words = new HashMap<String, Integer>();
        while (mat.find()) {
            var w = mat.group().toLowerCase();
            words.put(w, words.containsKey(w) ? words.get(w) + 1 : 1);
        }

        var list = new LinkedList<String>();
        for (int i = 0; i < 3; i++) {
            var maxWord = words.entrySet().stream().max(comparing(Entry::getValue));
            if(maxWord.isPresent()) {
                list.add(maxWord.get().getKey());
                words.remove(maxWord.get().getKey());
            }
        }
        return list;
    }
}
