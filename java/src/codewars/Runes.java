
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runes {

    static final String EXP = "(-?[0-9\\?]+)([\\+\\-\\*])(-?[0-9\\?]+)\\=(-?[0-9\\?]+)";
    static final Pattern pattern = Pattern.compile(EXP);

    public static int solveExpression(final String expression ) {
        boolean used[] = new boolean[10];
        // mark which digits are used
        for (int i = 0; i < expression.length(); i++) {
            if('0' <= expression.charAt(i) && expression.charAt(i) <= '9')
                used[expression.charAt(i)-'0'] = true;
        }
        // split operands and operation
        Matcher m = pattern.matcher(expression);
        if(!m.matches())
            return -1;

        String n1s = m.group(1);
        String op = m.group(2);
        String n2s = m.group(3);
        String res = m.group(4);

        for(int i = 0; i < 10; i++){
            if(used[i])
                continue;
            // validate leading zeros
            if(i == 0)
            {
                if(n1s.length() > 1 && n1s.charAt(0) == '?')
                    continue;
                if(n2s.length() > 1 && n2s.charAt(0) == '?')
                    continue;
                if(res.length() > 1 && res.charAt(0) == '?')
                    continue;
            }

            // replace rune
            int n1 = Integer.parseInt(n1s.replace('?', (char)('0'+i)));
            int n2 = Integer.parseInt(n2s.replace('?', (char)('0'+i)));
            int r = Integer.parseInt(res.replace('?', (char)('0'+i)));

            if(op.equals("+") && n1 + n2 == r)
                return i;
            else if(op.equals("-") && n1 - n2 == r)
                return i;
            else if(op.equals("*") && n1 * n2 == r)
                return i;
        }
        return -1;
    }

    public static String[] parse(String line){
        Matcher m = pattern.matcher(line);
        if(!m.matches())
            return new String[0];
        String[] args = new String[4];
        args[0] = m.group(1);
        args[1] = m.group(2);
        args[2] = m.group(3);
        args[3] = m.group(4);
        return args;
    }

}
