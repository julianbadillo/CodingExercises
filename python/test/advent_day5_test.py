from advent_day5 import *

def test_vent_init():
    vent = Vent("0,9 -> 5,9")
    assert vent.x1 == 0
    assert vent.y1 == 9
    assert vent.x2 == 5
    assert vent.y2 == 9
    assert vent.w == 5
    assert vent.h == 0
    assert vent.dx == 1
    assert vent.dy == 0
    assert vent.l == 5

def test_vent_init2():
    vent = Vent("6,4 -> 2,0")
    assert vent.x1 == 6
    assert vent.y1 == 4
    assert vent.x2 == 2
    assert vent.y2 == 0
    assert vent.w == -4
    assert vent.h == -4
    assert vent.dx == -1
    assert vent.dy == -1
    assert vent.l == 4

def test_mark_map():
    vent = Vent("1,0 -> 3,0")
    map = [[0 for i in range(5)] for j in range(5)]
    vent.mark(map)

    assert map == [[0, 0, 0, 0, 0],
                    [1, 0, 0, 0, 0],
                    [1, 0, 0, 0, 0],
                    [1, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0],]

def test_mark_map2():
    vent = Vent("1,3 -> 3,1")
    map = [[0 for i in range(5)] for j in range(5)]
    vent.mark(map)

    assert map == [[0, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0],
                    [0, 0, 1, 0, 0],
                    [0, 1, 0, 0, 0],
                    [0, 0, 0, 0, 0],]

def test_overlap_hv_test():
    lines = read_file("test/advent_day5_test.txt")
    vents = load_vents(lines)
    s = overlap_hv(vents)
    assert s == 5

def test_overlap_hv():
    lines = read_file("test/advent_day5.txt")
    vents = load_vents(lines)
    s = overlap_hv(vents)
    assert s == 5124


def test_overlap_test():
    lines = read_file("test/advent_day5_test.txt")
    vents = load_vents(lines)
    s = overlap_hv(vents)
    assert s == 12

def test_overlap():
    lines = read_file("test/advent_day5.txt")
    vents = load_vents(lines)
    s = overlap_hv(vents)
    assert s == 19771
