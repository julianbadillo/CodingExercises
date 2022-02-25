import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by jbadillo on 1/21/2016.
 */
public class NetworkCables {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        long [][] house = new long[N][2];

        if(N <= 1){
            System.out.println(0);
            return;
        }

        for (int i = 0; i < N; i++) {
            house[i][0] = in.nextInt(); // x
            house[i][1] = in.nextInt(); // y
        }

        long total = 0;

        //get the median on Y coord
        long med = medianY(house);
        long minx = house[0][0], maxx = house[0][1];
        //calculate the total cable distance
        for (int i = 0; i < N; i++) {
            //vertical distance
            total += Math.abs(house[i][1] - med);
            if(house[i][0] < minx) minx =house[i][0];
            if(house[i][0] > maxx) maxx =house[i][0];
        }
        //horizontal cable
        total += maxx - minx;
        System.out.println(total);
    }


    static long medianY(long houses[][]){
        //sort array by second coord
        Arrays.sort(houses, new Comparator<long[]>() {
                    public int compare(long[] o1, long[] o2) {
                        if(o1[1] == o2[1])
                            return 0;
                        else if(o1[1] < o2[1])
                            return -1;
                        else
                            return 1;
                    }
                }
        );
        //System.out.println(Arrays.deepToString(houses));
        return houses[houses.length/2][1];
    }

}
