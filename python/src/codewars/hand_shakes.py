"""
Problem Statement
    Consider a meeting of n businessmen sitting around a circular table. To start the meeting,
    they must shake hands. Each businessman shakes the hand of exactly one other businessman.
    All handshakes happen simultaneously. We say that the shake is perfect if no arms cross
    each other. Given an int n, return the number of perfect shakes that exist for n
    businessmen. See examples for further clarification.
Definition
Class:	HandsShaking
Method:	countPerfect
Parameters:	int
Returns:	long
Method signature:	long countPerfect(int n)
(be sure your method is public)
Notes
-	Businessmen are distinguishable. Rotating a perfect shake can yield a different perfect shake 
    (see example 1).
Constraints
-	n will be between 2 and 50, inclusive.
-	n will be even.

Examples
0)
2
Returns: 1

Two businessmen have only one possibility - just to shake each other's hand.
1)
4
Returns: 2
Two out of three possible shakes are perfect.

2)
8
Returns: 14
"""


def perfectHandShakes(n):
    n = n // 2
    f = [0 for i in range(n)]
    # easy cases
    f[0] = 1
    f[1] = 2
    # from easy to difficult
    for i in range(2, n):
        # s = 0
        # i the splitting factor
        for j in range(1, i):
            # sum left plus right
            f[i] += f[j - 1] * f[i - j - 1]
        # add borders
        f[i] += 2 * f[i - 1]
    print(f)
    return f[n - 1]


def totalHandShakes(n):
    # (n/2)!
    fact = 1
    for i in range(n // 2 + 1, n + 1):
        fact = fact * i
    # (n).. / 2^(n/2)
    return fact // pow(2, n // 2)


perfectHandShakes(8)
