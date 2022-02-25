import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinaryRegexp {
    /**
     * From the DFA reduction
     * @return
     */
    public static String multipleOf7() {
        String a = "01*0";
        String b = "(?:0|11)";
        String c = "(?:a0)".replaceAll("a", a);
        String d = "(?:a1)".replaceAll("a", a);
        String e = "10";
        String f = "1b".replaceAll("b", b);
        String g = "0b".replaceAll("b", b);
        String h = "(?:1|0e)".replaceAll("e", e);
        String i = "1e".replaceAll("e", e);
        String j = "1g".replaceAll("g", g);
        String k = "1h".replaceAll("h", h);
        String l = "(?:i|0h)".replaceAll("i", i)
                            .replaceAll("h", h);
        String m = "(?:f|0g)".replaceAll("f", f)
                .replaceAll("g", g);
        String n = "(?:k|jm*l)".replaceAll("k", k)
                .replaceAll("j", j)
                .replaceAll("m", m)
                .replaceAll("l", l);
        String p = "(?:c|dm*l)".replaceAll("c", c)
                .replaceAll("d", d)
                .replaceAll("m", m)
                .replaceAll("l", l);
        return "(?:0|np*1)+".replaceAll("n", n)
                .replaceAll("p", p);

    }

    public static String multipleOf2(){
        return "(0|1+0)+";
    }

    public static String multipleOf3(){
        return "(0|1(01*0)*1)+";
    }
    public static String multipleOf4(){
        String a = "11*0";
        String b = "(1(0|a))".replaceAll("a", a);
        return "(0|bb*0)+".replaceAll("b", b);
    }
}