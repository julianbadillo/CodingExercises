from advent_day6 import *
from itertools import product

def test_read_file():
    l = read_file("./data/adventofcode2021/advent_day6_test.txt")
    assert l == [3,4,3,1,2]

def test_lanternfish_progression():
    l = [3,4,3,1,2]
    ite = lanternfish_progression(l, 18)
    expected = [[2,3,2,0,1],
                [1,2,1,6,0,8],
                [0,1,0,5,6,7,8],
                [6,0,6,4,5,6,7,8,8],
                [5,6,5,3,4,5,6,7,7,8],
                [4,5,4,2,3,4,5,6,6,7],
                [3,4,3,1,2,3,4,5,5,6],
                [2,3,2,0,1,2,3,4,4,5],
                [1,2,1,6,0,1,2,3,3,4,8],
                [0,1,0,5,6,0,1,2,2,3,7,8],
                [6,0,6,4,5,6,0,1,1,2,6,7,8,8,8],
                [5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8],
                [4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8],
                [3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8],
                [2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7],
                [1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8],
                [0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8],
                [6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8],]
    i = 0
    for res, exp in zip(ite, expected):
        assert res == exp
        i += 1
    assert i == 18
    
def test_progr_all_test():
    l = read_file("data/adventofcode2021/advent_day6_test.txt")
    ite = lanternfish_progression(l, 80)    
    res = []
    for x in ite:
        res = x
    assert len(res) == 5934

def test_progr_all():
    l = read_file("data/adventofcode2021/advent_day6.txt")
    ite = lanternfish_progression(l, 80)    
    res = []
    for x in ite:
        res = x
    assert len(res) == 389726


def test_lanternfish_progression_dict():
    l = [3,4,3,1,2]
    ite = lanterfish_progression_dict(l, 18)
    expected = [[2,3,2,0,1],
                [1,2,1,6,0,8],
                [0,1,0,5,6,7,8],
                [6,0,6,4,5,6,7,8,8],
                [5,6,5,3,4,5,6,7,7,8],
                [4,5,4,2,3,4,5,6,6,7],
                [3,4,3,1,2,3,4,5,5,6],
                [2,3,2,0,1,2,3,4,4,5],
                [1,2,1,6,0,1,2,3,3,4,8],
                [0,1,0,5,6,0,1,2,2,3,7,8],
                [6,0,6,4,5,6,0,1,1,2,6,7,8,8,8],
                [5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8],
                [4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8],
                [3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8],
                [2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7],
                [1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8],
                [0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8],
                [6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8],]
    i = 0
    for res, exp in zip(ite, expected):
        assert res == len(exp)
        i += 1
    assert i == 18

def test_progr_all_dict_test():
    l = read_file("data/adventofcode2021/advent_day6_test.txt")
    ite = lanterfish_progression_dict(l, 256)    
    res = []
    for x in ite:
        res = x
    assert res == 26984457539

def test_progr_dict_all():
    l = read_file("data/adventofcode2021/advent_day6.txt")
    ite = lanterfish_progression_dict(l, 256)    
    res = []
    for x in ite:
        res = x
    assert res == 1743335992042