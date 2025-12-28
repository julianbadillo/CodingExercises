# Machine
class Mac
  attr_accessor :lights, :buttons, :jolts
  # Find the lowest combination of button pushing to get the lights on
  def solve_buttons()
    limit = 1 << (@buttons.length)
    comb = 0
    min_buttons = @buttons.length
    while comb < limit do
      if self.push_buttons(comb) == 0
        # count bits in one
        bits = 0
        x = comb
        while x > 0 do 
          if x & 1 == 1
            bits += 1
          end
          x = x >> 1
        end
        if min_buttons > bits
          min_buttons = bits
        end
      end
      comb += 1
    end
    return min_buttons
  end
  # simulate pushing a combination of buttons - encoded in the bits of
  # an integer
  def push_buttons(comb)
    res = @lights
    buttons.each_with_index do |bt, i|
      # if the ith bit is 1
      if (comb >> i) & 1 == 1
        res ^= bt
      end
    end
    return res
  end
end

class Red
  attr_accessor :tableau, :buttons, :jolts, :bn, :jn
  def setup_tableau()
    @bn = @buttons.length
    @jn = @jolts.length
    # two lines per equation, plus one for Z, one for A
    # @tableau = Array.new(2 * @jolts.length + 2) {
    @tableau = Array.new(@jolts.length + 2) {
      # One colum per button var, plus 3 times per equation, one for RHS (A, Z cols are redundant)
      # Array.new(@bn + 3 * @jn + 1, 0)
      Array.new(@bn + 1, 0)
    }
    # first - button columns
    @buttons.each_with_index do |b, c|
      b.each do |r|
        @tableau[r][c] = 1
        # And a duplicate for the second equations
        # @tableau[r + @jn][c] = 1
      end
    end
    # Last column - jolts
    @jolts.each_with_index do |jolt, r|
      @tableau[r][-1] = jolt
      # A duplicate for second equations
      # @tableau[r + @jn][-1] = jolt
    end
    
    @buttons.each_with_index do |b, c|
      # Second to last row Z - b0 - b1 - b2 ...
      @tableau[-2][c] = -1
      # Last row A = a0 + a1 + a2 ...
      # A + equation 1 + equation 2... -> jolts added per button
      @tableau[-1][c] = b.length
    end
    # Starting distance
    # A = eq1 + eq2.. = sum(buttons) + sum(vars) + sum(jolts)
    @tableau[-1][-1] = @jolts.sum
  end

  # Get the pivot col -> largest coef in A-row
  def find_pivot(row=-1)
    max_val = @tableau[row][0...@bn].max
    pivot_col = @tableau[row][0...@bn].index(max_val)

    # Get the pivot row -> smallest non-zero, non-inf division of C / coef
    min_val = 100000
    pivot_row = 0
    for r in (0...@jn) do
      if @tableau[r][pivot_col] != 0
        val = tableau[r][-1] / tableau[r][pivot_col]
        if val > 0 && val < min_val
          min_val = val
          pivot_row = r
        end
      end
    end
    # print("min_val=#{min_val}\n")
    # print("pivot v=#{@tableau[pivot_row][pivot_col]}\n")
    return pivot_row, pivot_col
  end

  # find non zero column value
  def find_nonzero(row, col)
    for row in (row...@jn)
      if @tableau[row][col] != 0
        return row
      end
    end
    return nil
  end

  def swap_rows(r1, r2)
    for c in (0...@tableau[r1].length)
      @tableau[r1][c], @tableau[r2][c] = @tableau[r2][c], @tableau[r1][c]
    end
  end

  # Gaussian reduction
  def reduce(pivot_row, pivot_col)
    # normalize row
    k = @tableau[pivot_row][pivot_col].to_f
    for c in (0...@tableau[pivot_row].length) do
      @tableau[pivot_row][c] /= k
    end
    # reduce other rows
    for r in (0...@tableau.length) do
      if r != pivot_row && @tableau[r][pivot_col] != 0
        k = -@tableau[r][pivot_col]
        for c in (0...@tableau[r].length) do 
          @tableau[r][c] += k*@tableau[pivot_row][c]
        end
      end
    end
    return @tableau[-1][-1]
  end

  def tprint()
    print("\n")
    @tableau.each { |r| print("#{r.map{|c| c.to_f.to_s.rjust(5)}.join(" ")}\n") }
  end
end


module Day10
  
  def self.parse_line(line)
    parts = line.split(" ")
    ligths_arr = parts[0][1...-1]
    lights = 0
    ligths_arr.each_char.with_index do |c, i|
      lights |= 1 << i if c == "#"
    end

    buttons_arr = parts[1...-1].map{|b| b[1...-1].split(",").map(&:to_i)}
    buttons = buttons_arr.map{ |bt|
      # each button becomes an encoding of wich bits it turns on / off
      bt.map{|b| 1 << b}.reduce(0){|x,y| x | y}
    }

    jolts = parts[-1][1...-1].split(",").map(&:to_i)
    return lights, buttons, jolts
  end

  def self.parse_input(lines)
    return lines.map{|line| 
      lights, buttons, jolts = self.parse_line(line)
      m = Mac.new()
      m.lights = lights
      m.buttons = buttons
      m.jolts = jolts
      m
    }
  end

  def self.part1(lines)
    machines = self.parse_input(lines)
    machines.map{|mac| mac.solve_buttons() }.sum
  end


  def self.parse_line2(line)
    parts = line.split(" ")
    # ignore lites

    buttons = parts[1...-1].map{|b| b[1...-1].split(",").map(&:to_i)}
    jolts = parts[-1][1...-1].split(",").map(&:to_i)
    return buttons, jolts
  end

  def self.parse_input2(lines)
    return lines.map{|line| 
      buttons, jolts = self.parse_line2(line)
      m = Red.new()
      m.buttons = buttons
      m.jolts = jolts
      m
    }
  end
  
  def self.part2(lines)
    reds = self.parse_input2(lines)
    s = 0
    reds.each_with_index do |red, i|
      red.setup_tableau
      # TODO
    end
    return s
  end
end
