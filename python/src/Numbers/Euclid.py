
"""
Euclid's algorithm to find a GCD
"""
def gcd(a, b):
    
    #greater number in a
    if a < b:
        a,b + b,a
    
    #reduce while we can
    while a % b != 0:
        a, b = b, a%b
    
    return b

"""
Euclid's algorithm in recursive way
"""
def gcdR(a, b):
    
    if a < b:
        return gcd(b%a,a)
    elif b < a:
        return gcd(a%b,b)
    else:
        return a

"""
Euclid's extended algorithm
"""
def euclidExt(a,b):
    #greater number in a
    if a < b:
        a,b + b,a
    x1 = 0
    y1 = 1
    x2 = 1
    y2 = 0
    
    #reduce while we can
    while a % b != 0:
        r = a % b
        q = a // b
        #keep track of quotients
        x1, x2 = x2 - q*x1, x1
        y1, y2 = y2 - q*y1, y1
        a = b
        b = r
    return b,x1,y1

if __name__ == '__main__':
    a = 120
    b = 23
    d,x,y = euclidExt(a,b)
    print(d,x,y)
    print( a*x +b*y, d)
