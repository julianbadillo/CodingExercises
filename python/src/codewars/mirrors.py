from fractions import gcd

u, v = [int(i) for i in raw_input().split()]
if u == v:
    print("B", u)
else:
    g = gcd(u, v)
    l = (u / g) * v
    c = "C" if (u / g) % 2 == 1 else "A"
    print(c, l)
