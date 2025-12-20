require 'minitest/autorun'
require_relative '../lib/day9'

class Day9Test < Minitest::Test

  def test_p()
    p1 = Pt.new 7, 1
    p2 = Pt.new 11, 7
    assert_equal 35, p1.area(p2)
  end

  def test_sq()
    p1 = Pt.new 7, 1
    p2 = Pt.new 11, 7
    assert_equal Sq.new(p1, p2), Sq.new(p2, p1)
    assert_equal 35, Sq.new(p1, p2).area
    assert_equal 35, Sq.new(p2, p1).area
    assert Sq.new(p1, p2).strict_contains?(Pt.new(9, 6))
    assert Sq.new(p1, p2).strict_contains?(Pt.new(8, 5))
    assert !Sq.new(p1, p2).strict_contains?(Pt.new(7, 5))
    assert !Sq.new(p1, p2).strict_contains?(Pt.new(11, 5))
    assert !Sq.new(p1, p2).strict_contains?(Pt.new(9, 1))
    assert !Sq.new(p1, p2).strict_contains?(Pt.new(9, 7))
  end

  def test_parse_input()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    pts = Day9.parse_input(lines)
    assert_equal 8, pts.length
    assert_equal Pt.new(11, 7), pts[2]
  end

  def test_build_squares()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    pts = Day9.parse_input(lines)
    sqs = Day9.build_squares(pts)
    assert_equal 28, sqs.length
    assert_equal Sq.new(Pt.new(2, 5), Pt.new(11, 1)), sqs.first
    assert_equal 50, sqs.first.area
  end

  def test_part1()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    assert_equal 50, Day9.part1(lines)
  end

  def test_edge()
    e = Ed.new(Pt.new(7,1), Pt.new(11, 1))
    assert_equal 'h', e.orient
    assert_equal 7, e.lx
    assert_equal 11, e.ux
    assert_equal 1, e.ly
    assert_equal 1, e.uy
    assert e.contains?(Pt.new(7, 1))
    assert e.contains?(Pt.new(8, 1))
    assert e.contains?(Pt.new(11, 1))
    assert !e.contains?(Pt.new(6, 1))
    assert !e.contains?(Pt.new(12, 1))
    assert !e.contains?(Pt.new(8, 2))
    # crossings
    e1 = Ed.new(Pt.new(3, 0), Pt.new(3, 5))
    e2 = Ed.new(Pt.new(0, 3), Pt.new(5, 3))
    assert e1.cross?(e2)
    assert e2.cross?(e1)
    assert_equal Pt.new(3, 2.5), e1.mid
    assert_equal Pt.new(2.5, 3), e2.mid
  end

  def test_arr_index()
    arr = [1, 2, 3]
    assert_equal arr[-1], 3
    assert_nil arr[3]
    
  end
  def test_poly()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    pts = Day9.parse_input(lines)
    poly = Poly.new(pts)
    assert_equal 8, poly.edges.length
    # contained points
    assert poly.contains?(Pt.new(7, 1))
    assert poly.contains?(Pt.new(8, 2))
    assert poly.contains?(Pt.new(11, 1))
    assert poly.contains?(Pt.new(10, 6))
    assert poly.contains?(Pt.new(11, 6))
    assert poly.contains?(Pt.new(11, 5))
    assert poly.contains?(Pt.new(9, 6))
    assert poly.contains?(Pt.new(2, 3))
    assert poly.contains?(Pt.new(2, 4))
    assert poly.contains?(Pt.new(2, 5))
    assert poly.contains?(Pt.new(7, 5))
    # not contained points
    assert !poly.contains?(Pt.new(1, 1))
    assert !poly.contains?(Pt.new(6, 2))
    assert !poly.contains?(Pt.new(8, 6))
    assert !poly.contains?(Pt.new(12, 4))

    # test contains square
    assert poly.contains_sq?(Sq.new(Pt.new(7, 3), Pt.new(11, 1)))
    assert poly.contains_sq?(Sq.new(Pt.new(9, 7), Pt.new(9, 5)))
    assert poly.contains_sq?(Sq.new(Pt.new(9, 5), Pt.new(2, 3)))
    assert poly.contains_sq?(Sq.new(Pt.new(7, 1), Pt.new(11, 5)))
  end

  def test_build_unadjacent_squares()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    pts = Day9.parse_input(lines)
    sqs = Day9.build_unadjacent_squares(pts)
    # puts(sqs.map(&:to_s).join("\n"))
    assert_equal 21, sqs.length
    assert_equal Sq.new(Pt.new(11,1), Pt.new(2, 5)), sqs.first
    assert_equal 50, sqs.first.area
  end

  def test_part2()
    lines = "7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3".split("\n")
    assert_equal 24, Day9.part2(lines)
  end

end