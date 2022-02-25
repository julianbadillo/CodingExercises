import java.util.regex.Pattern;

public class DivisibleByFive {
  public static Pattern pattern() {
	  return Pattern.compile("^(?=\\s*\\S)0*((111|1(10)*0|1(10)*11)(01*000|01*01)*10*)*$");
  }
}