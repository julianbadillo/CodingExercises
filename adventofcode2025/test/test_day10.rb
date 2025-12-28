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

  def test_swap()
    x = [1,2,3,4]
    x[0], x[1] = x[1], x[0]
    assert_equal [2,1,3,4], x
  end

  def test_parse_line()
    line = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}"
    lights, buttons, jolts = Day10.parse_line(line)
    assert_equal 0b01000, lights
    assert_equal 5, buttons.length
    assert_equal 0b11101, buttons[0]
    assert_equal 0b01100, buttons[1]
    assert_equal 0b10001, buttons[2]
    assert_equal 0b00111, buttons[3]
    assert_equal 0b11110, buttons[4]
    assert_equal 5, jolts.length
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
    lights, buttons, jolts = Day10.parse_line(line)
    assert_equal 4, jolts.length
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
    lights, buttons, jolts = Day10.parse_line(line)
    m = Mac.new()
    m.lights = lights
    m.buttons = buttons
    assert_equal 2, m.solve_buttons
    assert_equal 4, jolts.length
  end

  def test_part1()
    lines = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}".split("\n")
    assert_equal 7, Day10.part1(lines)
  end

  def test_mat_init()
    tableau = Array.new(2) { Array.new(3) }
    assert_equal 2, tableau.length
    assert_equal 3, tableau[0].length
    assert_equal 3, tableau[1].length
  end

  def test_setup_tableau()
    red = Red.new
    red.buttons = [[3], [1,3], [2], [2,3], [0,2], [0,1]]
    red.jolts = [3,5,4,7]
    red.setup_tableau
    # assert_equal 10, red.tableau.length
    assert_equal 6, red.tableau.length
    # assert_equal 19, red.tableau[0].length
    assert_equal 7, red.tableau[0].length
    assert_equal 1, red.tableau[0][5]
    assert_equal 1, red.tableau[0][4]
    assert_equal 1, red.tableau[1][1]
    assert_equal 1, red.tableau[3][0]
    assert_equal 1, red.tableau[3][1]
    assert_equal 1, red.tableau[3][3]
    # jolts
    assert_equal 3, red.tableau[0][-1]
    assert_equal 5, red.tableau[1][-1]
    assert_equal 4, red.tableau[2][-1]
    assert_equal 7, red.tableau[3][-1]
    # Z row
    assert_equal (-1), red.tableau[-2][0]
    assert_equal (-1), red.tableau[-2][1]
    assert_equal (-1), red.tableau[-2][2]
    assert_equal (-1), red.tableau[-2][3]
    # A row
    assert_equal 1, red.tableau[-1][0]
    assert_equal 2, red.tableau[-1][1]
    assert_equal 19, red.tableau[-1][-1]
  end

  def test_pivot()
    red = Red.new
    red.buttons = [[3], [1,3], [2], [2,3], [0,2], [0,1]]
    red.jolts = [3,4,5,7]
    red.setup_tableau
    pivot_row, pivot_col = red.find_pivot

    assert_equal 1, pivot_col
    assert_equal 1, pivot_row
    
    # test pivot
    assert_equal 11, red.reduce(pivot_row, pivot_col)

    assert_equal 0, red.tableau[0][1]
    assert_equal 1, red.tableau[1][1]
    assert_equal 0, red.tableau[2][1]
    assert_equal 0, red.tableau[3][1]
    assert_equal 0, red.tableau[4][1]
    assert_equal 0, red.tableau[5][1]
    # assert_equal 0, red.tableau[6][1]
    # assert_equal 0, red.tableau[7][1]
    # assert_equal 0, red.tableau[8][1]
    # assert_equal 0, red.tableau[9][1]

    pivot_row, pivot_col = red.find_pivot
    assert_equal 3, pivot_row
    assert_equal 3, pivot_col
    assert_equal 5, red.reduce(pivot_row, pivot_col)
    pivot_row, pivot_col = red.find_pivot
    assert_equal 2, pivot_row
    assert_equal 4, pivot_col
    assert_equal 1, red.reduce(pivot_row, pivot_col)
    pivot_row, pivot_col = red.find_pivot
    assert_equal 0, pivot_row
    assert_equal 0, pivot_col
    assert_equal 0, red.reduce(pivot_row, pivot_col)
    # Cost!
    assert_equal 10, red.tableau[-2][-1]
  end

  def test_pivot2()
    red = Red.new
    red.buttons = [[0,2,3,4], [2,3], [0,4], [0,1,2], [1,2,3,4]]
    red.jolts = [7,5,12,7,2]
    red.setup_tableau
    pr, pc = red.find_pivot
    red.reduce(pr, pc)
    # red.tprint()
    pr, pc = red.find_pivot
    red.reduce(pr, pc)
    # red.tprint()
    pr, pc = red.find_pivot
    red.reduce(pr, pc)
    # red.tprint()
    pr, pc = red.find_pivot
    red.reduce(pr, pc)
    # red.tprint()
    assert_equal 12, red.tableau[-2][-1]
  end

  def test_pivot3()
    red = Red.new
    red.buttons, red.jolts = Day10.parse_line2("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}")
    red.setup_tableau
    # red.tprint()
    while true do
      pr, pc = red.find_pivot
      r = red.reduce(pr, pc)
      # red.tprint()
      if r == 0
        break
      end
    end
    assert_equal 11, red.tableau[-2][-1]
    assert_equal 0, red.tableau[-1][-1]
  end

  def test_pivot4()
    red = Red.new
    red.buttons, red.jolts = Day10.parse_line2 "[.###.#..] (0,2,3,4,6,7) (1,2,4,5,6) (1,3,4,5,7) (0,2,4) (4,7) (0,1,4,6) (1,2,3,4,5,7) (2,4,7) {29,44,42,26,66,27,30,36}"
    red.setup_tableau
    # red.tprint()
    while true do
      pr, pc = red.find_pivot
      r = red.reduce(pr, pc)
      # red.tprint()
      if r == 0
        break
      end
    end
    assert_equal 66, red.tableau[-2][-1]
    assert_equal 0, red.tableau[-1][-1]
  end

  def test_solving()
    red = Red.new
    red.buttons, red.jolts = Day10.parse_line2 "[###.#...] (3,4,6) (1,2,3,4,5,7) (0,1,2,4) (0,1,3,5,7) (0,1,4,5,6,7) (0,2,7) (4,6) (7) (0,6) (1,4,5,6,7) {214,221,33,36,226,204,203,217}"
    red.setup_tableau
    # red.tprint()
    
    r = 0
    for c in (0...red.bn) do
      r2 = red.find_nonzero(r, c)
      if r2 == nil
        # print("Couldn't find a zero")
        # red.tprint()
        next
      end

      # if different - swap
      if r2 != r
        red.swap_rows(r2, r)
      end
      red.reduce(r, c)
      # red.tprint()
      r += 1
    end
    # Initial solution - still bound variables
    # TODO can I find a pivot for minimization
    pr, pc = red.find_pivot(-2) # On second to last row (objective)
    # print("pr, pc = #{pr}, #{pc}\n")
    # red.reduce(pr, pc)
    # red.tprint()

    # TODO can I find a pivot for minimization
    pr, pc = red.find_pivot(-2) # On second to last row (objective)
    # print("pr, pc = #{pr}, #{pc}\n")
    red.reduce(pr, pc)
    # red.tprint()

  end

end