"""
A building has n floors.
It has an elevator that is controlled by two buttons only: UP and DOWN.

By pressing the UP button, the elevator will go exactly a floors up.
By pressing the DOWN button, the elevator will go exactly b floors down.

If the elevator is commanded to go lower than the first floor or higher than the n-th floor, it will refuse to move and remain on its current floor.

The elevator starts on the k-th floor. Count how many times the buttons should be pressed to move the elevator to the m-th floor.
"""

import sys
import math


def find_min_bfs(n, a, b, k, m):
    """
    BFS exploration, first found its optimal
    :param n:
    :param a:
    :param b:
    :param k:
    :param m:
    :return:
    Killer case 10000 63 37 1 2
    10000 1 1 1 10000
    10000 2 1 1 10000
    10000 3 2 1 9999
    """
    min_steps = {}
    queue = [(0, 0)]

    while len(queue) > 0:
        i, j = queue.pop(0)
        # base case - found
        p = k + a * i - b * j
        if p == m:
            min_steps[p] = i + j
            return i + j

        # out of bounds
        if i >= n or j >= n or p > n or p < 1:
            continue

        # has been explored already
        if p in min_steps:
            continue
        # mark explored
        else:
            min_steps[p] = i + j

        # cannot move from here
        if p + a > n and p - b < 1:
            continue

        # recursive cases
        r1, r2 = None, None
        # can move up - recursive
        if p + a <= n:
            queue.append((i + 1, j))
        if p - b >= 1:
            queue.append((i, j + 1))


def find_min(n, a, b, k, m):
    """
    Brute force exploration O(n^2)
    Doesnt validate borders
    killer case: 17 16 6 1 3
    """
    for i in range(n):
        for j in range(n):
            mx = k + a * i - b * j
            if mx == m:
                return i + j
    return None


n, a, b, k, m = [int(i) for i in raw_input().split()]

# easy case k == m
if k == m:
    print(0)
# easy case if m > k (need to go up) and m - k % a == 0
elif m > k and (m - k) % a == 0:
    print((m - k) / a)
# easy case, if k > m (need to go down) and l - m % b == 0
elif k > m and (k - m) % b == 0:
    print((k - m) / b)
# easy case if we cannot move the elevator
elif k + a > n and k - b < 1:
    print("IMPOSSIBLE")
else:
    m = find_min_bfs(n, a, b, k, m)
    if m is None:
        print("IMPOSSIBLE")
    else:
        print(m)
