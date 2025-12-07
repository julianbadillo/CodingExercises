module Day3
  def self.part1(lines)
    lines.map{ |l| self.max_digit(l) }.sum
  end
  def self.max_digit(line)
    max_num = 0
    (0...line.length).each do |i|
      (i + 1...line.length).each do |j|
        num = (line[i] + line[j]).to_i
        max_num = num if num > max_num
      end
    end
    max_num
  end

  def self.max_digit_n(line, n=12)  
    m = line.length
    idx = (0...n).to_a
    num = idx.map{|i| line[i] }.join('').to_i
    max_num = num
    
    while true
      # Get the next sub-sequence
      (0...n).reverse_each do |i|
        idx[i] += 1
        if idx[i] <= m - n + i
          # all subsequent
          for j in (1...n - i)
            idx[i + j] = idx[i] + j
          end
          break
        end
      end
      if idx[-1] == m
        break
      end
      num = idx.map{|i| line[i] }.join('').to_i
      max_num = num if num > max_num
    end
    max_num
  end

  def self.part2(lines)
    lines.map{ |l| self.max_digit_n(l) }.sum
  end
end