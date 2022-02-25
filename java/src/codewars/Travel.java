import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Travel {
    public static String travel(String r, String zipcode) {
        String [] add = r.split(",");
        
        LinkedList<String> selec = new LinkedList<>();
        LinkedList<String> numbers = new LinkedList<>();
        Pattern p = Pattern.compile("^(\\d+) ");
        if(!zipcode.equals(""))
			for (int i = 0; i < add.length; i++) {
				if (add[i].endsWith(zipcode)) {
					Matcher m = p.matcher(add[i]);
					m.find();
					String number = m.group(1);
					numbers.add(number);
					selec.add(add[i].substring(number.length() + 1, add[i].length() - zipcode.length() - 1));
				}
			}
        return zipcode+":"+selec.stream().collect(Collectors.joining(","))
        		+"/"+numbers.stream().collect(Collectors.joining(","));
        
    }
}