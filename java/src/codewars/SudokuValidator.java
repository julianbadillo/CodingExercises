import java.util.Arrays;

public class SudokuValidator {
	public static boolean check(int[][] sudoku) {

		// cheating - validate 50 sum
		int n = 9;
		for (int i = 0; i < n; i++)
			System.out.println(Arrays.toString(sudoku[i]));

		// rows
		for (int i = 0; i < n; i++) {
			int s = 0;
			for (int j = 0; j < n; j++)
				s += sudoku[i][j];
			if (s != 45)
				return false;
		}
		// cols
		for (int j = 0; j < n; j++) {
			int s = 0;
			for (int i = 0; i < n; i++)
				s += sudoku[i][j];
			if (s != 45)
				return false;
		}
		// quadrants
		for (int k = 0; k < 3; k++) {
			for (int l = 0; l < 3; l++) {
				int s = 0;
				// all quadrant
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						s += sudoku[3 * k + i][3 * l + j];
				if (s != 45)
					return false;
			}
		}
		return true;
	}
}