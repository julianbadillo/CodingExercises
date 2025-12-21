require_relative '../lib/day10'


lines = STDIN.each_line.map(&:chomp).reject(&:empty?)
puts Day10.part1(lines)