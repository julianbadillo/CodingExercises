
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import org.junit.Test;

public class IntervalUnionTest{

    @Test
    public void testIntersectsSame(){
        var ite1 = new IntervalUnion.Interval(1, 3);
        var ite2 = new IntervalUnion.Interval(1, 3);

        assertTrue(ite1.intersects(ite2));
        assertTrue(ite2.intersects(ite1));

    }
    
    @Test
    public void testIntersectsIncluded(){
        var ite1 = new IntervalUnion.Interval(1, 5);
        var ite2 = new IntervalUnion.Interval(1, 3);
        var ite3 = new IntervalUnion.Interval(3, 5);
        var ite4 = new IntervalUnion.Interval(2, 4);
        

        assertTrue(ite1.intersects(ite2));
        assertTrue(ite2.intersects(ite1));
        assertTrue(ite1.intersects(ite3));
        assertTrue(ite3.intersects(ite1));
        assertTrue(ite1.intersects(ite4));
        assertTrue(ite4.intersects(ite1));

        assertFalse(ite2.intersects(ite3));
        assertFalse(ite3.intersects(ite2));
    }
    
    @Test
    public void testIntersects(){
        var ite1 = new IntervalUnion.Interval(1, 5);
        var ite2 = new IntervalUnion.Interval(4, 7);
        assertTrue(ite1.intersects(ite2));
        assertTrue(ite2.intersects(ite1));
    }

    @Test
    public void testMerge(){
        var ite1 = new IntervalUnion.Interval(1, 5);
        var ite2 = new IntervalUnion.Interval(4, 7);
        var ite3 = ite1.merge(ite2);
        assertEquals(1, ite3.begin);
        assertEquals(7, ite3.end);
        ite3 = ite2.merge(ite1);
        assertEquals(1, ite3.begin);
        assertEquals(7, ite3.end);
    }

    @Test
    public void testMergeIncluded(){
        var ite1 = new IntervalUnion.Interval(1, 5);
        var ite2 = new IntervalUnion.Interval(1, 3);
        var ite3 = new IntervalUnion.Interval(3, 5);
        var ite4 = new IntervalUnion.Interval(2, 4);


        var res = ite1.merge(ite2);

        assertEquals(ite1.begin, res.begin);
        assertEquals(ite1.end, res.end);

        res = ite1.merge(ite3);
        assertEquals(ite1.begin, res.begin);
        assertEquals(ite1.end, res.end);

        res = ite1.merge(ite4);
        assertEquals(ite1.begin, res.begin);
        assertEquals(ite1.end, res.end);
    }

    @Test
    public void testUnion1(){
        // A: {[1,3], [3, 4], [5,7]}
        // B: {[1,2], [3, 4], [9, 11]}
        // O: {[1,3], [3, 4], [5,7], [9, 11]}
        var A = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 4),
            new IntervalUnion.Interval(5, 7),
        };
        var B = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 2),
            new IntervalUnion.Interval(3, 4),
            new IntervalUnion.Interval(9, 11),
        };
        var O = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 4),
            new IntervalUnion.Interval(5, 7),
            new IntervalUnion.Interval(9, 11),
        };

        var output = IntervalUnion.union(A, B);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
        // simmetric test
        output = IntervalUnion.union(B, A);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }


    }

    @Test
    public void testUnion2AllIncluded(){
        // A: {[1,1000]}
        // B: {[1,2], [3, 4], [9, 11]}
        // O: {[1,1000]}
        var A = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 1000),
        };
        var B = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 2),
            new IntervalUnion.Interval(3, 4),
            new IntervalUnion.Interval(9, 11),
        };
        var O = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 1000),
        };

        var output = IntervalUnion.union(A, B);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
        // simmetric test
        output = IntervalUnion.union(B, A);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
    }

    @Test
    public void testUnion2AllOverlapping(){
        // A: {[1,3], [3, 5], [5,7]}
        // B: {[2,4], [4, 6], [6,8]}
        // O: {[1, 8]}
        var A = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 5),
            new IntervalUnion.Interval(5, 7),
        };
        var B = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(2, 4),
            new IntervalUnion.Interval(4, 6),
            new IntervalUnion.Interval(6, 8),
        };
        var O = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 8),
        };

        var output = IntervalUnion.union(A, B);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
        // simmetric test
        output = IntervalUnion.union(B, A);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
    }

    @Test
    public void testUnionAllExclusive(){
        // A: {[1,3], [5, 7], [9, 11]}
        // B: {[3,5], [7, 9], [11, 13]}
        // O: {[1,3],[3,5],  [5, 7], [7, 9], [9, 11], [11, 13]}
        var A = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(5, 7),
            new IntervalUnion.Interval(9, 11),
        };
        var B = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(3, 5),
            new IntervalUnion.Interval(7, 9),
            new IntervalUnion.Interval(11, 13),
        };
        var O = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 5),
            new IntervalUnion.Interval(5, 7),
            new IntervalUnion.Interval(7, 9),
            new IntervalUnion.Interval(9, 11),
            new IntervalUnion.Interval(11, 13),
        };

        var output = IntervalUnion.union(A, B);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
        // simmetric test
        output = IntervalUnion.union(B, A);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
    }

    @Test
    public void testUnionAllExclusive2(){
        // A: {[1,3], [3,5], [5, 7], }
        // B: { [7, 9],[9, 11], [11, 13]}
        // O: {[1,3],[3,5],  [5, 7], [7, 9], [9, 11], [11, 13]}
        var A = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 5),
            new IntervalUnion.Interval(5, 7),
        };
        var B = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(7, 9),
            new IntervalUnion.Interval(9, 11),
            new IntervalUnion.Interval(11, 13),
        };
        var O = new IntervalUnion.Interval[]{
            new IntervalUnion.Interval(1, 3),
            new IntervalUnion.Interval(3, 5),
            new IntervalUnion.Interval(5, 7),
            new IntervalUnion.Interval(7, 9),
            new IntervalUnion.Interval(9, 11),
            new IntervalUnion.Interval(11, 13),
        };

        var output = IntervalUnion.union(A, B);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
        // simmetric test
        output = IntervalUnion.union(B, A);
        assertEquals(O.length, output.size());
        System.out.println(output.stream().map(x -> x.toString()).collect(Collectors.joining(",")));
        for (int i = 0; i < O.length; i++) {
            IntervalUnion.Interval actual = output.get(i);
            assertEquals(O[i].begin, actual.begin);
            assertEquals(O[i].end, actual.end);
        }
    }

}