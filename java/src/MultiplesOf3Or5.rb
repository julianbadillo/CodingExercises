def sigma(x)
	x * (x + 1) / 2
end

def solution(number)
	# put your solution here
	3 * sigma((number - 1) / 3) + 5 * sigma((number - 1) / 5) - 15 * sigma((number - 1) / 15)
end


puts solution(ARGV[0].to_i)