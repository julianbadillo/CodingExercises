require_relative '../lib/day3'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
puts Day3.part1(lines)