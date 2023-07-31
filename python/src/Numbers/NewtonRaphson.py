#!/usr/env/bin python
'''
Created on Mar 24, 2013

@author: jabadillo
'''
import math
def sqrt_newton_raphson(a, epsilon=0.0000001):
    x = 1.0
    while(abs(a-x*x) > epsilon):
        x = (x + a/x)/2.0
        print(x)
    return x


print(sqrt_newton_raphson(99))
print(math.sqrt(99))