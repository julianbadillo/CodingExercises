require_relative '../lib/day7'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
puts Day7.part1(lines)