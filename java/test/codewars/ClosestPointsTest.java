import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClosestPointsTest {

    @Test
    public void testSortPointsByX(){
        List<Point> points = Arrays.asList(
                new Point(2, 2), //A
                new Point(2, 8), //B
                new Point(5, 5), //C
                new Point(6, 3), //D
                new Point(6, 7), //E
                new Point(7, 4), //F
                new Point(7, 9)  //G
        );
        points.sort((a, b) -> a.x == b.x? 1: (a.x > b.x? 1: -1) );

        for (int i = 0; i < points.size() - 1; i++) {
            Assert.assertTrue(points.get(i).x <= points.get(i+1).x);
        }

    }


    @Test
    public void test01_Example() {

        List<Point> points = Arrays.asList(
                new Point(2, 2), //A
                new Point(2, 8), //B
                new Point(5, 5), //C
                new Point(6, 3), //D
                new Point(6, 7), //E
                new Point(7, 4), //F
                new Point(7, 9)  //G
        );

        List<Point> result = ClosestPoints.closestPair(points);
        List<Point> expected = Arrays.asList(new Point(6, 3), new Point(7, 4));
        verify(expected, result);
    }

    @Test
    public void myTest(){
        List<Point> points = Arrays.asList(
                new Point(2, 0), //A
                new Point(0, 2), //B
                new Point(16, 1), //C
                new Point(1, 16), //D
                new Point(32, 1), //C
                new Point(1, 32) //C
        );
        List<Point> result = ClosestPoints.closestPair(points);
        List<Point> expected = Arrays.asList(new Point(2, 0), new Point(0, 2));
        verify(expected, result);
    }

    @Test
    public void test02_TwoPoints() {

        List<Point> points = Arrays.asList(
                new Point(2, 2),
                new Point(6, 3)
        );

        List<Point> result = ClosestPoints.closestPair(points);
        List<Point> expected = Arrays.asList(new Point(6, 3), new Point(2, 2));
        verify(expected, result);
    }

    @Test
    public void test03_DuplicatedPoint() {

        List<Point> points = Arrays.asList(
                new Point(2, 2), //A
                new Point(2, 8), //B
                new Point(5, 5), //C
                new Point(5, 5), //C
                new Point(6, 3), //D
                new Point(6, 7), //E
                new Point(7, 4), //F
                new Point(7, 9)  //G
        );

        List<Point> result = ClosestPoints.closestPair(points);
        List<Point> expected = Arrays.asList(new Point(5, 5), new Point(5,5));
        verify(expected, result);
    }

    private void verify(List<Point> expected, List<Point> actual) {
        Comparator<Point> comparer = Comparator.<Point>comparingDouble(p -> p.x);

        Assert.assertNotNull("Returned array cannot be null.", actual);
        Assert.assertEquals("Expected exactly two points.", 2, actual.size());
        Assert.assertFalse("Returned points must not be null.", actual.get(0) == null || actual.get(1) == null);

        expected.sort(comparer);
        actual.sort(comparer);
        boolean eq = expected.get(0).x == actual.get(0).x && expected.get(0).y == actual.get(0).y
                && expected.get(1).x == actual.get(1).x && expected.get(1).y == actual.get(1).y;
        Assert.assertTrue(String.format("Expected: %s, Actual: %s", expected.toString(), actual.toString()), eq);
    }
}
