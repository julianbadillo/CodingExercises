require 'minitest/autorun'
require_relative '../lib/day2'

class Day2Test < Minitest::Test
  def test_is_invalid_p1
    assert_equal true, Day2.is_invalid_p1(11)
    assert_equal true, Day2.is_invalid_p1(22)
    assert_equal true, Day2.is_invalid_p1(1212)
    assert_equal false, Day2.is_invalid_p1(1213)
    assert_equal true, Day2.is_invalid_p1(1188511885)
  end
  def test_count_invalid_p1
    assert_equal 2, Day2.count_invalid_p1(11, 22)
    assert_equal 1, Day2.count_invalid_p1(95, 115)
    assert_equal 1, Day2.count_invalid_p1(998, 1012)
    assert_equal 1, Day2.count_invalid_p1(1188511880, 1188511890)
    assert_equal 1, Day2.count_invalid_p1(222220, 222224)
    assert_equal 1, Day2.count_invalid_p1(38593856, 38593862)
  end
  def test_add_invalid_p1
    assert_equal 33, Day2.add_invalid_p1([[11, 22]])
    ranges = [[11, 22],[95, 115],[998, 1012],[1188511880, 1188511890],[222220, 222224],[1698522, 1698528],[446443, 446449],[38593856, 38593862],[565653, 565659],[824824821, 824824827],[2121212118, 2121212124]]
    assert_equal 1227775554, Day2.add_invalid_p1(ranges)
  end
  def test_arr_equality
    arr = ["12", "12", "12"]
    assert_equal true, arr.all?(arr.first)
    parts = []
    id_s = "121212"
    n = id_s.length / 3
    3.times { |i| parts.push(id_s[i*n...(i+1)*n]) }
    assert_equal arr, parts
    r = (0...3).map { |i| parts[i] }
    assert_equal r, parts
  end
  def test_is_invalid_p2
    assert_equal true, Day2.is_invalid_p2(11)
    assert_equal true, Day2.is_invalid_p2(22)
    assert_equal true, Day2.is_invalid_p2(1212)
    assert_equal false, Day2.is_invalid_p2(1213)
    assert_equal true, Day2.is_invalid_p2(1188511885)
    assert_equal true, Day2.is_invalid_p2(121212)
    assert_equal true, Day2.is_invalid_p2(999)
    assert_equal true, Day2.is_invalid_p2(1111111)
  end
  def test_add_invalid_p2
    assert_equal 33, Day2.add_invalid_p2([[11, 22]])
    ranges = [[11, 22],[95, 115],[998, 1012],[1188511880, 1188511890],[222220, 222224],[1698522, 1698528],[446443, 446449],[38593856, 38593862],[565653, 565659],[824824821, 824824827],[2121212118, 2121212124]]
    assert_equal 4174379265, Day2.add_invalid_p2(ranges)
  end
  
end
