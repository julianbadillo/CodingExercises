require 'minitest/autorun'
require_relative '../lib/day8'

class Day8Test < Minitest::Test

  def test_jb()
    jb = JB.new 1, 2, 3
    assert_equal "[1, 2, 3]", jb.to_s

    jb2 = JB.new(*"4,5,6".split(",").map(&:to_i))
    assert_equal "[4, 5, 6]", jb2.to_s
    assert_equal JB.new(4, 5, 6), jb2
    assert_equal 27, jb.d_square(jb2)

  end

  def test_parse_input()
    lines = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689".split("\n")
    boxes = Day8.parse_input(lines)
    assert_equal 20, boxes.length
    assert_equal JB.new(425, 690, 689), boxes[-1]
  end

  def test_jb_link()
    l1 = JBLink.new(JB.new(1,2,3), JB.new(3,2,1))
    assert_equal l1.d_sq, 8
    l2 = JBLink.new(JB.new(3,2,1), JB.new(1,2,3))
    assert_equal l2.d_sq, 8
    assert_equal l1, l2

  end

  def test_build_links()
    boxes = [
      JB.new(0,0,0),
      JB.new(4,0,0),
      JB.new(0,0,3),
      JB.new(0,2,0),
    ]
    links = Day8.build_links(boxes)
    assert_equal 6, links.length
    assert_equal JBLink.new(JB.new(0,0,0), JB.new(0,2,0)), links[0]
    assert_equal JBLink.new(JB.new(0,0,0), JB.new(0,0,3)), links[1]
    assert_equal JBLink.new(JB.new(0,2,0), JB.new(0,0,3)), links[2]
    assert_equal JBLink.new(JB.new(0,0,0), JB.new(4,0,0)), links[3]
    assert_equal JBLink.new(JB.new(0,2,0), JB.new(4,0,0)), links[4]
    assert_equal JBLink.new(JB.new(0,0,3), JB.new(4,0,0)), links[5]
  end

  def test_other()
    s = Set.new([1, 2, 3, 5])
    l = []
    l.push(s)
    g1 = l.select{|g| g.include?(6)}.first
    assert !g1
    g1 = l.select{|g| g.include?(3)}.first
    assert g1
    l.delete(g1)
    assert_equal 0, l.length
  end

  def test_connect_circuits()
    lines = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689".split("\n")
    boxes = Day8.parse_input(lines)
    circuits = Day8.connect_circuits(boxes, 10)
    assert_equal 4, circuits.length
    # largest circuits
    assert_equal 5, circuits[0].length
    assert_equal 4, circuits[1].length
    assert_equal 2, circuits[2].length
    assert_equal 2, circuits[2].length
  end
  def test_part_1()
    lines = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689".split("\n")
    assert_equal 40, Day8.part1(lines)
  end

  def test_connect_until_joined()
    lines = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689".split("\n")
    boxes = Day8.parse_input(lines)
    link = Day8.connect_until_joined(boxes)
    assert_equal JBLink.new(JB.new(216,146,977), JB.new(117,168,530)), link
  end

  def test_part2()
    lines = "162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689".split("\n")
    assert_equal 25272, Day8.part2(lines)
  end
end