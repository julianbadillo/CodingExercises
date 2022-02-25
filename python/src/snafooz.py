from itertools import permutations


class Piece:
    def __init__(self, mat):
        self.n = len(mat)
        # clockwise
        self.mat = mat
        self.rot = 0
        self.__set_edges()

    def __set_edges(self):
        self.up = [i for i in self.mat[0]]  # first row
        self.right = [self.mat[i][-1] for i in range(self.n)]  # last column
        self.down = [
            self.mat[-1][i] for i in range(self.n - 1, -1, -1)
        ]  # last row - inverted
        self.left = [
            self.mat[i][0] for i in range(self.n - 1, -1, -1)
        ]  # first column - inverted
        self.edges = {
            "up": self.up,
            "right": self.right,
            "down": self.down,
            "left": self.left,
        }

    def flip(self):
        """Flips piece horizontally (reverse rows)"""
        self.mat = [list(reversed(row)) for row in self.mat]
        self.__set_edges()

    def rotate(self):
        """Rotates nine degrees clockwise"""
        old_mat = self.mat
        self.mat = []
        for i in range(self.n):
            self.mat.append([])
            for j in range(self.n):
                self.mat[i].append(old_mat[self.n - j - 1][i])
        self.__set_edges()

    def print(self):
        for name, edge in self.edges.items():
            print(f"{name}: {edge}")

    @staticmethod
    def edges_fit(edge1, edge2):
        """
        Compare two edges of two pieces, true if
        they fit (reflected)
        """
        n = len(edge1)
        for i in range(n):
            j = n - i - 1
            # corners
            if i == 0 or i == n - 1:
                if edge1[i] + edge2[j] > 1:
                    return False
            # sides
            elif edge1[i] + edge2[j] != 1:
                return False
        return True


fit_map = {
    (0, 1): ("left", "up"),
    (0, 2): ("down", "up"),
    (0, 3): ("right", "up"),
    (0, 5): ("up", "down"),
    (1, 2): ("right", "left"),
    (1, 4): ("down", "left"),
    (1, 5): ("left", "left"),
    (2, 3): ("right", "left"),
    (2, 4): ("down", "up"),
    (3, 4): ("down", "right"),
    (3, 5): ("right", "right"),
    (4, 5): ("down", "up"),
}


def is_solved(pieces):
    """
    If an array of pieces is solved
    :pieces:
    """
    # test all the fitting map of edges
    for pos, edges in fit_map.items():
        # compare edge on the first piece vs edge on the second
        edge1 = pieces[pos[0]].edges[edges[0]]
        edge2 = pieces[pos[1]].edges[edges[1]]
        if not Piece.edges_fit(edge1, edge2):
            print(f"{pos[0]}->{edges[0]} doesn't fit {pos[1]}->{edges[1]}")
            return False
    return True


def next_rotation(pieces):
    i = 0
    while i < len(pieces):
        # mark rotation
        pieces[i].rot += 1
        pieces[i].rotate()
        # if all the way around, flip it
        if pieces[i].rot == 4:
            pieces[i].flip()
            return True
        # if flipped and rotated all way around -> reset -> rotate next
        elif pieces[i].rot == 8:
            pieces[i].rot = 0
            pieces[i].flip()
            i += 1
        else:
            return True
    return False


def solve_pieces(pieces):
    # 6! = 120 * 4^6 rotations
    pieces_copy = list()
    for order in permutations(range(len(pieces))):
        # make a permutation array
        for i in order:
            pieces_copy.append(pieces[i])

        # try all rotations
        while True:
            # if is solve - finish
            if is_solved(pieces_copy):
                return pieces_copy
            # try next rotation until finished
            if not next_rotation(pieces_copy):
                break

        # reset
        pieces_copy.clear()
    return pieces_copy


def solve_snafooz(pieces):  # pieces = int[6][6][6]

    # make an array of pieces
    pieces_arr = []
    for m in pieces:
        p = Piece(m)
        p.print()
        pieces_arr.append(p)
    # solve
    pieces_arr = solve_pieces(pieces_arr)
    # transform back to matrix
    return [p.mat for p in pieces_arr]
