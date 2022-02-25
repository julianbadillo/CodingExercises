public class QueensAndKings {

	static class King {
		int[] list;
		int currQueen = -1;
		int qsize = 0;
		boolean[] props;

		public King(int[] list) {
			this.list = list;
			props = new boolean[list.length];
		}

		void pickFavorite() {
			if ((qsize > 0 && currQueen == -1) || qsize > 1) {
				int idx;
				// pick the first on the list that has been offered
				for (idx = 0; idx < list.length && !props[list[idx]]; idx++)
					;
				currQueen = list[idx];
			}
		}
	}

	static class Queen {
		boolean matched = false;
		int kingIdx = 0;

		public Queen() {
		}
	}

	public static int[] bestMatch(int[][] queens, int[][] kings) {
		int n = queens.length;
		Queen[] qs = new Queen[n];
		for (int i = 0; i < n; i++)
			qs[i] = new Queen();

		King[] ks = new King[n];
		for (int i = 0; i < n; i++)
			ks[i] = new King(kings[i]);

		boolean newMatch = true;
		while (newMatch) {
			newMatch = false;
			// match each unmatched queen with its favorite king up to now
			for (int q = 0; q < n; q++) {
				Queen qu = qs[q];
				if (!qu.matched) {
					King ki = ks[queens[q][qu.kingIdx]];
					
					// add proposals
					ki.props[q] = true;
					ki.qsize++;
				}
			}

			// kings pick their favorite
			for (int k = 0; k < n; k++) {
				King ki = ks[k];
				ki.pickFavorite();
				if (ki.qsize > 0) {
					// mark queen as matched
					Queen qu = qs[ki.currQueen];
					qu.matched = true;
					if (ki.qsize > 1) {
						// all other queens as not matched
						for (int q = 0; q < n; q++) {
							if (q != ki.currQueen && ki.props[q]) {
								ki.props[q] = false;
								qs[q].matched = false;
								// increase counter
								qs[q].kingIdx++;
								newMatch = true;
							}
						}
						ki.qsize = 1;
					}
				}
			}
			
		}
		// match for each queen
		int[] match = new int[n];
		for (int q = 0; q < n; q++)
			match[q] = queens[q][qs[q].kingIdx];
		return match;
	}

}
