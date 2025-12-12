class M
  attr_accessor :dr, :dc
  def initialize(dr, dc)
    @dr=dr
    @dc=dc
  end
  def==(other)
    @dr==other.dr && @dc==other.dc
  end
end

module Day4
  MOVS = [
    M.new(-1, -1),
    M.new(-1, 0),
    M.new(-1, 1),
    M.new(0, 1),
    M.new(1, 1),
    M.new(1, 0),
    M.new(1, -1),
    M.new(0, -1),
  ]

  def self.is_roll_free(mat, n_rows, n_cols, r, c)
    # Count surrounding blocks, true if less than 4
    # print("#{r}, #{c}\n")
    if mat[r][c] == '.'
      return false
    end
    return MOVS.map{ |m| M.new(r+m.dr, c+m.dc) }
      .select{ |m| 
        0 <= m.dr && m.dr < n_rows && 0 <= m.dc && m.dc < n_cols}
      .map{ |m| mat[m.dr][m.dc] }
      .select { |c| c == '@'}
      .count < 4
  end

  def self.count_free_rolls(mat)
    n_rows = mat.length
    n_cols = mat[0].length
    free_rolls = 0
    for r in (0...n_rows)
      for c in (0...n_cols)
        if self.is_roll_free(mat, n_rows, n_cols, r, c)
          free_rolls += 1 
        end
      end
    end
    free_rolls
  end

  def self.find_free_rolls(mat)
    n_rows = mat.length
    n_cols = mat[0].length
    free_rolls = []
    for r in (0...n_rows)
      for c in (0...n_cols)
        if self.is_roll_free(mat, n_rows, n_cols, r, c)
          free_rolls.push(M.new(r, c)) 
        end
      end
    end
    free_rolls
  end

  def self.remove_free_rolls(mat)
    free_rolls = self.find_free_rolls(mat)
    free_rolls.each{|m| mat[m.dr][m.dc] = '.'}
    return free_rolls.length
  end

  def self.part2(mat)
    n = 0
    f = -1
    while f != 0 do
      f = self.remove_free_rolls(mat)
      n += f
    end
    return n
  end
end