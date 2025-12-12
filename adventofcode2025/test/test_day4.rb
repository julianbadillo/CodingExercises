require 'minitest/autorun'
require_relative '../lib/day4'

class Day4Test < Minitest::Test

  def test_m_class()
    m = M.new(1,-2)
    puts(m)
    # Day4.is_block_free(1,2,3)
  end

  def test_is_roll_free()
    mat = "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.".split("\n")
    assert_equal 10, mat.length
    n_rows = mat.length
    n_cols = mat[0].length
    assert true, Day4.is_roll_free(mat, n_rows, n_cols, 0, 2)
    assert true, Day4.is_roll_free(mat, n_rows, n_cols, 2, 6)
  end

  def test_count_free_rolls()
    mat = "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.".split("\n")
   assert_equal 13, Day4.count_free_rolls(mat)
  end

  def test_find_free_rolls()
    mat = "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.".split("\n")
    rolls = Day4.find_free_rolls(mat)
    assert_equal 13, rolls.length
    assert_equal M.new(0, 2), rolls[0]
    assert true, rolls.include?(M.new(2, 6))
  end

  def test_remove_free_rolls()
    mat = "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.".split("\n")
    assert_equal 13, Day4.remove_free_rolls(mat)
    mat2 = ".......@..
.@@.@.@.@@
@@@@@...@@
@.@@@@..@.
.@.@@@@.@.
.@@@@@@@.@
.@.@.@.@@@
..@@@.@@@@
.@@@@@@@@.
....@@@...".split("\n")
    assert_equal mat, mat2
  end

  def test_part2()
    mat = "..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.".split("\n")
    res = Day4.part2(mat)
    mat2 = "..........
..........
..........
....@@....
...@@@@...
...@@@@@..
...@.@.@@.
...@@.@@@.
...@@@@@..
....@@@...".split("\n")
    assert_equal mat, mat2
    assert_equal 43, res
  end
end
