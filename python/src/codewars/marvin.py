from distutils.log import error
import sys
import math
from itertools import product
from pprint import pprint


class MarvinSolver:
    def __init__(self):
        """
        Initialization
        :return:
        """
        # position
        self.row = 0
        self.col = 0
        self.direction = 0
        self.path = []
        self.moves = []
        self.distance = {}
        # starting
        self.st_row, self.st_col = 0, 0
        # read input
        parts = [int(i) for i in raw_input().split()]
        # print >> sys.stderr, ' '.join(str(s) for s in parts)
        (
            self.n_rows,
            self.n_cols,
            self.n_rounds,
            self.ex_row,
            self.ex_col,
            self.n_clones,
            self.n_add_els,
            self.n_els,
        ) = parts
        self.elevs = set()
        self.add_elevs = set()
        self.strategy = {}
        # elevators
        for i in range(self.n_els):
            p = [int(i) for i in raw_input().split()]
            # print >> sys.stderr, ' '.join(str(s) for s in p)
            self.elevs.add((p[0], p[1]))

    def set_start(self):
        self.st_row, self.st_col = self.row, self.col
        self.add_elevators()
        self.bfs_path()
        self.fill_strategy()

        # distance + 3*add_elevators + 3*blocks
        cost = self.get_cost()
        print >> sys.stderr, "COST = ", cost

        # if exceeds number of rounds
        while self.optimize_elevs() and cost > self.n_rounds:
            # optimize elevators
            # add them vs. move new ones closer (aligned)
            self.bfs_path()
            self.fill_strategy()
            cost = self.get_cost()
            print >> sys.stderr, "COST = ", cost

        # try to add elevators if remain
        while cost > self.n_rounds and self.n_add_els > (len(self.add_elevs)):
            self.add_best_elevator()
            self.bfs_path()
            self.fill_strategy()
            cost = self.get_cost()
            print >> sys.stderr, "COST = ", cost

        # if exceeds number of clones
        if len(self.strategy) > self.n_clones:
            print >> sys.stderr, "NOT OPTIMAL - not enough clones"
            # remove blocks

    def get_cost(self):
        return 1 + min(
            self.distance[(self.ex_row, self.ex_col, "R")],
            self.distance[(self.ex_row, self.ex_col, "L")],
        )

    def update_position(self, r, c, dr):
        """
        :param r:
        :param c:
        :param dr:
        :return:
        """
        self.row = int(r)
        self.col = int(c)
        self.direction = dr[0]

    def add_elevators(self):
        """
        Adds elevators on each missing floor
        :return:
        """
        # The ones without elevator
        elev_levels = set([e[0] for e in self.elevs])
        wo_levels = [
            i for i in range(self.n_rows) if i != self.ex_row and i not in elev_levels
        ]

        # add an elevator in each non-connected level
        for row in wo_levels:
            # calculate col for elevator
            col = (self.st_col + self.ex_col) / 2
            # add elevator
            self.add_elevs.add((row, col))

    def optimize_elevs(self):
        """
        Aligns added elevators
        :return:
        """
        # Get added elevators in path
        # option 1 - look got RIGHT - UP - LEFT pattern with UP in an added elevator

        for i in range(len(self.path) - 3):
            # get three
            if all(i == j for i, j in zip(self.moves[i : i + 3], ["R", "U", "L"])):
                # get if the given point is an elevator made by us
                r, c, dr = self.path[i + 1]
                if (r, c) in self.add_elevs:
                    # move elevator to the left (we need to recalculate strategy)
                    self.add_elevs.remove((r, c))
                    self.add_elevs.add((r, c - 1))
                    return True
            elif all(i == j for i, j in zip(self.moves[i : i + 3], ["L", "U", "R"])):
                # get if the given point is an elevator made by us
                r, c, dr = self.path[i + 1]
                if (r, c) in self.add_elevs:
                    # move elevator to the right (we need to recalculate strategy)
                    self.add_elevs.remove((r, c))
                    self.add_elevs.add((r, c + 1))
                    return True

        for i in range(len(self.path) - 4):
            # get four
            if all(i == j for i, j in zip(self.moves[i : i + 4], ["R", "U", "U", "L"])):
                # get if the given point is an elevator made by us
                r1, c1, dr1 = self.path[i + 1]
                r2, c2, dr2 = self.path[i + 2]
                if (r1, c1) in self.add_elevs and (r2, c2) in self.add_elevs:
                    # move elevator to the left (we need to recalculate strategy)
                    self.add_elevs.remove((r1, c1))
                    self.add_elevs.add((r1, c1 - 1))
                    self.add_elevs.remove((r2, c2))
                    self.add_elevs.add((r2, c2 - 1))
                    return True
            elif all(
                i == j for i, j in zip(self.moves[i : i + 4], ["L", "U", "U", "R"])
            ):
                # get if the given point is an elevator made by us
                r1, c1, dr1 = self.path[i + 1]
                r2, c2, dr2 = self.path[i + 2]
                if (r1, c1) in self.add_elevs and (r2, c2) in self.add_elevs:
                    # move elevator to the right (we need to recalculate strategy)
                    self.add_elevs.remove((r1, c1))
                    self.add_elevs.add((r1, c1 + 1))
                    self.add_elevs.remove((r2, c2))
                    self.add_elevs.add((r2, c2 + 1))
                    return True

        return False

    def add_best_elevator(self):
        """
        Adds elev on the point of most impact
        :return:
        """
        # The ones without elevator
        elev_levels = set([e[0] for e in self.add_elevs])
        wo_levels = [
            i for i in range(self.n_rows) if i != self.ex_row and i not in elev_levels
        ]

        # BRUTE FORCE
        best_elev = (wo_levels[0], 0)
        best_cost = self.get_cost()
        for i in wo_levels:
            for j in range(self.n_cols):
                self.add_elevs.add((i, j))
                self.bfs_path()
                self.fill_strategy()
                cost = self.get_cost()
                if cost < best_cost:
                    best_cost = cost
                    best_elev = (i, j)
                self.add_elevs.remove((i, j))

        print >> sys.stderr, "Best Elev =", best_elev, "Best Cost =", best_cost
        self.add_elevs.add(best_elev)

    def bfs_path(self):
        """
        BFS over the map Of min distance with existing elevators
        :return:
        """
        # max distance
        self.distance = {
            (r, c, d): 15 * 15 * 100
            for r, c, d in product(range(self.n_rows), range(self.n_cols), ["R", "L"])
        }
        prev = {}
        move = {}
        visited = set()
        queue = [(self.st_row, self.st_col, "R")]
        self.distance[(self.st_row, self.st_col, "R")] = 0

        while queue:
            # p = queue.pop(0)
            # TODO get the one with min distance - OPTIMIZE WITH HEAP
            mind = min(self.distance[p] for p in queue)
            i = 0
            while self.distance[queue[i]] != mind:
                i += 1
            p = queue.pop(i)
            r, c, dr = p
            if p in visited:
                continue
            visited.add(p)
            # neighbours
            ds = self.distance[p]
            p_up = (r + 1, c, dr)
            # there is an already-built elevator
            if (
                r < self.ex_row
                and (r, c) in self.elevs
                and ds + 1 < self.distance[p_up]
            ):
                self.distance[p_up] = ds + 1
                prev[p_up] = p
                move[p_up] = "U"
                queue.append(p_up)
            # there is an add elevator (d + 3 turns until the next clone)
            elif (
                r < self.ex_row
                and (r, c) in self.add_elevs
                and ds + 4 < self.distance[p_up]
            ):
                self.distance[p_up] = ds + 4
                prev[p_up] = p
                move[p_up] = "U"
                queue.append(p_up)
            else:  # No elevator
                if dr == "R":
                    # same direction
                    p_right = (r, c + 1, dr)
                    if c < self.n_cols - 1 and ds + 1 < self.distance[p_right]:
                        self.distance[p_right] = ds + 1
                        prev[p_right] = p
                        move[p_right] = "R"
                        queue.append(p_right)
                    # opposite direction
                    p_left = (r, c - 1, "L")
                    if c > 0 and ds + 4 < self.distance[p_left]:
                        self.distance[p_left] = ds + 4
                        prev[p_left] = p
                        move[p_left] = "L"
                        queue.append(p_left)
                elif dr == "L":
                    # same direction
                    p_left = (r, c - 1, dr)
                    if c > 0 and ds + 1 < self.distance[p_left]:
                        self.distance[p_left] = ds + 1
                        prev[p_left] = p
                        move[p_left] = "L"
                        queue.append(p_left)
                    # opposite direction
                    p_right = (r, c + 1, "R")
                    if c < self.n_cols - 1 and ds + 4 < self.distance[p_right]:
                        self.distance[p_right] = ds + 4
                        prev[p_right] = p
                        move[p_right] = "R"
                        queue.append(p_right)
        # print >> sys.stderr, prev
        # reverse path
        # """
        p_ex1 = (self.ex_row, self.ex_col, "R")
        p_ex2 = (self.ex_row, self.ex_col, "L")
        if p_ex1 in prev and p_ex2 in prev:
            # the closest
            p_ex = p_ex1 if self.distance[p_ex1] < self.distance[p_ex2] else p_ex2
        elif p_ex1 in prev:
            p_ex = p_ex1
        elif p_ex2 in prev:
            p_ex = p_ex2
        else:
            raise Exception("End non reachable")

        self.path = [p_ex]
        self.moves = []
        while self.path[0] != (self.st_row, self.st_col, "R"):
            self.moves.insert(0, move[self.path[0]])
            self.path.insert(0, prev[self.path[0]])
            # """

    def fill_strategy(self):
        # now go and fill strategy
        direction = "R"
        self.strategy = {}
        for move, p in zip(self.moves, self.path):
            # if I should go up but no elevator
            r, c, dr = p
            if move == "U" and (r, c) not in self.elevs:
                self.strategy[p] = "ELEVATOR"
            # if wrong direction, block
            elif move == "R" and direction == "L":
                self.strategy[p] = "BLOCK"
                direction = move
            elif move == "L" and direction == "R":
                self.strategy[p] = "BLOCK"
                direction = move

    def next_move(self):
        """
        :return: Decision
        """
        move = "WAIT"
        # anything decided on strategy
        if (self.row, self.col, self.direction) in self.strategy:
            move = self.strategy[(self.row, self.col, self.direction)]
            # remove from strategy
            del self.strategy[(self.row, self.col, self.direction)]
        return move


marvin = MarvinSolver()
first = True
while True:
    # lead clone position
    p = raw_input().split()
    marvin.update_position(p[0], p[1], p[2])

    if first:
        first = False
        # start
        marvin.set_start()

    print(marvin.next_move())
    error(marvin.path)
    error(marvin.moves)
    error(marvin.strategy)
