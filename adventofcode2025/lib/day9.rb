class Pt
  attr_accessor :x, :y
  def initialize(x, y)
    @x = x
    @y = y
  end
  def to_s
    "[#{x}, #{y}]"
  end
  def == other
    @x == other.x && @y == other.y
  end

  def area(o)
    ((@x - o.x).abs + 1) * ((@y - o.y).abs + 1)
  end
end

class Sq
  attr_accessor :p1, :p2, :area, :lx, :ly, :ux, :uy
  def initialize(p1, p2)
    @p1 = p1
    @p2 = p2
    @area = p1.area p2
    @lx = @p1.x < @p2.x ? @p1.x : @p2.x
    @ly = @p1.y < @p2.y ? @p1.y : @p2.y
    @ux = @p1.x > @p2.x ? @p1.x : @p2.x
    @uy = @p1.y > @p2.y ? @p1.y : @p2.y
  end

  def == other
    # equal if points are the same
    ((@p1 == other.p1) && (@p2 == other.p2)) || ((@p1 == other.p2) && (@p2 == other.p1))
  end
  def strict_contains?(p)
    @lx < p.x && p.x < @ux && @ly < p.y && p.y < @uy
  end
  
  def to_s
    "#{p1}<->#{p2}"
  end
end

# Edge
class Ed < Sq
  # Orientation, lower x, y, upper x, y
  attr_accessor :orient
  def initialize(p1, p2)
    super(p1, p2)
    # horizontal or vertical
    if @p1.x == @p2.x
      @orient = 'v'
    elsif @p1.y == @p2.y
      @orient = 'h'
    end
  end
  def contains?(p)
    @lx <= p.x && p.x <= @ux && @ly <= p.y && p.y <= @uy
  end
  def contains_edge(e)
    if @orient != e.orient
      return False
    end
    return @lx <= e.lx && e.ux <= @ux && @ly <= e.ly && e.uy <= @uy
  end
  def mid()
    return Pt.new((@ux + @lx) / 2.0, (@uy + @ly) / 2.0)
  end
  def cross?(e)
    # opposite orientations
    if @orient == e.orient 
      return false
    end
    if @orient == 'v'
      return e.lx < @p1.x && @p1.x < e.ux && @ly < e.p1.y && e.p1.y < @uy
    else
      return e.ly < @p1.y && @p1.y < e.uy && @lx < e.p1.x && e.p1.x < @ux
    end
  end
end

# Polygon
class Poly
  attr_accessor :pts, :edges
  def initialize(pts)
    @pts = pts.dup
    @edges = []
    for i in (1...pts.length) do
      @edges.push(Ed.new(pts[i-1], pts[i]))
    end
    # Roulding edge
    @edges.push(Ed.new(pts[-1], pts[0]))
  end

  # Evaluate if point in polygon - ray tracing
  def contains?(p)
    # if any of the edges include the point
    if edges.any?{|e| e.contains?(p) }
      return true
    end
    # find all vertical edges to the right (with greater x coord)
    # and where the Y coord is included
    edges = @edges.select{|e| 
      e.orient == 'v' &&
      p.x <= e.lx && 
      e.ly <= p.y && p.y <= e.uy
    }
    # if any of the edges include the point
    
    # even edges -> outside. odd edges -> inside
    return edges.length % 2 == 1
  end

  # evaluate if a square is contained within the polygon
  def contains_sq?(sq)
    # any corner is outside
    if !self.contains?(Pt.new(sq.lx, sq.ly)) ||
      !self.contains?(Pt.new(sq.lx, sq.uy)) ||
      !self.contains?(Pt.new(sq.ux, sq.ly)) ||
      !self.contains?(Pt.new(sq.ux, sq.uy))
      return false
    end
    # THe midpoint of the edge is strictly contained inside the square / there's a crossing
    if @edges.any?{|e| sq.strict_contains?(e.mid)}
      return false
    end
    return true
  end
end

module Day9
  
  def self.parse_input(lines)
    lines.map do|line|
      parts = line.split(",").map(&:to_i)
      Pt.new(*parts)
    end
  end
  
  def self.build_squares(pts)
    squares = []
    for i in (0...pts.length-1) do
      for j in (i + 1...pts.length) do
        squares.push(Sq.new(pts[i], pts[j]))
      end
    end
    # sort descending
    squares.sort!{|a, b| b.area <=> a.area}
    return squares
  end

  def self.build_unadjacent_squares(pts)
    squares = []
    for i in (0...pts.length-2) do
      for j in (i + 2...pts.length) do
        squares.push(Sq.new(pts[i], pts[j]))
      end
    end
    # sort descending
    squares.sort!{|a, b| b.area <=> a.area}
    return squares
  end
  def self.part1(lines)
    pts = self.parse_input(lines)
    sqs = self.build_squares(pts)
    return sqs.first.area
  end

  
  def self.part2(lines)
    pts = self.parse_input(lines)
    # polygon
    poly = Poly.new(pts)
    # squares
    sqs = self.build_unadjacent_squares(pts)
    for sq in sqs do
      print("#{sq.to_s}: #{sq.area}\n")
      # find the first that's completely contained
      if poly.contains_sq?(sq)
        # test all the points in the edges
        # if (sq.lx+1...sq.ux).any?{|x|!poly.contains?(Pt.new(x, sq.ly))}
        #   next
        # elsif (sq.lx+1...sq.ux).any?{|x|!poly.contains?(Pt.new(x, sq.uy))}
        #   next
        # elsif (sq.ly+1...sq.uy).any?{|y|!poly.contains?(Pt.new(sq.lx, y))}
        #   next
        # elsif (sq.ly+1...sq.uy).any?{|y|!poly.contains?(Pt.new(sq.ux, y))}
        #   next
        # else
        return sq.area
        # end
      end
    end
  end
end
