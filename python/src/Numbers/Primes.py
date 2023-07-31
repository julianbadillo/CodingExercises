import random
import sys
import math
"""
    tests if a given number is prime, using
    Fermat Little Theorem:
    if p is prime
    a^(p-1) = 1 (mod p)
    With a in [1,p-1]
"""
def isPrime( n, tries=10 ):
    
    for i in range(tries):
        #pick random a between 1 and p-1
        a = random.randint(1,n-1)
        #power
        p = pow(a,n-1,n)
        #print( "{}^{} (mod {}) = {}".format(a,n-1,n,p))
        if p != 1:
            return False
    
    return True
"""
    Generates a random prime of rougly the given bits
"""
def generateRandomPrime( bits ):
    bottom = pow(2,bits-1)
    top = pow(2,bits)
    p = random.randint(bottom,top)
    while not isPrime( p):
        p = random.randint(bottom,top)
    return p

if __name__ == '__main__':
    
    p = generateRandomPrime( 128 )
    print ( p, p.bit_length() )
    p = generateRandomPrime( 256 )
    print ( p, p.bit_length() )
    p = generateRandomPrime( 512 )
    print ( p, p.bit_length() )
    p = generateRandomPrime( 1024 )
    print ( p, p.bit_length() )
    
    print( "Escriba un numero")
    n = int(input())
    
    while n>1:
        if isPrime(n):
            print(n,"es primo")
        else:
            print(n,"es compuesto")
        print( "Escriba un numero")
        n = int(input())
    
    
    