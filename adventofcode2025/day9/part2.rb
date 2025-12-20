require_relative '../lib/day9'


lines = STDIN.each_line.map(&:chomp).reject(&:empty?)
puts Day9.part2(lines)