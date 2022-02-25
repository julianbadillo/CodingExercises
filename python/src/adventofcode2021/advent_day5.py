
from itertools import *
class Vent:
    size = 5
    def __init__(self, line) -> None:
        p1, p2 = line.split(" -> ")
        self.x1, self.y1 = (int(x) for x in p1.split(","))
        self.x2, self.y2 = (int(x) for x in p2.split(","))
        # deltas - if non-zero
        self.w, self.h = self.x2 - self.x1, self.y2 - self.y1
        self.dx = 0 if self.w == 0 else self.w // abs(self.w)
        self.dy = 0 if self.h == 0 else self.h // abs(self.h)
        self.l = max(abs(self.w), abs(self.h))
        
    def mark(self, map):
        for i in range(self.l + 1):
            map[self.x1 + self.dx * i][self.y1 + self.dy * i] += 1
    
def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, 'r') as file:
        list = [line.strip() for line in file.readlines()]
        return list

def load_vents(lines):
    return [Vent(line) for line in lines]
    
def overlap_hv(vents):
    w = max(max(v.x1, v.x2) for v in vents)
    h = max(max(v.y1, v.y2) for v in vents)
    map = [[0 for j in range(h + 1)] for i in range(w + 1)]

    for v in vents:
        # only horizontal or vertical
        if v.h == 0 or v.w == 0:
            v.mark(map)

    # count overlapping points
    return sum(sum(1 if x > 1 else 0 for x in row) for row in map)

def overlap_hv(vents):
    w = max(max(v.x1, v.x2) for v in vents)
    h = max(max(v.y1, v.y2) for v in vents)
    map = [[0 for j in range(h + 1)] for i in range(w + 1)]

    # all vents
    for v in vents:
        v.mark(map)

    # count overlapping points
    return sum(sum(1 if x > 1 else 0 for x in row) for row in map)


if __name__ == "__main__":
    lines = read_file("test/advent_day5_test.txt")
    vents = load_vents(lines)
    s = overlap_hv(vents)