import random
import sys
import Primes
import math
"""
"""
def modExp( e, n, m):
    r = 1
    p = e
    while n > 0:
        if n % 2 == 1:
            r = r*p % m
        p = p*p %m
        n = n//2
    return r
def modExpR( e, n, m):
    if n == 0:
        return 1
    if n % 2 == 0:
        return modExpR( e*e %m, n // 2, m) % m
    else:
        return e*modExpR( e*e %m , n // 2, m) % m
        
def testExp():
    for i in range(100):
        a = random.randint(1,0xffffff)
        b = random.randint(1,0xffffff)
        m = random.randint(1,0xffffff)
        print( "{} {} {}".format( a, b, m))
        print ( modExpR(a,b,m) )
        if modExp(a,b,m) != pow(a,b,m):
            print("Bruto, MOD EXP no funciona")
            break
        if modExpR(a,b,m) != pow(a,b,m):
            print("Bruto, MOD EXP R no funciona")
            break
        print(i)
    print("Funciona al PELO")
    
    
    
if __name__ == '__main__':
    print(pow(2,2006))
    encontre = False
    for i in range(1000):
        p = Primes.generateRandomPrime( 32 )
        a = random.randint(0xFFFFFFFFFF,0xFFFFFFFFFFFF)
        b = random.randint(0xFFFFFFFFFF,0xFFFFFFFFFFFF)
        
        n1 = pow(a,b,p)
        n2 = pow(a,b%(p-1),p)
        if n1 != n2:
            print("JEJEJEJE PAILA")
            encontre = True
            break
    print("acabe")
    if not encontre:
        print("No encontre")