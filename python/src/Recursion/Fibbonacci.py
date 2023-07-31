#! /usr/bin/env python2.7
def fibonacci(n):
    if n == 0:
        return 0
    a,b,i = 0,1,0
    while i < n:
        a, b, i = b, a+b, i+1
    return b

def summing(arr):
    s = 0    
    for x in arr:
        s = s + x
    return s

def euclid(a, b):
    x, y = a, b
    while x != y:
        if x > y:
            x = x-y
        else:
            y = y-x
    return x

import sys
#This is the main
if __name__ == '__main__':
    """
    f = fibonacci(10)
    print("Testing Fibonacci:"+("fib(10)="+str(f)).rjust(16))

    arr = [0,3,2,12,23,78,23,34]
    print("Testing sum:"+("sum(arr)="+str(sum(arr))).rjust(16))

    x = euclid(128,64)
    print("Testing euclid: "+str(x))
    """
    print("Introduzca un entero:")
    n = int( input())
    while n > 0:
        print("Fibonacci of %d is %d"%(n,fibonacci(n)))
        print("Introduzca un entero:")
        n = int( input())
