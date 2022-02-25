from advent_day13 import *
from itertools import product

def test_read_file():
    (coords, inst) = read_file("data/adventofcode2021/advent_day13_test.txt")
    assert coords == {(6,10),
                    (0,14),
                    (9,10),
                    (0,3),
                    (10,4),
                    (4,11),
                    (6,0),
                    (6,12),
                    (4,1),
                    (0,13),
                    (10,12),
                    (3,4),
                    (3,0),
                    (8,4),
                    (1,10),
                    (2,14),
                    (8,10),
                    (9,0),}
    assert inst == ["fold along y=7",
                    "fold along x=5"]

def test_fold_y():
    assert fold((0, 14), y = 7) == (0, 0)
    assert fold((0, 13), y = 7) == (0, 1)
    assert fold((0, 2), y = 7) == (0, 2)

def test_fold_x():
    assert fold((6, 0), x = 5) == (4, 0)
    assert fold((10, 4), x = 5) == (0, 4)
    assert fold((0, 4), x = 5) == (0, 4)

def test_fold_one_test():
    (coords, inst) = read_file("data/adventofcode2021/advent_day13_test.txt")
    assert fold_one(coords, inst[0]) == 17

def test_fold_one():
    (coords, inst) = read_file("data/adventofcode2021/advent_day13.txt")
    print("Points remaining: ", fold_one(coords, inst[0]))

def test_fold_all_test():
    (coords, inst) = read_file("data/adventofcode2021/advent_day13_test.txt")
    r = fold_all(coords, inst)
    assert len(r) == 16

def test_fold_all():
    (coords, inst) = read_file("data/adventofcode2021/advent_day13.txt")
    r = fold_all(coords, inst)
    print_points(r)
    