from math import floor, log2
from sys import stdin

charval = lambda c: ord(c)-97 if 97 <= ord(c) <= 122 else ord(c) - 65 + 26

def find_rumsack(s1, s2):
    l1, l2 = 0, 0
    for c1, c2 in zip(s1, s2):
        l1 |= (1<<charval(c1))
        l2 |= (1<<charval(c2))
    return floor(log2(l1 & l2)) + 1

sum_rumsacks = lambda lines: sum(find_rumsack(l[0:n//2], l[n//2:n]) for l in lines if (n := len(l)))

def find_badges(elves):
    lt = [0, 0, 0]
    for i, elf in enumerate(elves):
        for c in elf:
            lt[i] |= (1<<charval(c))
    return floor(log2(lt[0] & lt[1] & lt[2])) + 1

sum_badges = lambda lines: sum(find_badges(lines[i:i+3]) for i in range(0, len(lines), 3))

data = [l.strip() for l in stdin.readlines()]
print(sum_rumsacks(data))
print(sum_badges(data))
