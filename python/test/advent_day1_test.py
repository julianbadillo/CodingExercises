from advent_day1 import *


def test_read_file():
    expected = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263]
    actual = read_file('./test/advent_day1_test.txt')
    assert expected == actual

def test_larger_than_prev():
    input = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263]
    expected = 7
    actual = larger_than_prev(input)
    assert expected == actual

def test_all_part1():
    input = read_file('./test/advent_day1.txt')
    expected = 1532
    actual = larger_than_prev(input)
    assert expected == actual

def test_larger_than_prev_window():
    input = [199, 200, 208, 210, 200, 207, 240, 269, 260, 263]
    expected = 5
    actual = larger_than_prev_window(input)
    assert expected == actual

def test_all_part2():
    input = read_file('./test/advent_day1.txt')
    expected = 1571
    actual = larger_than_prev_window(input)
    assert expected == actual
