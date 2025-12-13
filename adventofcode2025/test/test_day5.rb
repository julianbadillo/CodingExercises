require 'minitest/autorun'
require_relative '../lib/day5'

class Day5Test < Minitest::Test

  def test_r_class()
    r = R.new(4, 5)
    assert r.includes?(4)
    assert r.includes?(5)
    assert !r.includes?(7)
    assert !r.includes?(3)

  end

  def test_parse_line()
    lines = "3-5
10-14
16-20
12-18

1
5
8
11
17
32".split("\n")
    ranges, ingredients = Day5.parse_input(lines)
    assert_equal 4, ranges.length
    assert_equal 6, ingredients.length

    assert_equal R.new(3, 5), ranges[0]
    assert_equal R.new(10, 14), ranges[1]

  end

  def test_is_fresh()
    ranges = [R.new(1, 4), R.new(3, 5), R.new(10, 12)]
    assert Day5.is_fresh?(ranges, 1)
    assert Day5.is_fresh?(ranges, 2)
    assert Day5.is_fresh?(ranges, 3)
    assert Day5.is_fresh?(ranges, 5)
    assert !Day5.is_fresh?(ranges, 6)
    assert !Day5.is_fresh?(ranges, 7)
    assert !Day5.is_fresh?(ranges, 8)
    assert !Day5.is_fresh?(ranges, 9)
    assert Day5.is_fresh?(ranges, 10)
    assert Day5.is_fresh?(ranges, 11)
    assert Day5.is_fresh?(ranges, 12)
    assert !Day5.is_fresh?(ranges, 13)
  end

  def test_part1()
    lines = "3-5
10-14
16-20
12-18

1
5
8
11
17
32".split("\n")
    assert_equal 3, Day5.part1(lines)
  end

  def test_overlaps()
    assert !R.new(3, 5).overlaps?(R.new(1, 2))
    assert R.new(3, 5).overlaps?(R.new(2, 3))
    assert R.new(3, 5).overlaps?(R.new(3, 4))
    assert R.new(3, 5).overlaps?(R.new(4, 6))
    assert R.new(3, 5).overlaps?(R.new(5, 6))
    assert !R.new(3, 5).overlaps?(R.new(6, 7))
  end

  def test_merge()
    m = R.new(3, 5).merge(R.new(5, 7))
    assert_equal R.new(3, 7), m
    m = R.new(3, 6).merge(R.new(4, 7))
    assert_equal R.new(3, 7), m
    m = R.new(2, 9).merge(R.new(4, 7))
    assert_equal R.new(2, 9), m
  end

  def test_merge_ranges()
    ranges = [
      R.new(1, 2),
      R.new(2, 4),
      R.new(3, 5),
      R.new(2, 6),
    ]
    res = Day5.merge_ranges(ranges)
    assert_equal 1, res.length
    assert_equal R.new(1, 6), res[0]
    # 3-5
    # 10-14
    # 16-20
    # 12-18
    ranges = [R.new(3, 5), R.new(10, 14), R.new(16, 20), R.new(12, 18)]
    res = Day5.merge_ranges(ranges)
    assert_equal 2, res.length
    assert res.include?(R.new(3, 5))
    assert res.include?(R.new(10, 20))
  end

  def test_size()
    assert_equal 3, R.new(3, 5).size
    assert_equal 11, R.new(10, 20).size
  end

  def test_part2()
    lines = "3-5
10-14
16-20
12-18

1
5
8
11
17
32".split("\n")
    assert_equal 14, Day5.part2(lines)
  end
end
