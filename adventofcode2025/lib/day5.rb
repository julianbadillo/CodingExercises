class R
  attr_accessor :s, :e
  def initialize(s, e)
    @s=s
    @e=e
  end

  def to_s
    "[#{@s}-#{@e}]"
  end

  def==(other)
    @s==other.s && @e==other.e
  end

  def includes?(x)
    @s <= x && x <= @e
  end

  def overlaps?(o)
    self.includes?(o.s) || self.includes?(o.e)
  end

  def merge(o)
    return R.new([@s, o.s].min, [@e, o.e].max)
  end

  def size()
    return @e - @s + 1
  end
end

module Day5
  def self.parse_input(lines)
    # Parse ranges
    inRanges = true
    ranges = []
    ingredients = []
    for line in lines do
      if line == ""
        inRanges = false
      elsif inRanges
        parts = line.split("-")
        ranges.push R.new(parts[0].to_i, parts[1].to_i)
      else
        ingredients.push line.to_i
      end
    end
    return ranges, ingredients
  end

  # Ingredient is in any range
  def self.is_fresh?(ranges, ing)
    ranges.any?{|r| r.includes? ing}
  end

  def self.part1(lines)
    ranges, ingredients = self.parse_input(lines)
    ingredients.select{|ing| self.is_fresh?(ranges, ing)}.count
  end

  def self.merge_ranges(ranges)
    result = []
    queue = ranges.sort{|a, b| a.s <=> b.s }  # Shallow copy
    while !queue.empty? do
      # compare first element with all others
      r1 = queue.shift
      go = true
      while go do
        queue2 = []
        go = false
        while !queue.empty? do
          r2 = queue.shift
          # merge overlapping ranges
          if r1.overlaps?(r2)
            r1 = r1.merge(r2)
            go = true
          else
            queue2.push(r2)
          end
        end
        queue = queue2
      end
      # r1 cannot be merged with any other range
      result.push(r1)
    end
    return result
  end

  def self.part2(lines)
    ranges, ingredients = self.parse_input(lines)
    res = self.merge_ranges(ranges)
    
    for i in (0...res.length) do
      for j in (i+1...res.length) do
        # print("#{i}, #{j}\n")
        if res[i].overlaps?(res[j])
          print("Error!!!!! #{res[i].to_i}, #{res[j].to_i}")
        end
      end
    end

    res.map{|r| r.size}.sum
  end
end