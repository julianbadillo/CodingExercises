from advent_day4 import *

def test_load_bingos():
    lines = read_file("test/advent_day4_test.txt")
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    for b in bingos:
        print(b)
        print()
    
    assert numbers == [7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1]

    assert bingos[0].numbers == [[22,13,17,11,0],
                                [8,2,23,4,24],
                                [21,9,14,16,7],
                                [6,10,3,18,5],
                                [1,12,20,15,19],]
    assert bingos[0].marked == [[False, False, False, False, False],
                                [False, False, False, False, False],
                                [False, False, False, False, False],
                                [False, False, False, False, False],
                                [False, False, False, False, False],]

    assert len(bingos) == 3

def test_mark_number():
    bingo = Bingo()
    bingo.numbers = [[22,13,17,11,0],
                    [8,2,23,4,24],
                    [21,9,14,16,7],
                    [6,10,3,18,5],
                    [1,12,20,15,19],]
    r = bingo.mark_number(22)
    assert r is None
    assert bingo.marked[0][0]


def test_mark_nubmer_bingo_row():
    bingo = Bingo()
    bingo.numbers = [[22,13,17,11,0],
                    [8,2,23,4,24],
                    [21,9,14,16,7],
                    [6,10,3,18,5],
                    [1,12,20,15,19],]
    r = bingo.mark_number(22)
    assert r is None
    r = bingo.mark_number(13)
    assert r is None
    r = bingo.mark_number(17)
    assert r is None
    r = bingo.mark_number(11)
    assert r is None
    r = bingo.mark_number(0)
    assert r == 237


def test_mark_nubmer_bingo_column():
    bingo = Bingo()
    bingo.numbers = [[22,13,17,11,0],
                    [8  ,2,23,4,24],
                    [21 ,9,14,16,7],
                    [6  ,10,3,18,5],
                    [1  ,12,20,15,19],]
    r = bingo.mark_number(13)
    assert r is None
    r = bingo.mark_number(2)
    assert r is None
    r = bingo.mark_number(9)
    assert r is None
    r = bingo.mark_number(10)
    assert r is None
    r = bingo.mark_number(12)
    assert r == 254



def test_mark_nubmer_bingo_row2():
    bingo = Bingo()
    bingo.numbers = [[14, 21, 17, 24, 4],
                    [10, 16, 15, 9, 19],
                    [18, 8, 23, 26, 20],
                    [22, 11, 13, 6, 5],
                    [2, 0, 12, 3, 7],]
    numbers = [7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1]
    for n in numbers:
        r = bingo.mark_number(n)
        if n != 24:
            assert r is None, "Number = " + str(n)
        else:
            assert r == 188
            break


def test_play_game():
    lines = read_file("test/advent_day4_test.txt")
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    (i, res) = play_game(numbers, bingos)

    assert res == 188
    assert i == 24


def test_play_game_final():
    lines = read_file("test/advent_day4.txt")
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    (i, res) = play_game(numbers, bingos)
    print(i, res, res * i)
    assert res == 782
    assert i == 60


def test_play_game_looser():
    lines = read_file("test/advent_day4_test.txt")
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    (i, res) = play_game_loser(numbers, bingos)
    print(i, res, res * i)
    assert res == 148
    assert i == 13

def test_play_game_looser_final():
    lines = read_file("test/advent_day4.txt")
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    (i, res) = play_game_loser(numbers, bingos)
    print(i, res, res * i)
    assert res == 361
    assert i == 35

