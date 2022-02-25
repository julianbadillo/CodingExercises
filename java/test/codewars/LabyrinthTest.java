import java.io.File;
import java.util.Scanner;

/**
 * Created by jbadillo on 3/30/2016.
 */
public class LabyrinthTest {

    public static void main(String a[])throws Exception{

        Scanner in = new Scanner(new File("data/lab1.txt"));
        int R = in.nextInt(), C = in.nextInt(), startR = in.nextInt(), startC = in.nextInt();
        in.nextLine();

        char[][] mat = new char[R][];
        for (int i = 0; i < R; i++)
            mat[i] = in.next().toCharArray(); // C of the characters in '#.TC?' (i.e. one line of the ASCII maze).

        Kirk k = new Kirk(R,C,100);
        k.updateMat(mat,13,28);
        k.bfs(startR,startC);

        k.print_mat();
        k.print_dist();
        k.print_moving();
    }
}
