require_relative '../lib/day6'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
puts Day6.part1(lines)