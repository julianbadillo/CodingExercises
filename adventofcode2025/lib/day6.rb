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
end
