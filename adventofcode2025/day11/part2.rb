require_relative '../lib/day11'


lines = STDIN.each_line.map(&:chomp).reject(&:empty?)
puts Day11.part2(lines)