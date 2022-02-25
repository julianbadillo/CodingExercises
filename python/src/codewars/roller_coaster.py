from distutils.log import error
import sys
import math

# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

seats, rides, n = [int(i) for i in raw_input().split()]
line = [int(raw_input()) for x in xrange(n)]

# Simulate the roller coaster
money = 0
ride = []
pos1 = 0  # first in the queue
pos2 = 0
i = 0

money_history = {}
rides_history = {}

while i < rides:
    pos2 = pos1
    in_ride = 0
    # while enough space in the roller coaster
    if pos1 < 10:
        print >> sys.stderr, "pos1 ", pos1
    while True:
        in_ride += line[pos1]
        money += line[pos1]
        pos1 = (pos1 + 1) % n

        # break when reach capacity or exhausted all line
        if in_ride + line[pos1] > seats or pos1 == pos2:
            break
    i += 1

    if pos1 in money_history:
        error("Cycle found", pos1, i, money)
        error("History:", rides_history[pos1], money_history[pos1])
        remaining = rides - i
        delta_money = money - money_history[pos1]
        delta_rides = i - rides_history[pos1]
        cycles = remaining / delta_rides

        i += cycles * delta_rides
        money += cycles * delta_money
        # improvement -- add history

    else:
        money_history[pos1] = money
        rides_history[pos1] = i


print(money)
