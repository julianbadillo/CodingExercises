require 'minitest/autorun'
require_relative '../lib/day3'

class Day3Test < Minitest::Test

  def test_max_digit()
    assert_equal 99, Day3.max_digit('9111239')
    assert_equal 89, Day3.max_digit('21181239')
    assert_equal 98, Day3.max_digit('987654321111111')
    assert_equal 89, Day3.max_digit('811111111111119')
    assert_equal 78, Day3.max_digit('234234234234278')
    assert_equal 92, Day3.max_digit('818181911112111')
  end

  def test_part1()
    lines = "987654321111111
811111111111119
234234234234278
818181911112111".split("\n")
    assert_equal 357, Day3.part1(lines)
  end
  
  def test_arr()
    assert_equal [0, 1, 2, 3], (0...4).to_a
    assert_equal 1234, (0...5).map{|i| i.to_s}.join('').to_i
  end

  def test_subsequences()
    k = 5
    n = 12
    idx = (0...5).to_a
    go = true
    while go
      # Get the next sub-sequence
      (0...k).reverse_each do |i|
        idx[i] += 1
        if idx[i] <= n - 5 + i
          # all subsequent
          for j in (1...5 - i)
            idx[i + j] = idx[i] + j
          end
          break
        end
      end
      if idx[-1] == n
        break
      end
    end
    assert_equal [8,9,10,11,12], idx
  end

  def test_max_digit_n()
    assert_equal 999999999999, Day3.max_digit_n('999999999999111')
    assert_equal 999999999999, Day3.max_digit_n('111999999999999')
    assert_equal 999999999999, Day3.max_digit_n('119199999999999')
    assert_equal 999999999999, Day3.max_digit_n('191919999999999')
    assert_equal 999999999999, Day3.max_digit_n('191999999999991')
    assert_equal 999999999999, Day3.max_digit_n('999999999999111')
    assert_equal 987654321111, Day3.max_digit_n('987654321111111')
    assert_equal 888911112111, Day3.max_digit_n('818181911112111')
  end

  def test_str()
    assert_equal "1234", "0001234"[3..]
    assert_equal "1234".each_char.map { |i| i.to_i }, [1, 2, 3, 4]
  end

  def test_max_digit_r()
    assert_equal 999999999999, Day3.max_digit_r('999999999999111')
    assert_equal 999999999999, Day3.max_digit_r('111999999999999')
    assert_equal 999999999999, Day3.max_digit_r('119199999999999')
    assert_equal 999999999999, Day3.max_digit_r('191919999999999')
    assert_equal 999999999999, Day3.max_digit_r('191999999999991')
    assert_equal 999999999999, Day3.max_digit_r('999999999999111')
    assert_equal 987654321111, Day3.max_digit_r('987654321111111')
    assert_equal 888911112111, Day3.max_digit_r('818181911112111')
  end

  def test_max_digit_dp()
    assert_equal 34, Day3.max_digit_dp('1234', 2)
    assert_equal 345, Day3.max_digit_dp('12345', 3)
    assert_equal 25, Day3.max_digit_dp('21115', 2)
    assert_equal 435, Day3.max_digit_dp('41315', 3)
    assert_equal 999999999999, Day3.max_digit_dp('999999999999111')
    assert_equal 999999999999, Day3.max_digit_dp('111999999999999')
    assert_equal 999999999999, Day3.max_digit_dp('119199999999999')
    assert_equal 999999999999, Day3.max_digit_dp('191919999999999')
    assert_equal 999999999999, Day3.max_digit_dp('191999999999991')
    assert_equal 999999999999, Day3.max_digit_dp('999999999999111')
    assert_equal 987654321111, Day3.max_digit_dp('987654321111111')
    assert_equal 888911112111, Day3.max_digit_dp('818181911112111')
  end
end
