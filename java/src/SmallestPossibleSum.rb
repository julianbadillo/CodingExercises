def gcd(x, y)
	a, b = [x, y].min, [x, y].max
	while b % a != 0
		b, a = a, b % a
	end
	return a
end

# return smallest possible sum of all numbers in Array
def solution(numbers)
	return numbers.length * numbers.reduce{|x, y| gcd(x, y)}
end

arr = ARGV.collect{|x| x.to_i }
puts solution(arr)