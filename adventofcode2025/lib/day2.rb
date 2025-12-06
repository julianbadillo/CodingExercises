module Day2
  def self.is_invalid_p1(id)
    id_s = id.to_s
    n = id_s.length
    if n % 2 == 1
      return false
    end
    p1 = id_s[0...n/2]
    p2 = id_s[n/2...]
    return p1 == p2
  end
  def self.count_invalid_p1(a, b)
    (a..b).select{ |n| self.is_invalid_p1(n) }.count
  end
  def self.add_invalid_p1(ranges)
    ranges.map{ |a, b|
      (a..b).select{ |n| self.is_invalid_p1(n) }.sum
    }.sum
  end

  PRIMES = [2, 3, 5, 7, 11]

  # Is invalid if it's composed by a repeated string
  def self.is_invalid_p2(id)
    id_s = id.to_s
    n = id_s.length
    # only dividers of length
    PRIMES.select{ |p| n % p == 0 }.each do |p|
      m = n / p
      # Split id_s in p parts
      parts = (0...p).map { |i| id_s[i*m...(i+1)*m] }
      # all the same
      if parts.all?(parts.first)
        return true
      end
    end
    return false
  end
  def self.add_invalid_p2(ranges)
    ranges.map{ |a, b|
      (a..b).select{ |n| self.is_invalid_p2(n) }.sum
    }.sum
  end
end