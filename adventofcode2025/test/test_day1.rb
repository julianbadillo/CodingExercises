require 'minitest/autorun'
require_relative '../lib/day1'

class Day1Test < Minitest::Test
  def test_part1
    moves = [['R', 50]]
    assert_equal 1, Day1.part1(moves)

    moves = [['R', 50], ['R', 100]]
    assert_equal 2, Day1.part1(moves)

    moves = [['L', 50]]
    assert_equal 1, Day1.part1(moves)

    moves = [['L', 50], ['L', 100]]
    assert_equal 2, Day1.part1(moves)
  end

  def test_part1_file
    # Open da1/input_test.txt
    moves = []
    File.open('./day1/input_test.txt', 'r') do |file|
        moves = file.each_line.map { |l| [l[0], l[1..].to_i] }
    end
    assert_equal 3, Day1.part1(moves)
  end
  def test_part2
    moves = [['R', 50]]
    assert_equal 1, Day1.part2(moves)

    moves = [['R', 100], ['R', 150]]
    assert_equal 3, Day1.part2(moves)

    moves = [['L', 50]]
    assert_equal 1, Day1.part2(moves)

    moves = [['L', 100], ['L', 150]]
    assert_equal 3, Day1.part2(moves)
  end

  def test_part2_file
    # Open da1/input_test.txt
    moves = []
    File.open('./day1/input_test.txt', 'r') do |file|
        moves = file.each_line.map { |l| [l[0], l[1..].to_i] }
    end
    assert_equal 6, Day1.part2(moves)
  end
end
