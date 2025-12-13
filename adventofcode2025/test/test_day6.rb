require 'minitest/autorun'
require_relative '../lib/day6'

class Day6Test < Minitest::Test

  def test_split()
    s = "  5    4     3  ".split(/\s+/).reject(&:empty?)
    assert_equal 3, s.length
    assert_equal ["5", "4", "3"], s
  end

  def test_regex()
    assert "1233552" =~ /^\d+\z/
    assert !("1223-5" =~ /^\d+\z/)
    assert !("1223-5".match?(/^\d+\z/))
  end

  def test_parse_input()
    lines = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ".split("\n")
    probs = Day6.parse_input(lines)
    assert_equal 4, probs.length
    assert_equal 3, probs[0].nums.length
    assert_equal [123, 45, 6], probs[0].nums
    assert_equal "*", probs[0].op
  end
  def test_res()
    p = P.new
    p.nums = [1, 2, 3, 4]
    p.op = "+"
    assert_equal 10, p.res
    p = P.new
    p.nums = [1, 2, 1, 3, 5]
    p.op = "*"
    assert_equal 30, p.res
  end

  def test_part1()
    lines = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ".split("\n")
    assert_equal 4277556, Day6.part1(lines)
  end

  def test_parse_cephalopod_input()
    lines = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ".split("\n")
    probs = Day6.parse_cephalopod_input(lines)
    assert_equal 4, probs.length
    assert_equal 3, probs[0].nums.length
    assert_equal [1, 24, 356], probs[0].nums
    assert_equal "*", probs[0].op
    assert_equal 3, probs[1].nums.length
    assert_equal [369, 248, 8], probs[1].nums
    assert_equal "+", probs[1].op
  end

  def test_part2()
    lines = "123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  ".split("\n")
    assert_equal 3263827, Day6.part2(lines)
  end

end
