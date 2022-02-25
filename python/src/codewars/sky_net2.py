import sys


class Bike:
    # s: the motorbikes' speed
    # x: x coordinate of the motorbike
    # y: y coordinate of the motorbike
    # a: indicates whether the motorbike is activated "1" or detroyed "0"

    def __init__(self):
        pass

    def update(self, s, l):
        self.s = s
        self.x, self.y, self.a = l


def decision(road, bikes):

    # TODO first strategy -> just jump if something is ahead
    # accelerate if stopped
    if bikes[0].s <= 1:
        return "SPEED"

    for bike in bikes:
        # Active bikes
        if bike.a == 1:
            lane = road[bike.y][bike.x + 1 :]  # lane ahead
            # there is a hole ahead - JUMP
            print >> sys.stderr, lane
            if "0" in lane[: bike.s - 1]:
                return "JUMP"
            elif bike.s < len(lane) and lane[bike.s - 1] == "0":  # last block
                return "UP"
    return "WAIT"


# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

m = int(raw_input())  # the amount of motorbikes to control
v = int(raw_input())  # the minimum amount of motorbikes that must survive

# create road
road = [raw_input(), raw_input(), raw_input(), raw_input()]
# bikes
bikes = [Bike() for i in xrange(m)]

# game loop
while True:
    s = int(raw_input())  # the motorbikes' speed
    for i in xrange(m):
        bikes[i].update(s, [int(j) for j in raw_input().split()])

    # Write an action using print
    # ...........0
    # 012345678901
    for b in bikes:
        print >> sys.stderr, "x=%s, y=%s" % (b.x, b.y)
    for l in road:
        print >> sys.stderr, l

    print(decision(road, bikes))
