class P
  attr_accessor :nums, :op
  def initialize()
    @nums = []
  end
  def res()
    if @op == "+"
      return @nums.reduce(0){ |a, b| a + b}
    else
      return @nums.reduce(1){ |a, b| a * b}
    end
  end
end

module Day6
  def self.parse_input(lines)
    probs = []
    for line in lines do
      parts = line.split(/\s+/).reject(&:empty?)
      probs = Array.new(parts.length) { P.new } if probs.length == 0
      probs.zip(parts).each do |prob, part|
        if part =~ /^\d+\z/
          prob.nums.append(part.to_i)
        else
          prob.op = part
        end
      end
    end
    probs
  end
  def self.part1(lines)
    probs = self.parse_input(lines)
    return probs.map{|p| p.res}.sum
  end

  def self.parse_cephalopod_input(lines)
    num_r = lines.length
    num_c = lines[0].length
    probs = []
    p = P.new
    for c in (0...num_c) do
      col_str = (0...num_r - 1).map {|r| lines[r][c] }.join("").strip
      last_char = lines[num_r - 1][c]
      # set operator if not empty
      p.op = last_char if last_char != " "
      if col_str == ""
        probs.push(p)
        p = P.new
      else
        p.nums.push(col_str.to_i)
      end
    end
    probs.push(p)
    probs
  end
  def self.part2(lines)
    probs = self.parse_cephalopod_input(lines)
    return probs.map{|p| p.res}.sum
  end
end
