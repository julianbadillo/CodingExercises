"""
Implementacion del algortimo de Karatsube
"""

def log_2( n ):
    """
    number of bits of an integer
    """
    m = 0
    while n > 0:
        n >>= 1
        m += 1
    return m

def karatsuba_mult( x, y):
    """
    using the algorithm of karatsuba
    """
    #number of bits
    n = max( log_2(x), log_2(y) )
    #base case
    if n <= 1:
        return x * y
    #next even
    n = n if n % 2 == 0 else n+1
    #compute split x and y
    a = x >> n/2
    b = x - (a << n/2)
    c = y >> n/2
    d = y - (c << n/2)
    #recursive call    
    ac = karatsuba_mult(a,c)
    bd = karatsuba_mult(b,d)
    #ad + bc = (a+b)(c+d) - ac - bd
    s = karatsuba_mult(a+b,c+d) - ac - bd
    #polinomial correcting exponentials
    return (ac << n) + (s << n/2) + bd

print karatsuba_mult(132, 1001)
print karatsuba_mult(1010, 22)
