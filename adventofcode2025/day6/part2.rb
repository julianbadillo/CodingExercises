require_relative '../lib/day6'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
puts Day6.part2(lines)