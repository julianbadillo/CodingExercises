import java.util.Arrays;
import java.util.List;

public class ClosestPoints {

    public static List<Point> closestPair(List<Point> points) {
        // sort by x coordinate
        int n = points.size();
        points.sort((a, b) -> a.x == b.x? 1: (a.x > b.x? 1: -1) );
        R r = closestPairR(points, 0, n);
        return Arrays.asList(r.p1, r.p2);
    }

    private static R closestPairR(List<Point> points, int start, int end){
        // base cases
        R r = new R();
        // one point
        if(end - start <= 1){
            r.d = Double.MAX_VALUE;
            return r;
        }
        // two points
        if(end - start == 2){
            r.p1 = points.get(start);
            r.p2 = points.get(start + 1);
            r.d = dist(r.p1, r.p2);
            return r;
        }
        // recursive case
        int middle = (end + start) / 2;
        R r1 = closestPairR(points, start, middle);
        R r2 = closestPairR(points, middle, end);
        if(r1.d < r2.d)
            r = r1;
        else
            r = r2;
        // case combination
        double borderx = points.get(middle).x;
        for (int i = start; i < middle; i++) {
            // skip cases far from the border
            Point p1 = points.get(i);
            if(borderx - p1.x > r.d)
                continue;
            for (int j = middle; j < end; j++) {
                //skip cases far from the point
                Point p2 = points.get(j);
                if(p2.x - p1.x > r.d)
                    break;
                double d = dist(p1, p2);
                if(d < r.d){
                    r.d = d;
                    r.p1 = p1;
                    r.p2 = p2;
                }
            }
        }

        return r;
    }

    private static class R{
        Point p1, p2;
        double d;
    }


    private static double dist(Point p1, Point p2){
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));
    }
}

class Point {
    public double x, y;

    public Point() {
        x = y = 0.0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        } else {
            return false;
        }
    }
}