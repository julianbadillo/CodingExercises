from advent_day12 import *
from itertools import product

def test_read_file():
    l = read_file("test/advent_day12_test1.txt")
    assert l == [["start", "A"],
                ["start", "b"],
                ["A", "c"],
                ["A", "b"],
                ["b", "d"],
                ["A", "end"],
                ["b", "end"],]


def test_build_graph():
    l = [["start", "A"],
                ["start", "b"],
                ["A", "c"],
                ["A", "b"],
                ["b", "d"],
                ["A", "end"],
                ["b", "end"],]
    graph = build_graph(l)
    assert "start" in graph
    assert "A" in graph
    assert "b" in graph
    assert "end" in graph

    assert graph["A"] in graph["start"].edges
    assert graph["b"] in graph["start"].edges
    assert graph["A"] in graph["b"].edges
    assert graph["b"] in graph["A"].edges

def test_count_paths_simple():
    l = [["start", "a"],
        ["start", "b"],
        ["a", "end"],
        ["b", "end"],]
    g = build_graph(l)
    assert count_paths(g) == 2

def test_count_paths_test1():
    l = read_file("test/advent_day12_test1.txt")
    g = build_graph(l)
    assert count_paths(g) == 10

def test_count_paths_test2():
    l = read_file("test/advent_day12_test2.txt")
    g = build_graph(l)
    assert count_paths(g) == 19

def test_count_paths_test3():
    l = read_file("test/advent_day12_test3.txt")
    g = build_graph(l)
    assert count_paths(g) == 226

def test_count_paths():
    l = read_file("test/advent_day12.txt")
    g = build_graph(l)
    print(count_paths(g))

def test_any_small_duplicate():
    assert not any_small_duplicate(['a','b','c','d','e'])
    assert any_small_duplicate(['a','b','c','d','e', 'a'])
    assert not any_small_duplicate(['A','a','A','b','A', 'c'])

def test_count_paths2_simple():
    l = [["start", "a"],
        ["start", "b"],
        ["a", "end"],
        ["b", "end"],]
    g = build_graph(l)
    assert count_paths2(g) == 2

def test_count_paths2_multi():
    l = [["start", "a"],
        ["start", "b"],
        ["a", "b"],
        ["a", "end"],
        ["b", "end"],]
    g = build_graph(l)
    assert count_paths2(g) == 6

"""
def test_count_paths2_r():
    l = read_file("test/advent_day12_test1.txt")
    g = build_graph(l)
    r = count_paths_r2(g["A"], ["start", "A", "b", "A"])
    assert r == 1

"""
def test_count_paths2_test1():
    l = read_file("test/advent_day12_test1.txt")
    g = build_graph(l)
    assert count_paths2(g) == 36

def test_count_paths2_test2():
    l = read_file("test/advent_day12_test2.txt")
    g = build_graph(l)
    assert count_paths2(g) == 103

def test_count_paths2_test3():
    l = read_file("test/advent_day12_test3.txt")
    g = build_graph(l)
    assert count_paths2(g) == 3509

def test_count_paths2():
    l = read_file("test/advent_day12.txt")
    g = build_graph(l)
    print(count_paths2(g))
