import sys
import math
import bisect
import time

# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.


class Calc:
    def __init__(self, id, s, l):
        self.id = id
        self.start = s
        self.length = l
        self.end = s + l
        self.overlaps = 0

    def __str__(self):
        return "[%s , %s]" % (self.start, self.end)


def has_overlapping(calcs):
    """
    Assume sorted by starting day
    """
    for i in xrange(len(calcs)-1):
        calc1, calc2 = calcs[i], calcs[i+1]
        if calc1.end > calc2.start:
            return True
    return False


def get_overlappings(calcs):
    # clear overlapping count
    for c in calcs: c.overlaps = 0

    for i in xrange(len(calcs)):
        c1 = calcs[i]
        for j in xrange(i+1, len(calcs)):
            c2 = calcs[j]
            if c1.start <= c2.start < c1.end:
                c1.overlaps += 1
                c2.overlaps += 1
            else:
                break

n = int(raw_input())

calcs = []

for i in xrange(n):
    j, d = [int(j) for j in raw_input().split()]
    calcs.append(Calc(i, j, d))

t1 = time.time()
calcs = sorted(calcs, key=lambda c: c.start)
t2 = time.time()
#print "Time", t2-t1

# Ideas, dynamic programming?

# 1M slots, its feasible to make O(n)
# Marking slots is not feasible 1k*1M
# 100K slots, we can do O(n*logn) but not O(n^2)
#  if they were sorted by starting date?
# I can validate in O(n) if there is overlapping
# greedy? remove the min needed to eliminate overlapping?
# can be done on O(n log n)
# that will need building a graph of incidence = # of overllaped

t1 = time.time()
if has_overlapping(calcs):
    get_overlappings(calcs)

    # sort by number of overlaps
    calcs = sorted(calcs, key=lambda c: c.overlaps)

    calcs2 = []
    calcs2_keys = []

    for i in xrange(len(calcs)):
        new_calc = calcs[i]

        # bisect for insertion
        y = bisect.bisect(calcs2_keys, new_calc.start)

        collides = False
        if len(calcs2) < 1:
            pass
        elif y == len(calcs2):
            # only test previous
            c1 = calcs2[y-1]
            c2 = new_calc
            if c1.start == c2.start or c1.end > c2.start:
                collides = True
                # print "This ", c1
                # print "Collides with ", c2

        elif y == 0:
            # only test next
            c1 = new_calc
            c2 = calcs2[y]
            if c1.start == c2.start or c1.end > c2.start:
                collides = True
                # print "This ", c1
                # print "Collides with ", c2

        else:
            # test after and before
            c1 = calcs2[y-1]
            c2 = new_calc
            c3 = calcs2[y]
            if c1.start == c2.start or c2.start < c1.end:
                collides = True
                # print "This ", c1
                # print "Collides with ", c2

            elif c1.start == c3.start or c3.start < c2.end:
                collides = True
                # print "This ", c2
                # print "Collides with ", c3

        if not collides:
            calcs2.insert(y, new_calc)
            calcs2_keys.insert(y, new_calc.start)
            # print (calcs2_keys)

    # substitute
    calcs = calcs2
t2 = time.time()

#print "Time", t2-t1

print len(calcs)
