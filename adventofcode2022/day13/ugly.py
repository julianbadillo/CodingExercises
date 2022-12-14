import sys

def compare(n1, n2):
    if type(n1) == int and type(n2) == int:
        return -1 if n1 < n2 else 1 if n1 > n2 else 0
    if type(n1) == list and type(n2) == list:
        for x, y in zip(n1, n2):
            if (d := compare(x, y)) != 0:
                return d
        return -1 if len(n1) < len(n2) else 1 if len(n1) > len(n2) else 0
    if type(n1) == int and type(n2) == list:
        return compare([n1], n2)
    if type(n1) == list and type(n2) == int:
        return compare(n1, [n2])

data = sys.stdin.read().splitlines()
r1 = 0
d1 = [[2]]
d2 = [[6]]
c1, c2 = 0, 0
for i in range(0, len(data), 3):
    n1 = eval(data[i])
    n2 = eval(data[i+1])
    print(n1, n2)
    if compare(n1, n2) < 0:
        r1 += (i//3 + 1)
        print(f"ok {i//3 + 1}")
    if compare(n1, d1) < 0:
        c1 += 1
    if compare(n1, d2) < 0:
        c2 += 1
    if compare(n2, d1) < 0:
        c1 += 1
    if compare(n2, d2) < 0:
        c2 += 1
r2 = (c1 + 1)*(c2 + 2)
print(f"Answer I {r1}")
print(f"Answer II {r2}")
