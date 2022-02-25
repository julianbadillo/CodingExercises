import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Primes {

    public static IntStream stream() {
        return IntStream.generate(new ProbablePrimeSupplier());
    }

    private static class ProbablePrimeSupplier implements  IntSupplier{

        int x = 11;
        int i = 0;
        @Override
        public int getAsInt() {
            if(i == 0)
            {
                i++;
                return 2;
            }
            else if(i == 1)
            {
                i++;
                return 3;
            }
            else if(i == 2)
            {
                i++;
                return 5;
            }
            else if(i == 3)
            {
                i++;
                return 7;
            }
            else if(i == 4)
            {
                i++;
                return 11;
            }
            x += 2;
            while(!BigInteger.valueOf(x).isProbablePrime(50))
                x += 2;
            return x;
        }
    }

    private static class PrimeSupplier implements IntSupplier{

        private LinkedList<Integer> elements;
        private int pos;

        private PrimeSupplier(){
            elements = new LinkedList<Integer>(Arrays.asList(2,3,5,7,11,13,17,19,23,29));
        }

        private void addOneMore(){
            int n = elements.getLast() + 2;
            while(true) {
                int r = (int)Math.sqrt(n);
                boolean divides = false;
                for(int i: elements){
                    if(n % i == 0){
                        divides = true;
                        break;
                    }
                    if(i > r)
                        break;
                }
                if (!divides) {
                    elements.add(n);
                    break;
                }
                n += 2;
            }
        }

        @Override
        public int getAsInt() {
            while(pos >= elements.size())
                addOneMore();
            var r = elements.get(pos++);
            return r;
        }
    }


}
