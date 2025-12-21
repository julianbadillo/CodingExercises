require 'minitest/autorun'
require_relative '../lib/day10'

class Day10Test < Minitest::Test

  def test_bit_ops()
    x = 0b0010
    y = 0b0001
    assert_equal 0b0011, x | y
    assert_equal 0b0011, x ^ y
    assert_equal 0b0000, x & y
    assert_equal 0b0100, x << 1
    assert_equal 0b0001, x >> 1
    assert_equal 0b1111, (1 << 4) -1
  end

  def test_count_bits()
    bits = 0
    x = 0b0111010011
    while x > 0 do 
      if x & 1 == 1
        bits += 1
      end
      x = x >> 1
    end
    assert_equal 6, bits
  end

  def test_substr()
    x = "[1,2,3,4]"
    assert_equal "1,2,3,4", x[1...-1]
  end

  def test_parse_line()
    line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}"
    lights, buttons, volts = Day10.parse_line(line)
    assert_equal 0b01000, lights
    assert_equal 5, buttons.length
    assert_equal 0b11101, buttons[0]
    assert_equal 0b01100, buttons[1]
    assert_equal 0b10001, buttons[2]
    assert_equal 0b00111, buttons[3]
    assert_equal 0b11110, buttons[4]
  end

  def test_parse_input()
    lines = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}".split("\n")
    machines = Day10.parse_input(lines)
    assert_equal 3, machines.length
    assert_equal 0b0110, machines[0].lights
    assert_equal 6, machines[0].buttons.length
    assert_equal 4, machines[0].jolts.length
  end

  def test_machine_push_buttons()
    line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
    lights, buttons, volts = Day10.parse_line(line)
    m = Mac.new()
    m.lights = lights
    m.buttons = buttons

    assert_equal 0b0110, m.lights
    assert_equal 0b1000, m.buttons[0]
    assert_equal 0b1010, m.buttons[1]
    assert_equal 0b0100, m.buttons[2]
    assert_equal 0b1100, m.buttons[3]
    assert_equal 0b0101, m.buttons[4]
    assert_equal 0b0011, m.buttons[5]

    # Apply button 0
    assert_equal 0b1110, m.push_buttons(0b000001)
    # Apply button 1
    assert_equal 0b1100, m.push_buttons(0b000010)
    # Apply both 0 and 1
    assert_equal 0b0110 ^ 0b1000 ^ 0b1010, m.push_buttons(0b000011)
    # solution 1 - pressing first three buttons
    assert_equal 0, m.push_buttons(0b000111)
    # solution 2 - pressing bt 1, 3
    assert_equal 0, m.push_buttons(0b001010)
    # solution 5 - pressing all but 1
    assert_equal 0, m.push_buttons(0b111101)
  end
  def test_machine_solve_buttons()
    line = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
    lights, buttons, volts = Day10.parse_line(line)
    m = Mac.new()
    m.lights = lights
    m.buttons = buttons
    assert_equal 2, m.solve_buttons
  end

  def test_part1()
    lines = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}".split("\n")
    assert_equal 7, Day10.part1(lines)
  end
end