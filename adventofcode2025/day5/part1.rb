require_relative '../lib/day5'

lines = ARGF.each_line.map(&:chomp)
puts Day5.part1(lines)