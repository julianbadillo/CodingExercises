import java.util.LinkedList;
import java.util.List;

/***
 * Given two sorted lists of intervals
 * Produce a list of of the union of intervals.
 * 
 */
public class IntervalUnion {

    public static class Interval {
        public int begin;
        public int end;

        public Interval(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        public boolean intersects(Interval n) {
            if (this.begin <= n.begin && n.begin < this.end)
                return true;
            if (this.begin < n.end && n.end <= this.end)
                return true;
            // flip case
            if (n.begin <= this.begin && this.begin < n.end)
                return true;
            if (n.begin < this.end && this.end <= n.end)
                return true;
            return false;
        }

        public Interval merge(Interval n) {
            Interval result = new Interval(Math.min(this.begin, n.begin), Math.max(this.end, n.end));
            return result;
        }

        @Override
        public String toString() {
            return "[" + this.begin + ", " + this.end + "]";
        }
    }

    public static List<Interval> union(Interval[] A, Interval[] B) {
        LinkedList<Interval> output = new LinkedList<Interval>();
        Interval temp = null;
        int ia = 0, ib = 0;
        while (ia < A.length && ib < B.length) {
            Interval intA = A[ia];
            Interval intB = B[ib];
            if (temp != null) {
                // take the smallest and merge it
                Interval minInt = intA.begin < intB.begin ? intA : intB;
                if (!temp.intersects(minInt)) {
                    // add and go to next loop
                    output.add(temp);
                    temp = null;
                }
                // intersects
                else {
                    temp = temp.merge(minInt);
                    // move
                    if (intA.begin < intB.begin) {
                        ia++;
                    } else {
                        ib++;
                    }
                }
            }
            // no intersection
            else if (!intA.intersects(intB)) {
                // A smaller
                if (intA.begin < intB.begin) {
                    output.add(intA);
                    ia++;
                }
                // B smaller
                else {
                    output.add(intB);
                    ib++;
                }
            }
            // intersection
            else {
                temp = intA.merge(intB);
                ia++;
                ib++;
            }
        }

        // remaining elements
        while (ia < A.length) {
            Interval intA = A[ia];
            if (temp != null) {
                if (temp.intersects(intA)) {
                    temp = temp.merge(intA);
                    ia++;
                } else {
                    output.add(temp);
                    temp = null;
                }
            } else {
                output.add(intA);
                ia++;
            }
        }

        while (ib < B.length) {
            Interval intB = B[ib];
            if (temp != null) {
                if (temp.intersects(intB)) {
                    temp = temp.merge(intB);
                    ib++;
                } else {
                    output.add(temp);
                    temp = null;
                }
            } else {
                output.add(intB);
                ib++;
            }
        }
        if (temp != null)
            output.add(temp);
        return output;
    }

}