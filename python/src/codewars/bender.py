# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.


class Bender:
    def __init__(self, startl, startc):
        # line and column
        self.l = startl
        self.c = startc
        self.d = "S"
        self.history = []
        self.breaker = False
        self.dchange = 1
        self.directions = "SENW"
        self.loop = False
        self.last_break = 0

    def next_move(self):
        # Process next move
        nextl = self.l
        nextc = self.c
        if self.d == "S":
            nextl += 1
        elif self.d == "N":
            nextl -= 1
        elif self.d == "E":
            nextc += 1
        elif self.d == "W":
            nextc -= 1

        # easy case, it can move, don't change direction, just position
        if mat[nextl][nextc] != "X" and mat[nextl][nextc] != "#":
            self.l = nextl
            self.c = nextc
            self.history.append((self.l, self.c, self.d, self.breaker, self.dchange))
        # Finds and obstacle,
        else:
            # If on breaker mode, delete obstacle and go on
            if self.breaker and mat[nextl][nextc] == "X":
                self.c = nextc
                self.l = nextl
                mat[nextl][nextc] = " "
                self.last_break = len(self.history)
                self.history.append(
                    (self.l, self.c, self.d, self.breaker, self.dchange)
                )

            # If not on breaker mode, change direction, not position.
            else:
                # loop al directions to find one
                for i in xrange(4):
                    if self.dchange == 1:
                        self.d = self.directions[i]
                    else:
                        self.d = self.directions[-(i + 1)]
                    nextl = self.l
                    nextc = self.c
                    if self.d == "S":
                        nextl += 1
                    elif self.d == "N":
                        nextl -= 1
                    elif self.d == "E":
                        nextc += 1
                    elif self.d == "W":
                        nextc -= 1
                    # if not an obstacle or breaker mode
                    if (self.breaker and mat[nextl][nextc] == "X") or (
                        mat[nextl][nextc] != "X" and mat[nextl][nextc] != "#"
                    ):
                        return False
                # if not found
                self.loop = True
                return True

        # check for loop
        if self.has_loop():
            self.loop = True
            return True

        # If end reached
        if mat[self.l][self.c] == "$":
            return True
        # Finds a direction changer - change direction
        elif mat[self.l][self.c] in "SNWE":
            self.d = mat[self.l][self.c]
        # Finds a beer - flip breaker mode
        elif mat[self.l][self.c] == "B":
            self.breaker = not self.breaker
        # Finds a direction Inverter
        elif mat[self.l][self.c] == "I":
            self.dchange *= -1
        # FInds a teleporter, change position, not direction (look for the
        #  other teleporter)
        elif mat[self.l][self.c] == "T":
            # find the other gate
            l, c = self.find_gate()
            self.l = l
            self.c = c

        return False

    def find_gate(self):
        for l in xrange(len(mat)):
            for c in xrange(len(mat[l])):
                if mat[l][c] == "T" and not (self.l == l and self.c == c):
                    return l, c

    def has_loop(self):
        if len(self.history) < 2:
            return False
        # Find the last one in the history
        # from the time we did the last wall break
        # On2 in number of moves
        last = self.history[-1]
        return last in self.history[self.last_break : -1]


l, c = [int(i) for i in raw_input().split()]

mat = []
startc = 0
startl = 0

for i in xrange(l):
    line = raw_input().strip()
    c = line.find("@")
    if c != -1:
        startc = c
        startl = i
    mat.append(list(line))

bender = Bender(startl, startc)

while not bender.next_move():
    pass

if bender.loop:
    print("LOOP")
else:
    dir_name = {"S": "SOUTH", "N": "NORTH", "E": "EAST", "W": "WEST"}
    print("\n".join(dir_name[h[2]] for h in bender.history))
