require_relative '../lib/day4'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
puts Day4.part2(lines)