import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class Interval {

    public static int sumIntervals(int[][] intervals) {
        if(intervals == null || intervals.length == 0)
            return 0;
        var sortedList = Arrays.stream(intervals)
                .map(p -> new Seg(p[0],p[1]))
                .sorted(Comparator.comparing(p -> p.from))
                .collect(Collectors.toList());
        var list = new LinkedList<Seg>();
        Seg last = null;
        for(Seg seg: sortedList)
        {
            if(last == null){
                last = seg;
                list.addLast(last);
            } else if(seg.from <= last.to){
                last.to = max(seg.to, last.to);
            }
            else{
                last = seg;
                list.addLast(last);
            }
        }
        return list.stream().mapToInt(s -> s.length()).sum();
    }

    private static class Seg{
        int from;
        int to;
        private Seg(int from, int to){
            this.from = from;
            this.to = to;
        }

        int length(){
            return to - from;
        }
    }

}
