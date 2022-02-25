"""
Given two integers A and B, you have to find the largest power of A that divides
B! with no remainder.
You have to find the largest integer value of X such that A^X divides B! where
B! = B x (B-1) x (B-2) x ... x 2 x 1.

2 <= A, B <= 10^7
600K primes lower than 10^7 (10M)

"""
from distutils.log import error
import sys
import math


def get_factors(x, r):
    """
    O(sqrt(n)) - speed up with sieve
    :param x:
    :param r:
    :return:
    """
    i = 2
    while i <= x:
        while x % i == 0:
            if i not in r:
                r[i] = 0
            r[i] += 1
            x /= i
        i += 1
    return r


def get_prime_power(x, prime):
    # how many times does it have a 2?
    # log r (b!)
    # 8! = 2 * 3 * 4 * 5 * 6 * 7 * 8
    # once every even 8 / 2 = 4
    # once every four 8 / 4 = 2
    # once every eight 8 / 8 = 1
    r = 0
    power = prime
    while power <= x:
        r += x / power
        power *= prime
    return r


a, b = [int(i) for i in raw_input().split()]

# Write an action using print()
# To debug: print >> sys.stderr, "Debug messages..."

a_factors = get_factors(a, {})
b_factors = {}
maxe = None
for d in a_factors:
    b_factors[d] = get_prime_power(b, d)
maxe = min(b_factors[d] / a_factors[d] for d in a_factors)
error(a_factors)
error(sys.stderr, b_factors)
print(maxe)
# Strategy 1: get prime divisors (and powers of ) A
# O(sqrt(A)) - O(e^n/2)
# pd = [ {d, p} ... ]   12 = [{2,2}, {3,1}]
# Traverse all B!  making also a list of primes and prime divisors
# 5! = 5, 4, 3, 2, 1 = [{2, 3}, {3, 1}, {5, 1}]
# killer case: 13512 3411598
