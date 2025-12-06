require_relative '../lib/day2'

lines = ARGF.each_line.map(&:chomp).reject(&:empty?)
ranges = lines[0].split(',').map{ |r| r.split('-').map(&:to_i) }
# puts ranges
puts Day2.add_invalid_p1(ranges)