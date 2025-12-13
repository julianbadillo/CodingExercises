module Day7
  def self.split_beams(beams, splitters)
    res = Set.new
    split_c = 0
    beams.each do |beam|
      # split
      if splitters[beam] == "^"
        res.add(beam-1)
        res.add(beam+1)
        split_c += 1
      else
        res.add(beam)
      end
    end
    return res, split_c
  end

  def self.part1(lines)
    start = lines[0].index("S")
    beams = Set[start]
    split_c = 0
    lines[1..].each do |line|
      beams, t = self.split_beams(beams, line)
      split_c += t
    end
    return split_c
  end

  def self.split_timelines(beams, splitters)
    res = Hash.new(0)
    split_c = 0
    beams.each do |beam, n|
      # split
      if splitters[beam] == "^"
        res[beam + 1] += n
        res[beam - 1] += n
        split_c += 1
      else
        res[beam] += n
      end
    end
    return res, split_c
  end

  def self.part2(lines)
    start = lines[0].index("S")
    beams = {start => 1}
    split_c = 0
    lines[1..].each do |line|
      beams, t = self.split_timelines(beams, line)
      split_c += t
    end
    return beams.values.sum
  end
end
