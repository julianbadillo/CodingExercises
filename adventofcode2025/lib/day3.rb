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

  def self.max_digit_n(line, k=12)  
    n = line.length
    idx = (0...k).to_a
    num = idx.map{|i| line[i] }.join('').to_i
    max_num = num
    
    while true
      # Get the next sub-sequence
      (0...k).reverse_each do |i|
        idx[i] += 1
        if idx[i] <= n - k + i
          # all subsequent
          for j in (1...k - i)
            idx[i + j] = idx[i] + j
          end
          break
        end
      end
      if idx[-1] == n
        break
      end
      num = idx.map{|i| line[i] }.join('').to_i
      max_num = num if num > max_num
    end
    max_num
  end

  def self.max_digit_r(line, k=12, start=0)
    # Base cases
    n = line.length - start
    if n == k
      return line[start..].to_i
    end
    if k == 1
      return line[start..].each_char.map{|i| i.to_i}.max
    end
    max_num = 0
    for i in (0..n-k)
      num = 10**(k-1) * line[start + i].to_i + self.max_digit_r(line, k-1, start + i + 1)
      max_num = num if num > max_num
    end
    return max_num
  end

  def self.max_digit_dp(line, k=12)
    # same approach as recursive, but with dynamic prog.
    # memory
    max_digit_mat = {}
    n = line.length
    for start in (0..n-1)
      max_digit_mat[start] = {}
      # fill base case for all starts, k = 1
      max_digit_mat[start][1] = line[start..].each_char.map{|i| i.to_i}.max
    end
    for k2 in (2..k)
      for start in (0...n - k2 + 1)
        num = 10**(k2-1) * line[start].to_i
        # look anything starting after 'start', with a shorter length
        num += (start + 1..n - k2 + 1).map {|s2| max_digit_mat[s2][k2 - 1]}.max
        max_digit_mat[start][k2] = num
      end
    end

    return max_digit_mat.values.map{ |v| v.values.max }.max

  end

  def self.part2(lines)
    lines.map{ |l| self.max_digit_dp(l) }.sum
  end
end