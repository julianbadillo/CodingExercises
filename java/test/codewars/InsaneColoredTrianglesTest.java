import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class InsaneColoredTrianglesTest {

    @Test
    public void examples() {
        assertEquals('B', InsaneColoredTriangles.triangle("B"));
        assertEquals('R', InsaneColoredTriangles.triangle("R"));
        assertEquals('G', InsaneColoredTriangles.triangle("G"));
        assertEquals('R', InsaneColoredTriangles.triangle("GB"));
        assertEquals('B', InsaneColoredTriangles.triangle("GR"));
        assertEquals('G', InsaneColoredTriangles.triangle("RB"));
        assertEquals('R', InsaneColoredTriangles.triangle("RRR"));
        assertEquals('B', InsaneColoredTriangles.triangle("RGBG"));
        assertEquals('G', InsaneColoredTriangles.triangle("RBRGBRB"));
        assertEquals('G', InsaneColoredTriangles.triangle("RBRGBRBGGRRRBGBBBGG"));
        assertEquals(InsaneColoredTriangles.triangle("RBRGBRBGGRRRBGBBBGG"), InsaneColoredTriangles.triangle("RBRGBRBGGRBGBGG"));


        StringBuilder bf = new StringBuilder();
        for (int i = 0; i <  50000; i++)
            bf.append("R");
        for (int i = 0; i < 100; i++)
            assertEquals('R', InsaneColoredTriangles.triangle(bf.toString()));



    }


    @Test
    public void testExpMod(){
        assertEquals(8, InsaneColoredTriangles.expMod(2,3, 10));
        assertEquals(5, InsaneColoredTriangles.expMod(5,19, 10));
        assertEquals(6, InsaneColoredTriangles.expMod(6,19, 10));
    }

    @Test
    public void testChooseMod(){
        assertEquals(1, InsaneColoredTriangles.chooseModulo(6, 0, 50));
        assertEquals(6, InsaneColoredTriangles.chooseModulo(6, 1, 50));
        assertEquals(15, InsaneColoredTriangles.chooseModulo(6, 2, 50));
        assertEquals(20, InsaneColoredTriangles.chooseModulo(6, 3, 50));
        assertEquals(15, InsaneColoredTriangles.chooseModulo(6, 4, 50));
        assertEquals(6, InsaneColoredTriangles.chooseModulo(6, 5, 50));
        assertEquals(1, InsaneColoredTriangles.chooseModulo(6, 6, 50));


        assertEquals(1, InsaneColoredTriangles.chooseModulo(5, 0, 50));
        assertEquals(5, InsaneColoredTriangles.chooseModulo(5, 1, 50));
        assertEquals(10, InsaneColoredTriangles.chooseModulo(5, 2, 50));
        assertEquals(10, InsaneColoredTriangles.chooseModulo(5, 3, 50));
        assertEquals(5, InsaneColoredTriangles.chooseModulo(5, 4, 50));
        assertEquals(1, InsaneColoredTriangles.chooseModulo(5, 5, 50));
    }

    @Test
    public void testPrintout(){
        String s = "BRGGGRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGB";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
    }


    @Test
    public void testCompress(){
        String s = "RRRRRG";
        assertEquals("RG", InsaneColoredTriangles.compress(s));
        s = "RRGGGGGGGGBB";
        assertEquals("RRGGBB", InsaneColoredTriangles.compress(s));
        s = "RGBRGBRGB";
        assertEquals(s, InsaneColoredTriangles.compress(s));
        s = "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGBRBBBRGBBGBGGBRGGRGBBBGBRBGGBGBBGBBRRRRBRRGBGRBBBBBBBBRBGGBR";
        String compressed = "GBBRBRRGGBBGGRGBRRGRRGGRGRRBBRGBGBRBGBRBRGBBGBGGBRGGRGBGBRBGGBGBBGBBRRBRRGBGRBBRBGGBR";
        assertEquals(compressed, InsaneColoredTriangles.compress(s));

        s = "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGB";
        compressed = "GBBRBRRGGBBGGRGBRRGRRGGRGRRBBRGBGBRBGB";
        assertEquals(compressed, InsaneColoredTriangles.compress(s));
    }

    @Test
    public void testCompression(){
        String s = "RRRRRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));

        s = "RRRRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));

        s = "BRRRRRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "BRRRRRRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));

        s = "RGGGGGGGGB";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "RRGGGGGGGGBB";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "GGGGRRR";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "GGGGRRRR";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "GGGRRRR";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "BGBGBBBRRRRRRRBBBGBBGGBRRRBRBBGBBRBRGGRBRRRRBGBBGRGGBBGGBGRRGBBBGGGBGGBBRRBBBBRBGGRGGGBGBGBRBBGBBRRG";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));

        s = "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGB";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
        s = "RBBBRGBBGBGGBRGGRGBBBGBRBGGBGBBGBBRRRRBRRGBGRBBBBBBBBRBGGBR";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));

        s = "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGBRBBBRGBBGBGGBRGGRGBBBGBRBGGBGBBGBBRRRRBRRGBGRBBBBBBBBRBGGBR";
        assertEquals(InsaneColoredTriangles.triangleNoComp(s), InsaneColoredTriangles.triangle(s));
    }

    @Test
    public void randomMedium(){
        String s = "BGBGBBBRRRRRRRBBBGBBGGBRRRBRBBGBBRBRGGRBRRRRBGBBGRGGBBGGBGRRGBBBGGGBGGBBRRBBBBRBGGRGGGBGBGBRBBGBBRRG" +
                "GBBRBBBRRGGBBGGRGBRRRRGRRGGGGRGRRBBRGBGBRBGBRBBBRGBBGBGGBRGGRGBBBGBRBGGBGBBGBBRRRRBRRGBGRBBBBBBBBRBGGBR" +
                "BGRGRGGRBRRBRBGBRRGGRRRRBBRGGGBBRBBRRGRRBRBGGRGBBBRGBGRRRBRGGGBRGRBBGBGBBBGBBGRBBBRRRBBBRRRGBRGGBRRGRRB" +
                "BRRBRRGBRRGRBGBRRGBBBBGBGGGGBBGRGRBRRGBBRRBGGRGGGBGGGBBRGBBRBBGRBGRBRBGGBRBGRGGBGRGBBGGRGBGBGRBGBGGBBBG" +
                "GBRBGGRRRBBBGGBGGGRRBGRGGBRRGGGBRGRRRGGBBGRRGRBRGGRRRGRRRRGBRGGGRGBRGBBBRBBRBBGBRGBGGRBRBGRBGGGGGGGRRRR" +
                "GBRRRBGGRRBRGGBGRGBGRBBGBGRRRGBBRBGBGRRGRGGBRRGGBRBBBRGGRGGGBGRRBRGGBGGRRBGRRGRRGRBGGBBRGGBRGBGBRGRRRBR" +
                "BGRBRRGRRGGBRBRBBRBRGGGBRGRGBBBRBGRRBBGGBRBRGBBBGRRRGGRBGBGBGBRGGRGBGBGRRRBRRBRBBBRRBBBRGRBGGGGBBBGGRGG" +
                "BRRRRBGBGBBBBRBRGRGRBBBRBBBBBBGRGGBGBRRGGBBGBBRRRBGBRGRBBGBGRRBGGBBBBRRBGGBRGBGRBGRGRGGGRRBGRGBBGBBBGRR" +
                "RRBBRGGRBBRRBGGRBRRBRBGBRRRRGRGGRRGGBBRRGRRRRBGGGRRGGGBGGGGGRGRGGBRGBRBBRGRRGRGRBRRRGBBBRBBBBBRBBRGGGRG" +
                "BBGBGBRRGGRRBRGBGRRRRBRBRBBGGRGRRGBGRRRGBBBRBGRBGGBBGGRBGBGBGBGBRGRBRBGBGBBBGGBRRGGBGGRBRRGBRRBRGBRBRRG" +
                "GRGRRRRBBGBRRRRRGGGBGBGBGBRGGGGBRGBRBGRGRRRBRGRBBRRRRRGRRBRGGGGGGRGBBGBRRBGBGGBGBGGGBGGGGBRRGRGBRRRGGGR" +
                "RBBBGRBBBGGGBGRBGBRRBGRBBRBRBBBBBBGRGBRGRBRRGBGRRGBBRBRRBBRGBBGBRGRGRBBGGBBBBRRGRRGBBRBRBRRGBRGBGGBRBRR" +
                "RRBGGRRGGBGGRRGGGGGBRRRGBGGRBBRBRGBRRRGBBBBGGBBRRGGGGRRGRBBRBRBRRBBBRBRGBRRGGBBGGBBGBRBRRRGRRBBRBGGBRBR" +
                "RBBRRBRBRGBGGRBRRRBRRGGGRRGBBBBGBGRGRBGBRRGRRBGRBRBGRGGGBRRBRRGGRRRRGGBGBBRGBRRBRRBBBGRBBGGGBGGBGRRBGBB" +
                "BRRGGRBBGGGBGBRBBBRGGBRBRBRGGGRGGRRGGBGGRGBBBGRGBBRGGRGBBRGRBBBBBGBRGRBRRBRBRBBBRGGRRRRBRRGRGBGGRRBGBGG" +
                "RGGBRGBRGGGRRBBGRRBBRBBRGGRRGBGGGBGRRRBGRGBRGRRBBRBBBRRRBRRRGBBGGGGGGRBRGGGRBGBGRRGRGRGGRBBGBRRRBRRBRRG" +
                "GRRGRGRGBRGRRBRGGBRBRBRRRBBRGRRBGGRBBRRGGGGRBGBBBRBRRBBBRBBBBGGRRBGBBRBGGBRRRRRBGGRRBGRGBGBRRGBBBRRGGBG" +
                "RBGBGRRBBGRBRGBGGRRGGGRBGBGGBRGGGRBBGRRBBGGRGGGRRBRBRRGGGRRBGBRBGRGRRGGRGBRGRGGBBBGGBBGRBGRBRBBBBBGRRBB" +
                "GBBBBRBGBGGGGRRRGBRRGBBRGRGRRRBGGGRGRBGRRGBBGBBBGGBRBRGBGGRGRGRGBGGBGBBGRBGBGGGBBGGGBGBBGBBGRBRBGGGBGRB" +
                "RGBRGRBGRBRGGRBBGBGBBGRGBBGRBRBGBBRGGRRRBGRRBGRRRRGBRGGGBBRBRGBBBGBBRRBGBGRGGRRRGRBBRGGRGBGBBBGBGBGBBRG" +
                "BRRGBRRGGBBBGBGGRGRGGBRRBRRRGBBBGRRBGBBRRBBGBBBGGGBRBGRRGRBBGRBRRBRRRGGGRGGRRBGGBBGRBRGBGBBGGBBGBRRRBGB" +
                "RRRRBRBRRRGBBGRGGRRBRBBRRGBGBRBRRBGGGGGGRRRRGRBGBGGBBRRRGGRRGBBRBBBBGBRBBBRRBRBGRRRRBRBGRGRGRGBRRRGBRGR" +
                "BGBBRGGGRRBRGBRRBBBRGGRBGGRRGBGRBGBBGRGGBGBRRRRGBGRGBRGGBGGGBRRBBBRGGBRBRGRBBGGRGBBBBBBGBRGGGBBGGRRGGGR" +
                "BRRBBGRGRGBBBRRRRGBRBBRGRRRRGGBGBGGRBRGRBGRRRBRGGBRRRRRBBRGBBRGGRRGRGRBBGGBBGBGBGGBRRRRBBBGBBGGBRRBBBGB" +
                "GRRRBGRGGRBGGBRBBGBGRBRRBBGBRRBGRBGRRGBGBRBBBRBRGBGGRRGGBBRRGBRRGBRRBBRBGBBRRGGBRBRGRGRBRRRBGGRRRGBBGRG" +
                "RBGBGRGBRBRRGBBBGBGRGRRRRBBGRRBGBBRRRBGBBGGGRGBGRGRBGRGBGGBRBBRRGRBRBRRRGRBRBBRGRGBRGRGBBRBGBRBRRRGRGBR" +
                "RBBGRRBGBRBGRGRBRGBBBRGRBGBRGRGRGRGRRGBBRBGBBRBBRBBBGRRGGRGGGRBBBBBBGGBRRBBBBGGGRGGGGBBRBGBBGBBGGRGGGBR" +
                "RRGGBRGGGRBGBRGRGGGBRGRRRRBRBRGRRBBRRBBGBGGBBBRRBBGRRRRRGBGGBBGBGRGRGGRGGRRGBGBBRBRGGGBRRBRRRRRGBBBRGRG" +
                "BBBBRBBRBBBGRRGBGGBRRGRGRRGRBRBRRRRBRGBBGGRBGBGGGGGGBRGGRRBBRRRGRBBRBRGRBRRRGRGGRBGRRBGGRBBGRBGGBRGBRGG" +
                "BGBGBBRGBRGGBRBBGRGRBRBGGRBGRBGRGRRBBRBGGRRGBRRRGGBRRBRGBBRBRRGRRRBGGRRBBGBBGGRGRRRBRGBBBGRBGBRBRRGGRGR" +
                "GBGGBGGRGGRRGRBRGGBBBRBBGRBRRRRBGRBGBBRBRBGGBGGRRRBRGGRRRRRGRGBRBBGGGRRRRRRRBRRBGBGRRGGBBRBRRRRBGRGBGGB" +
                "RGRRGGGRBRBGRRGBBGBBBRRBGBRBBBGGBRRRBGBRBRRGBRRGGBRBGRGGGRRGBBBGGGBRBGBRBBGRBGRGBGGRGGBGRRRGBBBRBBGRGBG" +
                "BGRGRGRGGRRRBRBRBGGBGGRRGBGRRGBBGRGRRBGRBBBGGBRBGRGRGGRGRRRGBBBRGGBBGRBRBBRGRRRRRRRBRGBBRGRBRGRBRRGBBBG" +
                "BRGGBBRGRRRRGRRRGRGRBBGRBBGGBGGBGGRGGBRRGRRGGRBGRBBBRRRRBGGGBBBRGBGRGRBRBRRRRRBGBBGRBBGRGBBRRRRGGBRBBRG" +
                "BRRRRGGBBBRRRGGR";
        assertEquals('G', InsaneColoredTriangles.triangleNoComp(s));
        assertEquals('G', InsaneColoredTriangles.triangle(s));
    }

    @Test
    public void testUnequalCombinatoriesSum(){
        assertEquals('B', InsaneColoredTriangles.triangle("B"));
        assertEquals('R', InsaneColoredTriangles.triangle("R"));
        assertEquals('G', InsaneColoredTriangles.triangle("G"));
        assertEquals('R', InsaneColoredTriangles.triangle("GB"));
        assertEquals('B', InsaneColoredTriangles.triangle("GR"));
        assertEquals('G', InsaneColoredTriangles.triangle("RB"));
        assertEquals('G', InsaneColoredTriangles.triangle("RGB"));
        assertEquals('B', InsaneColoredTriangles.triangle("RBG"));
        assertEquals('R', InsaneColoredTriangles.triangle("GRB"));

        assertEquals('R', InsaneColoredTriangles.triangle("GRB"));
        assertEquals('R', InsaneColoredTriangles.triangle("RGBR"));
        assertEquals('G', InsaneColoredTriangles.triangle("GBRG"));
        assertEquals('R', InsaneColoredTriangles.triangle("RBGR"));

        assertEquals('R', InsaneColoredTriangles.triangle("RGBRGBR"));
        assertEquals('G', InsaneColoredTriangles.triangle("GRBRGBR"));

    }
}