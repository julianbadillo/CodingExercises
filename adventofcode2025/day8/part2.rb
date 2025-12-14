require_relative '../lib/day8'


lines = STDIN.each_line.map(&:chomp).reject(&:empty?)
puts Day8.part2(lines)