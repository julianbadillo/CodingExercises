require 'minitest/autorun'
require_relative '../lib/day7'

class Day7Test < Minitest::Test

  def test_split_beams()
    start  = ".......S.......".index("S")
    beams = Set[start]
    assert_equal 1, beams.length
    assert beams.include?(start)
    # no splitting
    beams, split_c = Day7.split_beams(beams, "...............")
    assert_equal 1, beams.length
    assert_equal 0, split_c
    assert beams.include?(start)
    # split once
    beams, split_c = Day7.split_beams(beams, ".......^.......")
    assert_equal 2, beams.length
    assert_equal 1, split_c
    assert beams.include?(start-1)
    assert beams.include?(start+1)
    # split twice - merged beams
    
    beams, split_c = Day7.split_beams(beams, "......^.^......")
    assert_equal 3, beams.length
    assert_equal 2, split_c
    assert beams.include?(start-2)
    assert beams.include?(start)
    assert beams.include?(start+2)
    
  end

  def test_part1()
    lines = ".......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
...............".split("\n")
    assert_equal 21, Day7.part1(lines)
  end

  def test_split_timelines()
    start  = ".......S.......".index("S")
    beams = {start => 1}
    assert_equal 1, beams.length
    assert 1, beams[start]
    # no splitting
    beams, split_c = Day7.split_timelines(beams, "...............")
    assert_equal 1, beams.length
    assert 1, beams[start]
    assert_equal 0, split_c
    # split once
    beams, split_c = Day7.split_timelines(beams, ".......^.......")
    assert_equal 2, beams.length
    assert_equal 1, split_c
    assert_equal 1, beams[start-1]
    assert_equal 1, beams[start+1]
    # split twice - merged beams
    
    beams, split_c = Day7.split_timelines(beams, "......^.^......")
    assert_equal 3, beams.length
    assert_equal 2, split_c
    assert_equal 1, beams[start-2]
    assert_equal 2, beams[start]
    assert_equal 1, beams[start+2]
    
  end

  def test_part2()
    lines = ".......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
...............".split("\n")
    assert_equal 40, Day7.part2(lines)
  end

end
