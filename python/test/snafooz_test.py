import pytest
from snafooz import Piece, is_solved, next_rotation, solve_pieces, solve_snafooz
from itertools import permutations

class TestSnafooz():
    

    @pytest.mark.parametrize("mat, up, right, down, left", [
            # 1 x 1 case
            ([[1]], [1], [1], [1], [1]), 
            # 2 x 2 case
            ([  [1, 2],
                [4, 3],], 
            [1, 2], [2, 3], [3, 4], [4, 1]), 
            # 4 x 4 case
            ([  [0, 0, 0, 1],
                [1, 0, 0, 0],
                [1, 0, 0, 0],
                [0, 0, 1, 1],], 
            [0, 0, 0, 1],
            [1, 0, 0, 1],
            [1, 1, 0, 0],
            [0, 1, 1, 0],), 
        ],)
    def test_piece_init_edges(self, mat, up, right, down, left):
        p = Piece(mat)
        assert p.up == up
        assert p.right == right
        assert p.down == down
        assert p.left == left


    @pytest.mark.parametrize(["first", "second"], [
        ([0, 0, 0, 0], [1, 1, 1, 1]),
        ([0, 1, 1, 0], [1, 0, 0, 1]),
        ([0, 1, 0, 0], [1, 1, 0, 0]),
        ([0, 1, 0, 1], [0, 1, 0, 1])
    ])
    def test_piece_edges_fit(self, first, second):
        # 4 x 4 case
        assert Piece.edges_fit(first, second)
        assert Piece.edges_fit(second, first)

    @pytest.mark.parametrize(["first", "second"], [
        ([0, 1, 0, 0], [1, 1, 1, 1]),
        ([0, 1, 0, 0], [1, 0, 0, 1]),
        ([1, 1, 0, 1], [0, 0, 1, 1]),
        ([1, 1, 0, 1], [1, 0, 1, 0])
    ])
    def test_piece_edges_fit_fail(self, first, second):
        assert Piece.edges_fit(first, second) is False
        assert Piece.edges_fit(second, first) is False

    def test_piece_fitting(self):
        # 3 x 3 case
        p1 = Piece([[0,1,0,1],
                    [0,1,1,0],
                    [0,1,1,1],
                    [1,0,1,0]])
        p2 = Piece([[0,1,0,1],
                    [0,1,1,0],
                    [0,1,1,1],
                    [1,0,1,0]])
        # fit one on top of the other
        assert Piece.edges_fit(p1.down, p2.up)
        assert Piece.edges_fit(p1.up, p2.down)
        # fit if we rotate the second one
        assert Piece.edges_fit(p1.right, p2.right)
        assert Piece.edges_fit(p1.down, p2.down)

    def test_piece_fitting_fail(self):
        # 4 x 4 case
        p1 = Piece([[0,1,0,1],
                    [0,1,1,0],
                    [0,1,1,1],
                    [1,0,1,0]])
        p2 = Piece([[0,1,0,1],
                    [0,1,1,0],
                    [0,1,1,1],
                    [1,0,1,0]])
        # dont fit by side
        assert Piece.edges_fit(p1.right, p2.left) is False
        assert Piece.edges_fit(p1.left, p2.right) is False
        # some rotations won't fit
        assert Piece.edges_fit(p1.left, p2.left) is False
        assert Piece.edges_fit(p1.down, p2.right) is False
        assert Piece.edges_fit(p1.left, p2.up) is False


    def test_flip(self):
        p1 = Piece([[0,1],
                    [0,1],])
        p1.flip()
        expected = [[1,0],
                    [1,0],]
        assert p1.mat == expected

        p2 = Piece([[0,1,0],
                    [0,1,1],
                    [1,1,0],])
        p2.flip()
        expected = [[0,1,0],
                    [1,1,0],
                    [0,1,1],]
        assert p2.mat == expected

    def test_rotate(self):
        p1 = Piece([[0,1],
                    [0,1],])
        p1.rotate()
        expected = [[0,0],
                    [1,1],]
        assert p1.mat == expected

        p2 = Piece([[0,1,0],
                    [0,1,1],
                    [1,1,0],])
        p2.rotate()
        expected = [[1,0,0],
                    [1,1,1],
                    [0,1,0],]
        assert p2.mat == expected

    def test_is_solved(self):
        # 3 x 3 easiest puzzle
        m0 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m1 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m2 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        m3 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m4 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m5 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        pieces = [m0, m1, m2, m3, m4, m5]
        assert is_solved(pieces)
    
    
    def test_is_solved2(self):

        m0 = Piece([
            [1, 1, 0, 1, 0, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 0, 1, 0, 1, 1],
        ])
        m1 = Piece([
            [0, 0, 1, 0, 0, 0],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [1, 1, 1, 0, 0, 0],
        ])
        m2 = Piece([
            [1, 1, 0, 1, 0, 0],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 0, 0, 1, 1],
        ])
        m3 = Piece([
            [0, 0, 0, 0, 0, 0],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 0, 1, 0, 0],
        ])
        m4 = Piece([
            [1, 0, 1, 1, 0, 0],
            [1, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 1],
            [0, 0, 1, 1, 1, 0],
        ])
        m5 = Piece([
            [0, 1, 0, 0, 0, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 0, 1, 0, 1, 1],
        ])
        pieces = [m0, m1, m2, m3, m4, m5]
        assert is_solved(pieces)

    def test_next_rotation(self):
        # 3 x 3 easiest puzzle 
        m0 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m1 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m2 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        m3 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m4 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m5 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        pieces =  [m0, m1, m2, m3, m4, m5]
        # do a rotation
        assert next_rotation(pieces)
        assert pieces[0].rot == 1
        # do seven rotations more until reset
        for i in range(7):
            assert next_rotation(pieces)
        assert pieces[0].rot == 0
        assert pieces[1].rot == 1
        # set final rotations
        for p in pieces:
            p.rot = 7
        assert not next_rotation(pieces)
        assert all(p.rot == 0 for p in pieces)


    def test_solve_pieces_only_permutation(self):
        # 3 x 3 easiest puzzle - solve by only permutating pieces
        m0 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m1 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m2 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        m3 = Piece([[0, 0, 0],
                    [1, 1, 1],
                    [0, 0, 0],])
        m4 = Piece([[0, 1, 0],
                    [1, 1, 1],
                    [0, 1, 0],])
        m5 = Piece([[1, 0, 1],
                    [0, 1, 0],
                    [1, 0, 1],])
        pieces =  [m1, m0, m3, m2, m5, m4]
        assert not is_solved(pieces)
        pieces = solve_pieces(pieces)
        assert is_solved(pieces)


    def test_solve_pieces_only_permutation2(self):
        # 6x6 case - solved by only permutating pieces (not rotatiing)
        m0 = Piece([
            [1, 1, 0, 1, 0, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 0, 1, 0, 1, 1],
        ])
        m1 = Piece([
            [0, 0, 1, 0, 0, 0],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [1, 1, 1, 0, 0, 0],
        ])
        m2 = Piece([
            [1, 1, 0, 1, 0, 0],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 0, 0, 1, 1],
        ])
        m3 = Piece([
            [0, 0, 0, 0, 0, 0],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 0, 1, 0, 0],
        ])
        m4 = Piece([
            [1, 0, 1, 1, 0, 0],
            [1, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [0, 1, 1, 1, 1, 1],
            [0, 0, 1, 1, 1, 0],
        ])
        m5 = Piece([
            [0, 1, 0, 0, 0, 1],
            [1, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 1],
            [0, 1, 1, 1, 1, 0],
            [1, 1, 1, 1, 1, 1],
            [0, 0, 1, 0, 1, 1],
        ])
        pieces =  [m1, m0, m3, m2, m5, m4]
        # not solved yet
        assert not is_solved(pieces)
        pieces = solve_pieces(pieces)
        assert is_solved(pieces)

    def test_example_puzzle(self):
        pieces = [[[0, 0, 1, 1, 0, 0], 
                    [0, 1, 1, 1, 1, 1], 
                    [1, 1, 1, 1, 1, 0], 
                    [0, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 1], 
                    [1, 0, 1, 0, 1, 1]], 
                  
                    [[0, 1, 0, 0, 1, 1], 
                    [1, 1, 1, 1, 1, 1], 
                    [0, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 0], 
                    [0, 1, 1, 1, 1, 1], 
                    [0, 0, 1, 1, 0, 1]], 
                  
                    [[0, 0, 1, 1, 0, 1], 
                    [1, 1, 1, 1, 1, 1], 
                    [0, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 0], 
                    [0, 1, 1, 1, 1, 1], 
                    [0, 0, 1, 1, 0, 0]], 
                  
                    [[0, 0, 1, 1, 0, 0], 
                    [0, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 1], 
                    [1, 1, 1, 1, 1, 1], 
                    [0, 1, 1, 1, 1, 0], 
                    [0, 1, 0, 0, 1, 0]], 
                  
                    [[0, 0, 1, 1, 0, 1], 
                    [1, 1, 1, 1, 1, 1], 
                    [0, 1, 1, 1, 1, 0], 
                    [0, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 1], 
                    [1, 1, 0, 0, 1, 1]], 
                  
                    [[0, 0, 1, 1, 0, 0], 
                    [0, 1, 1, 1, 1, 1], 
                    [1, 1, 1, 1, 1, 0], 
                    [1, 1, 1, 1, 1, 0], 
                    [0, 1, 1, 1, 1, 1],
                    [0, 1, 0, 0, 1, 0]]]

        result = solve_snafooz(pieces)
        # assert solution    
        assert len(result) > 0
        pieces_obj = [Piece(m) for m in result]
        assert is_solved(pieces_obj)

        
    def test_example_puzzle2(self):
        pieces =[[[0, 0, 1, 1, 0, 0],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [0, 0, 1, 1, 0, 0]], 
                  
                    [[1, 1, 0, 0, 1, 1],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [1, 1, 0, 1, 1, 1]], 
                  
                    [[0, 0, 1, 0, 0, 0],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 0, 0, 1, 0]], 
                  
                    [[0, 0, 1, 1, 0, 0],
                    [1, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [0, 0, 1, 1, 0, 0]], 
                  
                    [[0, 0, 1, 1, 0, 0],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 0, 0, 1, 1]], 
                  
                    [[0, 0, 1, 1, 0, 1],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [0, 1, 0, 0, 1, 1]]]
        result = solve_snafooz(pieces)
        # assert solution    
        assert len(result) > 0
        pieces_obj = [Piece(m) for m in result]
        assert is_solved(pieces_obj)
        
    def test_example_puzzle3(self):
        pieces = [[[0, 0, 1, 1, 0, 0],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 0, 0, 1, 0]],

                    [[0, 1, 0, 1, 0, 0],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [1, 1, 0, 0, 1, 0]],

                    [[1, 1, 0, 0, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [0, 0, 1, 1, 0, 0]],

                    [[0, 0, 1, 1, 0, 0],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [0, 1, 0, 0, 1, 1]],

                    [[0, 0, 1, 1, 0, 0],
                    [0, 1, 1, 1, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [0, 0, 1, 1, 0, 0]],

                    [[1, 1, 0, 0, 1, 1],
                    [1, 1, 1, 1, 1, 0],
                    [0, 1, 1, 1, 1, 1],
                    [0, 1, 1, 1, 1, 0],
                    [1, 1, 1, 1, 1, 1],
                    [0, 1, 0, 1, 0, 1]]]        
        result = solve_snafooz(pieces)
        # assert solution    
        assert len(result) > 0
        pieces_obj = [Piece(m) for m in result]
        assert is_solved(pieces_obj)
        
