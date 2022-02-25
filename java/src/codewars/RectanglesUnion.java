import java.util.*;
import java.util.stream.Collectors;


public class RectanglesUnion {


    public static int calculateSpace(int[][] rectangles) {
        // set of disjoint rectangle
        var set = mergeRectangles(rectangles);
        return set.stream().mapToInt(r -> r.getArea()).sum();
    }

    public static Set<Rectangle>  mergeRectangles(int[][] rectangles) {
        HashSet<Rectangle> set = new HashSet<>();
        // TODO optimize - sorted by coordinates - smart search
        for (int i = 0; i < rectangles.length; i++) {
            Rectangle newRect = new Rectangle(rectangles[i][0],rectangles[i][1],rectangles[i][2],rectangles[i][3]);
            // find all overlapping rectangles
            var overlapping = set.stream().filter(r -> r.intersects(newRect)).collect(Collectors.toList());
            if(overlapping.size() == 0)
                set.add(newRect);
            else {
                // remove them from the set
                set.removeIf(r -> r.intersects(newRect));
                var newRects = new HashSet<Rectangle>();
                // add all merging
                overlapping.forEach(r -> newRects.addAll(r.merge(newRect)));
                // resolve overlaps

                Rectangle rec1 = null, rec2 = null;
                boolean overlaps = true;
                // TODO N^2? potentially
                // while still overlaps
                while(overlaps) {
                    overlaps = false;
                    var ite1 = newRects.iterator();
                    while (ite1.hasNext() && !overlaps) {
                        rec1 = ite1.next();
                        var ite2 = newRects.iterator();
                        while (ite2.hasNext() && !overlaps) {
                            rec2 = ite2.next();
                            // find a pair that overlaps
                            if (!rec1.equals(rec2) && rec1.intersects(rec2))
                                overlaps = true;

                        }
                    }
                    if(overlaps){
                        // remove and merge
                        newRects.remove(rec1);
                        newRects.remove(rec2);
                        newRects.addAll(rec1.merge(rec2));
                    }
                }
                // no rectangles should be overlapping

                set.addAll(newRects);
            }
        }
        return set;
    }

    public static class Point{
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Point))
                return false;
            Point p = (Point)obj;
            return this.x == p.x && this.y == p.y;
        }
    }

    public static class Rectangle {
        private Point p1, p2, p3, p4;
        public Rectangle(Point p1, Point p2){
            this.p1 = p1;
            this.p2 = p2;
            p3 = new Point(p1.x, p2.y);
            p4 = new Point(p2.x, p1.y);
        }

        public Rectangle(int x1, int y1, int x2, int y2){
            p1 = new Point(x1, y1);
            p2 = new Point(x2, y2);
            p3 = new Point(p1.x, p2.y);
            p4 = new Point(p2.x, p1.y);
        }
        public int getW(){
            return p2.x - p1.x;
        }
        public int getH(){
            return p2.y - p1.y;
        }
        public int getArea(){
            return (p2.x - p1.x)*(p2.y - p1.y);
        }

        public boolean intersects(Rectangle rec){
            if(contains(rec) || rec.contains(this))
                return true;

            // rec.p1 inside rectangle but not on right - top sides
            if((this.p1.x <= rec.p1.x && rec.p1.x < this.p2.x &&
                    this.p1.y <= rec.p1.y && rec.p1.y < this.p2.y)||
                // mirror
                (rec.p1.x <= this.p1.x && this.p1.x < rec.p2.x &&
                    rec.p1.y <= this.p1.y && this.p1.y < rec.p2.y))
                return true;
            // rec.p2 inside rectangle but not left bottom
            if((this.p1.x < rec.p2.x && rec.p2.x <= this.p2.x
                && this.p1.y < rec.p2.y && rec.p2.y <= this.p2.y) ||
                // mirror
                (rec.p1.x < this.p2.x && this.p2.x <= rec.p2.x
                    && rec.p1.y < this.p2.y && this.p2.y <= rec.p2.y))
                return true;
            // rec.p3 inside rectangle but not right bottom
            if((this.p1.x <= rec.p3.x && rec.p3.x < this.p2.x
                && this.p1.y < rec.p3.y && rec.p3.y <= this.p2.y) ||
                // mirror
                (rec.p1.x <= this.p3.x && this.p3.x < rec.p2.x
                && rec.p1.y < this.p3.y && this.p3.y <= rec.p2.y))
                return true;
            // rec.p4 inside rectangle but not left top
            if((this.p1.x < rec.p4.x && rec.p4.x <= this.p2.x
                && this.p1.y <= rec.p4.y && rec.p4.y < this.p2.y) ||
                // mirror
                (rec.p1.x < this.p4.x && this.p4.x <= rec.p2.x
                && rec.p1.y <= this.p4.y && this.p4.y < rec.p2.y))
                return true;
            // cross
            return (this.p1.x <= rec.p1.x && rec.p2.x <= this.p2.x &&
                    rec.p1.y <= this.p1.y && this.p2.y <= rec.p2.y)||
                (rec.p1.x <= this.p1.x && this.p2.x <= rec.p2.x &&
                        this.p1.y <= rec.p1.y && rec.p2.y <= this.p2.y);
        }


        public boolean contains(Rectangle rec){
            return contains(rec.p1) && contains(rec.p2) && contains(rec.p3) && contains(rec.p4);
        }

        public boolean contains(Point... points){
            return Arrays.stream(points).allMatch(p -> this.contains(p));
        }

        public boolean contains(Point p){
            return p1.x <= p.x && p.x <= p2.x && p1.y <= p.y && p.y <= p2.y;
        }

        /***
         * Merges a rectangle with other intersect rectangle.
         * @param rec
         * @return a list of non-overlapping rectangles.
         */
        public Collection<Rectangle> merge(Rectangle rec){

            var res = new LinkedList<Rectangle>();
            // easy case equals
            if(this.equals(rec))
                res.add(this);
            // second easy case - no overlapping
            else if(!this.intersects(rec)){
                res.add(this);
                res.add(rec);
            }
            // easy case - one inside the other
            else if(this.contains(rec))
                res.add(this);
            else if(rec.contains(this))
                res.add(rec);
            // rec is right and above
            else if(this.contains(rec.p1) && rec.contains(this.p2)) {
                res.add(new Rectangle(p1.x, p1.y, rec.p1.x, p2.y));
                res.add(new Rectangle(rec.p1.x, p1.y, p2.x, rec.p1.y));
                res.add(rec);
            }
            // mirror case
            else if(rec.contains(this.p1) && this.contains(rec.p2))
                return rec.merge(this);
            // rec is right and below
            else if(this.contains(rec.p3) && rec.contains(this.p4)) {
                res.add(new Rectangle(p1.x, p1.y, rec.p1.x, p2.y));
                res.add(new Rectangle(rec.p1.x, rec.p2.y, p2.x, p2.y));
                res.add(rec);
            }
            // mirror cases
            else if(rec.contains(this.p3) && this.contains(rec.p4))
                return rec.merge(this);
            // C-contained, bigger on the left
            else if(this.contains(rec.p1, rec.p3))
            {
                res.add(this);
                res.add(new Rectangle(this.p2.x, rec.p1.y, rec.p2.x, rec.p2.y));
            }
            // mirror
            else if(rec.contains(this.p1, this.p3))
                return rec.merge(this);
            // C-contained, bigger on the right
            else if(this.contains(rec.p2, rec.p4))
            {
                res.add(this);
                res.add(new Rectangle(rec.p1.x, rec.p1.y, this.p1.x, rec.p2.y));
            }
            // mirror case
            else if(rec.contains(this.p2, this.p4))
                return rec.merge(this);
            // C-contained, bigger on the bottom
            else if(this.contains(rec.p1, rec.p4))
            {
                res.add(this);
                res.add(new Rectangle(rec.p1.x, this.p2.y, rec.p2.x, rec.p2.y));
            }
            // mirror case
            else if(rec.contains(this.p1, this.p4))
                return rec.merge(this);
            // C-contained, bigger on the top
            else if(this.contains(rec.p3, rec.p2)){
                res.add(this);
                res.add(new Rectangle(rec.p1.x, rec.p1.y, rec.p2.x, this.p1.y));
            }
            // mirror case
            else if(rec.contains(this.p3, this.p2))
                return rec.merge(this);
                // cross case
            else if((this.p1.x <= rec.p1.x && rec.p2.x <= this.p2.x &&
                        rec.p1.y <= this.p1.y && this.p2.y <= rec.p2.y))
            {
                res.add(this);
                res.add(new Rectangle(rec.p1.x, rec.p1.y, rec.p2.x, this.p1.y));
                res.add(new Rectangle(rec.p1.x, this.p2.y, rec.p2.x, rec.p2.y));
            }
            // mirror
            else if ((rec.p1.x <= this.p1.x && this.p2.x <= rec.p2.x &&
                        this.p1.y <= rec.p1.y && rec.p2.y <= this.p2.y))
                return rec.merge(this);
            else
                throw new RuntimeException("No intersection!");

            res.removeIf(r -> r.getArea() == 0);
            return res;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Rectangle))
                return false;
            Rectangle r = (Rectangle)obj;
            return p1.equals(r.p1) && p2.equals(r.p2);
        }

        @Override
        public String toString() {
            return String.format("[(%s,%s), (%s,%s)]", p1.x, p1.y, p2.x, p2.y);
        }

        @Override
        public int hashCode() {
            return  Integer.hashCode(p1.x) ^ Integer.hashCode(p1.y) ^ Integer.hashCode(p2.x) ^ Integer.hashCode(p2.y);
        }
    }

}