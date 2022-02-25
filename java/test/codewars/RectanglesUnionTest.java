import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class RectanglesUnionTest {


    @Test
    public void testZeroRectangles() {
        int[][] recs = {};
        Assert.assertEquals("Zero rectangles", 0, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testOneRectangle() {
        int[][] recs = {{0, 4, 11, 6}};
        Assert.assertEquals("One rectangle [0, 4, 11, 6] => 22", 22, RectanglesUnion.calculateSpace(recs));
    }


    @Test
    public void test2RectanglesContained() {
        int[][] recs = {{0,0,20,40},
                {5,10,15,30}};
        Assert.assertEquals(800, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{{0,0,20,40},
                {10,0,20,20}};
        Assert.assertEquals(800, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void test2RectanglesDiagonal() {
        int[][] recs = {{0,0,20,20},
                {10,10,30,30}};
        Assert.assertEquals(700, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{{0,10,20,30},
                {10,0,30,20}};
        Assert.assertEquals(700, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void test2RectanglesCContained() {
        int[][] recs = {{0,0,20,30},
                {10,10,30,20}};
        Assert.assertEquals(700, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{{0,0,30,20},
                {10,10,20,30}};
        Assert.assertEquals(700, RectanglesUnion.calculateSpace(recs));
    }


    @Test
    public void test3Rectangles() {
        int[][] recs = {{0,0,20,20},
                {10,10,30,30},
                {20,20,40,40}};
        Assert.assertEquals(1000, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testRectangles1() {
        int[][] recs = {
                {10,20,40,30},
                {0,0,60,60},
                {30,20,50,40}};
        Assert.assertEquals(3600, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{
                {10,20,40,30},
                {0,0,60,60},
                {30,20,50,40},
                {0,10, 20, 70}};
        Assert.assertEquals(3800, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{
                {10,20,40,30},
                {0,0,60,60},
                {30,20,50,40},
                {0,10, 20, 70},
                {30,0, 80, 30}};
        Assert.assertEquals(4400, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{
                {10,20,40,30},
                {0,0,60,60},
                {30,20,50,40},
                {0,10, 20, 70},
                {30,0, 80, 30},
                {40,10, 70, 60}};
        Assert.assertEquals(4700, RectanglesUnion.calculateSpace(recs));

        recs = new int[][]{{3, 4, 7, 7},
                            {5, 2, 7, 7},};
        Assert.assertEquals(16, RectanglesUnion.calculateSpace(recs));

    }

    @Test
    public void testCalculateSpaceRandomPerformance(){
        int S = 10000, W = 10000, H = 10000;
        int N = 20000;
        Random r = new Random();
        int [][] coords = new int[N][];
        for (int i = 0; i < N; i++) {
            coords[i] = new int[]{r.nextInt(S), r.nextInt(S), 1 + r.nextInt(W), 1 + r.nextInt(H)};
            // positive coordinates
            coords[i][2] += coords[i][0];
            coords[i][3] += coords[i][1];
        }

        var res = RectanglesUnion.calculateSpace(coords);
        Assert.assertTrue(res > 0);

    }

    @Test
    public void mergeRectanglesNoIntersect(){
        int[][] recs = {{1, 3, 11, 5},
                        {3, 0, 11, 3},
                        {0, 2, 8, 8},
                        {1, 3, 6, 5},
                        {1, 0, 2, 5},
                                        };
        var merged = RectanglesUnion.mergeRectangles(recs).toArray(new RectanglesUnion.Rectangle[0]);
        Arrays.stream(merged).forEach(System.out::println);
        var s = Arrays.stream(merged).mapToInt(r -> r.getArea()).sum();
        Assert.assertEquals(75, s);
        for (int i = 0; i < merged.length - 1; i++) {
            for (int j = i + 1; j < merged.length; j++) {
                Assert.assertFalse("Overlapping rectangles: "+merged[i]+" "+merged[j], merged[i].intersects(merged[j]));
            }
        }
    }

    @Test
    public void mergeRectanglesRandomNoIntersect(){
        int S = 5, W = 10, H = 10;
        int N = 50;
        Random r = new Random();
        int [][] coords = new int[N][];
        for (int t = 0; t < 100; t++) {


            for (int i = 0; i < N; i++) {
                coords[i] = new int[]{r.nextInt(S), r.nextInt(S), 1 + r.nextInt(W), 1 + r.nextInt(H)};
                // positive coordinates
                coords[i][2] += coords[i][0];
                coords[i][3] += coords[i][1];
            }

            Arrays.stream(coords).forEach(row -> System.out.println(Arrays.toString(row)));

            var merged = RectanglesUnion.mergeRectangles(coords).toArray(new RectanglesUnion.Rectangle[0]);
            Arrays.stream(merged).forEach(System.out::println);
            for (int i = 0; i < merged.length - 1; i++) {
                for (int j = i + 1; j < merged.length; j++) {
                    Assert.assertFalse("Overlapping rectangles: " + merged[i] + " " + merged[j], merged[i].intersects(merged[j]));
                }
            }

        }

    }

    @Test
    public void testRectangle(){

        var r = new RectanglesUnion.Rectangle(10,20,20,40);
        Assert.assertEquals(10, r.getW());
        Assert.assertEquals(20, r.getH());
        Assert.assertEquals(200, r.getArea());

    }

    @Test
    public void testIntersects(){

        var r = new RectanglesUnion.Rectangle(10,20,20,40);
        var r2 = new RectanglesUnion.Rectangle(0,0, 10, 20);
        Assert.assertFalse(r.intersects(r2));
        Assert.assertFalse(r2.intersects(r));

        r2 = new RectanglesUnion.Rectangle(20,40, 25, 45);
        Assert.assertFalse(r.intersects(r2));
        Assert.assertFalse(r2.intersects(r));

        r2 = new RectanglesUnion.Rectangle(15,30, 25, 45);
        Assert.assertTrue(r.intersects(r2));
        Assert.assertTrue(r2.intersects(r));

        r2 = new RectanglesUnion.Rectangle(15,30, 17, 35);
        Assert.assertTrue(r.intersects(r2));
        Assert.assertTrue(r2.intersects(r));

        r2 = new RectanglesUnion.Rectangle(0,0, 9, 19);
        Assert.assertFalse(r.intersects(r2));
        Assert.assertFalse(r2.intersects(r));

        r2 = new RectanglesUnion.Rectangle(10,41, 20, 52);
        Assert.assertFalse(r.intersects(r2));
        Assert.assertFalse(r2.intersects(r));

        r = new RectanglesUnion.Rectangle(3, 4, 7, 7);
        r2 = new RectanglesUnion.Rectangle(5, 2, 7, 7);
        Assert.assertTrue(r.intersects(r2));
        Assert.assertTrue(r2.intersects(r));

        r = new RectanglesUnion.Rectangle(10, 0, 20, 30);
        r2 = new RectanglesUnion.Rectangle(0, 10, 30, 20);
        Assert.assertTrue(r.intersects(r2));
        Assert.assertTrue(r2.intersects(r));


        r = new RectanglesUnion.Rectangle(0,0, 10,40);
        r2 = new RectanglesUnion.Rectangle(10,10, 30,30);
        Assert.assertFalse(r.intersects(r2));
        Assert.assertFalse(r2.intersects(r));

        r = new RectanglesUnion.Rectangle(1,2, 7,3);
        r2 = new RectanglesUnion.Rectangle(4,0, 9,6);
        Assert.assertTrue(r.intersects(r2));
        Assert.assertTrue(r2.intersects(r));

    }

    @Test
    public void testContains() {
        var r = new RectanglesUnion.Rectangle(0, 0, 20, 40);
        var r2 = new RectanglesUnion.Rectangle(5, 10, 15, 30);
        Assert.assertTrue(r.contains(r2));

        r = new RectanglesUnion.Rectangle(10,20,20,40);
        Assert.assertTrue(r.contains(new RectanglesUnion.Point(10, 20)));
        Assert.assertFalse(r.contains(new RectanglesUnion.Point(10, 10)));
        Assert.assertTrue(r.contains(new RectanglesUnion.Point(15, 35)));
        Assert.assertFalse(r.contains(new RectanglesUnion.Point(25, 35)));
    }


    @Test
    public void testMergingContainedRectangles(){
        var r = new RectanglesUnion.Rectangle(0,0,20,40);
        var r2 = new RectanglesUnion.Rectangle(5,10,15,30);

        var list = r.merge(r2);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0, 20, 40)));

        list = r2.merge(r);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0, 20, 40)));

    }

    @Test
    public void testMergingContainedRectangles2(){
        var r = new RectanglesUnion.Rectangle(0,0,20,40);
        var r2 = new RectanglesUnion.Rectangle(10,0,20,20);

        var list = r.merge(r2);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,20,40)));

        list = r2.merge(r);
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,20,40)));
    }

    @Test
    public void testMergingDiagonalRectangles(){
        var r = new RectanglesUnion.Rectangle(0,0,20,20);
        var r2 = new RectanglesUnion.Rectangle(10,10,30,30);

        var list = r.merge(r2);
        Assert.assertEquals(3, list.size());
        list.stream().forEach(System.out::println);
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,10,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,20,10)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,10,30,30)));

        list = r2.merge(r);
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,10,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,20,10)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,10,30,30)));


    }

    @Test
    public void testMergingDiagonalRectangles2(){
        var r = new RectanglesUnion.Rectangle(0,10,20,30);
        var r2 = new RectanglesUnion.Rectangle(10,0,30,20);

        var list = r.merge(r2);
        list.forEach(System.out::println);
        Assert.assertEquals(3, list.size());

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,10,10,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,30,20)));

        list = r2.merge(r);
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,10,10,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,30,20)));

    }

    @Test
    public void testMergingCContainedHorizontalRectangles(){
        var r = new RectanglesUnion.Rectangle(0,0,20,30);
        var r2 = new RectanglesUnion.Rectangle(10,10,30,20);

        var list = r.merge(r2);

        Assert.assertEquals(2, list.size());

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,20,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(20,10,30,20)));

        list = r2.merge(r);
        list.forEach(System.out::println);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,20,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(20,10,30,20)));

    }

    @Test
    public void testMergingCContainedVerticalRectangles(){
        var r = new RectanglesUnion.Rectangle(0,0,30,20);
        var r2 = new RectanglesUnion.Rectangle(10,10,20,30);

        var list = r.merge(r2);

        Assert.assertEquals(2, list.size());

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,30,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));

        list = r2.merge(r);

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,0,30,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));

        r = new RectanglesUnion.Rectangle(0,10, 40,30);
        r2 = new RectanglesUnion.Rectangle(10,0,20,20);
        Assert.assertTrue(r.intersects(r2));

        list = r2.merge(r);
        list.forEach(System.out::println);
        Assert.assertEquals(2, list.size());

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,10, 40,30)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0, 20, 10)));
        Assert.assertTrue(r2.intersects(r));

    }

    @Test
    public void testMergingCrossRectangles(){
        var r = new RectanglesUnion.Rectangle(0,10,30,20);
        var r2 = new RectanglesUnion.Rectangle(10,0,20,30);

        var list = r.merge(r2);

        Assert.assertEquals(3, list.size());

        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,10,30,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,20,10)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));

        list = r2.merge(r);
        list.forEach(System.out::println);
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(0,10,30,20)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,0,20,10)));
        Assert.assertTrue(list.contains(new RectanglesUnion.Rectangle(10,20,20,30)));

    }
}