class JB
  attr_accessor :x, :y, :z
  def initialize(x, y, z)
    @x = x
    @y = y
    @z = z
  end
  def to_s
    "[#{x}, #{y}, #{z}]"
  end
  def == other
    @x == other.x && @y == other.y && @z == other.z
  end

  def d_square(o)
    (@x - o.x)**2 + (@y - o.y)**2 + (@z - o.z)**2
  end
end

class JBLink
  attr_accessor :p1, :p2, :d_sq
  def initialize(p1, p2)
    @p1 = p1
    @p2 = p2
    @d_sq = p1.d_square p2
  end

  def == other
    # equal if points are the same
    ((@p1 == other.p1) && (@p2 == other.p2)) || ((@p1 == other.p2) && (@p2 == other.p1))
  end

  def to_s
    "#{p1}<->#{p2}"
  end

end

module Day8
  def self.parse_input(lines)
    lines.map do|line|
      parts = line.split(",").map(&:to_i)
      JB.new(*parts)
    end
  end
  def self.build_links(boxes)
    links = []
    for i in (0...boxes.length-1) do
      for j in (i + 1...boxes.length) do
        links.push(JBLink.new(boxes[i], boxes[j]))
      end
    end
    links.sort!{|a, b| a.d_sq <=> b.d_sq}
    return links
  end
  def self.connect_circuits(boxes, n)
    # get the closest n pairs
    links = self.build_links(boxes)[...n]
    circuits = []
    for link in links do
      # find if either point belongs to any group
      c1 = circuits.select{|g| g.include?(link.p1) }.first
      c2 = circuits.select{|g| g.include?(link.p2) }.first
      # remove the groups in case we need to merge them
      if c1
        circuits.delete(c1)
      else
        c1 = Set.new()
      end
      if c2
        circuits.delete(c2)
      else
        c2 = Set.new()
      end
      # add to the groups
      c1.add(link.p1)
      c2.add(link.p2)
      c1.merge c2
      # push them back
      circuits.push(c1)
    end
    
    # sort by size (largest to smalles)
    circuits.sort!{|a, b| b.length <=> a.length }
    # print "Result:\n"
    # print circuits.map{ |c| c.map(&:to_s).join(', ') }.join("\n")
    # far points remain on their own individual circuit
    return circuits
  end
  def self.part1(lines, n = 10)
    boxes = self.parse_input(lines)
    circuits = connect_circuits(boxes, n)
    circuits[...3].map(&:length).reduce(1){|a,b| a*b}
  end

  def self.is_last_connection(circuits, boxes)
    circuits.length == 1 && circuits.first.length == boxes.length
  end

  """Connect junction boxes until only one circuit, return the circuit"""
  def self.connect_until_joined(boxes)
    # get the closest n pairs
    links = self.build_links(boxes)
    circuits = []
    for link in links do
      # find if either point belongs to any group
      c1 = circuits.select{|g| g.include?(link.p1) }.first
      c2 = circuits.select{|g| g.include?(link.p2) }.first
      # remove the groups in case we need to merge them
      if c1
        circuits.delete(c1)
      else
        c1 = Set.new()
      end
      if c2
        circuits.delete(c2)
      else
        c2 = Set.new()
      end
      # add to the groups
      c1.add(link.p1)
      c2.add(link.p2)
      c1.merge c2
      # push them back
      circuits.push(c1)
      if self.is_last_connection(circuits, boxes)
        return link
      end
    end
  end

  def self.part2(lines)
    boxes = self.parse_input(lines)
    link = connect_until_joined(boxes)
    return link.p1.x * link.p2.x
  end
end
