require_relative '../lib/day1'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
moves = lines.map { |l| [l[0], l[1..].to_i] }

puts Day1.part2(moves)