# Machine
class Mac
  attr_accessor :lights, :buttons, :jolts
  # Find the lowest combination of button pushing to get the lights on
  def solve_buttons()
    limit = 1 << (@buttons.length)
    comb = 0
    min_comb = 0
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
          min_comb = comb
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

  
  def self.part2(lines)
    return -2
  end
end
