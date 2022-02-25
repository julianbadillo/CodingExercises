from advent_day2 import *


def test_read_file():
    expected = [['forward', '5'],
                ['down', '5'],
                ['forward', '8'],
                ['up', '3'],
                ['down', '8'],
                ['forward', '2'],]
    actual = read_file('./data/adventofcode2021/advent_day2_test.txt')
    assert expected == actual

def test_calc_position():
    input = expected = [['forward', '5'],
                ['down', '5'],
                ['forward', '8'],
                ['up', '3'],
                ['down', '8'],
                ['forward', '2'],]
    expected = (15, 10)
    actual = calculate_final_position(input)
    assert expected == actual

def test_all_part1_testdata():
    input = read_file('./data/adventofcode2021/advent_day2_test.txt')
    expected = (15, 10)
    actual = calculate_final_position(input)
    assert expected == actual

def test_all_part1():
    input = read_file('./data/adventofcode2021/advent_day2.txt')
    expected = (1940, 861)
    actual = calculate_final_position(input)
    print(f"{actual} = {actual[0] * actual[1]}")
    assert expected == actual

def test_calc_position_aim():
    input = expected = [['forward', '5'],
                ['down', '5'],
                ['forward', '8'],
                ['up', '3'],
                ['down', '8'],
                ['forward', '2'],]
    expected = (15, 60)
    actual = calculate_final_position_aim(input)
    assert expected == actual

def test_all_part2():
    input = read_file('./data/adventofcode2021/advent_day2.txt')
    expected = (1940, 1007368)
    actual = calculate_final_position_aim(input)
    print(f"{actual} = {actual[0] * actual[1]}")
    assert expected == actual