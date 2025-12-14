require_relative '../lib/day8'


n = ARGV.length >= 1 ? ARGV[0].to_i : 10
lines = STDIN.each_line.map(&:chomp).reject(&:empty?)
puts n
puts Day8.part1(lines, n)