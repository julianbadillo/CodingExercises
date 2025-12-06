module Day1
  def self.part1(moves, start = 50)
    pos = start
    zeros = 0

    moves.each do |dir, n|
      step = dir == 'R' ? n : -n
      pos = (pos + step) % 100
      zeros += 1 if pos == 0
    end
    # Return
    zeros
  end

  def self.part2(moves, start = 50)
    pos = start
    zeros = 0

    moves.each do |dir, n|
      if dir == 'R'
        zeros += (pos + n) / 100
        pos = (pos + n) % 100
      else
        if pos == 0
          zeros += n / 100
        elsif pos <= n
          zeros += 1 + (n - pos) / 100
        end
        pos = (pos - n) % 100
      end
    end
    # Return
    zeros
  end
end
