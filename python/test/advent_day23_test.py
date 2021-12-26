from advent_day23 import *

    
def test_is_final():
    pods = [2, 3, 5, 6, 8, 9, 11, 12] 
    assert is_final(pods)


def test_is_valid_move():

    # #############
    # #...........#
    # ###B#C#B#D###
    #   #A#D#C#A#
    #   #########
    # #############
    # #01.4.7.0.34#
    # ###2#5#8#1###
    #   #3#6#9#2#
    #   #########
    #      [A   A  B  B  C  C  D   D ]
    pods = [3, 12, 2, 8, 5, 9, 6, 11] 
    assert not is_valid_move(0, 2, pods)
    assert is_valid_move(2, 1, pods)
    assert is_valid_move(2, 4, pods)
    assert not is_valid_move(6, 5, pods)

    # #############
    # #...B.......#
    # ###B#C#.#D###
    #   #A#D#C#A#
    #   #########
    #       A   A  B  B  C  C  D  D 
    pods = [3, 12, 2, 4, 5, 9, 6, 11]
    assert not is_valid_move(3, 2, pods)
    assert not is_valid_move(3, 5, pods)
    assert is_valid_move(3, 1, pods)
    assert is_valid_move(3, 7, pods)
    assert not is_valid_move(3, 8, pods)

def test_explore_test():
    # #############
    # #...........#
    # ###B#C#B#D###
    #   #A#D#C#A#
    #   #########
    # #############
    # #01.4.7.0.34#
    # ###2#5#8#1###
    #   #3#6#9#2#
    #   #########
    #      [A   A  B  B  C  C  D   D ]
    pods = [3, 12, 2, 8, 5, 9, 6, 11] 
    r = explore(pods)
    assert r == 12521

def test_explore_f():
    # #############
    # #...........#
    # ###B#D#C#A###
    #   #C#D#B#A#
    #   #########
    # #############
    # #01.4.7.0.34#
    # ###2#5#8#1###
    #   #3#6#9#2#
    #   #########
    #      [0   1  2  3  4  5  6   7]
    #      [A   A  B  B  C  C  D   D ]
    pods = [11, 12, 2, 9, 3, 8, 5, 6] 
    r = explore(pods)
    print("Min energy = ", r)



def test_score1():
    #      [0   1  2  3  4  5  6   7]
    #      [A   A  B  B  C  C  D   D ]
    pods = [3, 12, 2, 8, 5, 9, 6, 11] 
    steps = [(3, 7), #B
            (3, 4),  
            (4, 7),  #C
            (4, 8),
            (6, 5), #D
            (6, 7),
            (3, 5),# B
            (3, 6),
            (2, 4), # other B
            (2, 5),
            (7,10), #D
            (1, 11),#A
            (1, 13),
            (7, 11), # both D's
            (7, 12),
            (6, 10),
            (6, 11),
            (1,10), # final A
            (1,7),
            (1,4),
            (1,2)]
    total = score(pods, steps)
    assert total == 12521

def test_score2():
    # #############
    # #...........#
    # ###B#D#C#A###
    #   #C#D#B#A#
    #   #########
    # #############
    # #01.4.7.0.34#
    # ###2#5#8#1###
    #   #3#6#9#2#
    #   #########
    #      [0   1  2  3  4  5  6   7]
    #      [A   A  B  B  C  C  D   D ]
    pods = [11, 12, 2, 9, 3, 8, 5, 6] 
    steps = [(0,13), # move A's to the hall
            (0, 14),
            (1,11),
            (1,10),
            (1,7),
            (1,4),
            (1,1),
            (6,7), # move D's in place
            (6,10),
            (6,11),
            (6,12),
            (7,5),# other D
            (7,7),
            (7,10),
            (7,11),
    # 
    # #############
    # #.A.......A.#
    # ###B#.#C#D###
    #   #C#.#B#D#
    #   #########
    # 
            (2,4), # B to its place
            (2,5),
            (2,6),
    # """
    # #############
    # #.A.......A.#
    # ###.#.#C#D###
    #   #C#B#B#D#
    #   #########
    # """        
            (5,10), # C out
            (3,8), # second B to its place
            (3,7),
            (3,5),
    # """
    # #############
    # #.A.....C.A.#
    # ###.#B#.#D###
    #   #C#B#.#D#
    #   #########
    # """        
        # C to its place
        (5,8),
        (5,9),
        # other C to its place
        (4,2),
        (4,4),
        (4,7),
        (4,8),
        # A's to their place
        (1,2),
        (1,3),
        (0,13),
        (0,10),
        (0,7),
        (0,4),
        (0,2),
    ]
    total = score(pods, steps)
    print(f"Total score = {total}")