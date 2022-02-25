import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by jbadillo on 2/3/2016.
 */
public class Labyrinth{

}

class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int R = in.nextInt(); // number of rows.
        int C = in.nextInt(); // number of columns.
        int A = in.nextInt(); // number of rounds between the time the alarm countdown is activated and the time the alarm goes off.

        Kirk kirk = new Kirk(R, C, A);
        while (true) {
            int KR = in.nextInt(); // row where Kirk is located.
            int KC = in.nextInt(); // column where Kirk is located.
            if (kirk.status == Kirk.KirkStatus.CREATED)
                kirk.setStartR(KR, KC);

            char[][] mat = new char[R][];

            for (int i = 0; i < R; i++)
                mat[i] = in.next().toCharArray(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).

            kirk.updateMat(mat, KR, KC);

            System.out.println(kirk.nextMove()); // Kirk's next move (UP DOWN LEFT or RIGHT).

        }
    }
}



