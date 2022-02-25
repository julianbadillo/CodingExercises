# // #############
# // #01.4.7.0.34#
# // ###2#5#8#1###
# //   #3#6#9#2#
# //   #########

# /// Adj matrix + energy
# /// 0 -> 1(1)
# /// 1 -> 2(2), 4(2)
# /// 2 -> 3(1), 4(2)
# /// 3 -> 2(1)
# /// 4 -> 1(2), 2(2), 5(2), 7(2)
# /// 5 -> 4(2), 6(1), 7(2)
# /// 6 -> 5(1),
# /// 7 -> 4(2), 5(2), 8(2), 10(2)
# /// 8 -> 7(2), 9(1), 10(2)
# /// 9 -> 8(1)
# /// 10 -> 7(2), 8(2), 11(2), 13(2)
# /// 11 -> 10(2), 12(1), 13(2)
# /// 12 -> 11(1)
# /// 13 -> 10(2), 11(2), 14(1)
# /// 14 -> 13(1)


# FalseAdj matrix + energy
class Edge:
    def __init__(self, dest, eng) -> None:
        self.dest = dest
        self.eng = eng


cave = [
    [Edge(1, 1)],
    [Edge(2, 2), Edge(4, 2)],
    [Edge(3, 1), Edge(4, 2)],
    [Edge(2, 1)],
    [Edge(1, 2), Edge(2, 2), Edge(5, 2), Edge(7, 2)],
    [Edge(4, 2), Edge(6, 1), Edge(7, 2)],
    [
        Edge(5, 1),
    ],
    [Edge(4, 2), Edge(5, 2), Edge(8, 2), Edge(10, 2)],
    [Edge(7, 2), Edge(9, 1), Edge(10, 2)],
    [Edge(8, 1)],
    [Edge(7, 2), Edge(8, 2), Edge(11, 2), Edge(13, 2)],
    [Edge(10, 2), Edge(12, 1), Edge(13, 2)],
    [Edge(11, 1)],
    [Edge(10, 2), Edge(11, 2), Edge(14, 1)],
    [Edge(13, 1)],
]

#  #############
#  #01.2.3.4.56#
#  ###7#8#9#0###
#    #1#2#3#4#
#    #5#6#7#8#
#    #9#0#1#2#
#    #########


large_cave = [
    # 0
    [Edge(1, 1)],
    # 1
    [Edge(0, 1), Edge(2, 2), Edge(7, 2)],
    # 2
    [Edge(1, 2), Edge(7, 2), Edge(8, 2), Edge(3, 2)],
    # 3
    [Edge(2, 2), Edge(8, 2), Edge(9, 2), Edge(4, 2)],
    # 4
    [Edge(3, 2), Edge(9, 2), Edge(10, 2), Edge(5, 2)],
    # 5
    [Edge(4, 2), Edge(10, 2), Edge(6, 1)],
    # 6
    [
        Edge(5, 1),
    ],
    # 7
    [Edge(1, 2), Edge(11, 1), Edge(2, 2)],
    # 8
    [Edge(2, 2), Edge(12, 1), Edge(3, 2)],
    # 9
    [Edge(3, 2), Edge(13, 1), Edge(4, 2)],
    # 10
    [Edge(4, 2), Edge(14, 1), Edge(5, 2)],
    # 11
    [Edge(7, 1), Edge(15, 1)],
    # 12
    [Edge(8, 1), Edge(16, 1)],
    # 13
    [Edge(9, 1), Edge(17, 1)],
    # 14
    [Edge(10, 1), Edge(18, 1)],
    # 15
    [Edge(11, 1), Edge(19, 1)],
    # 16
    [Edge(12, 1), Edge(20, 1)],
    # 17
    [Edge(13, 1), Edge(21, 1)],
    # 18
    [Edge(14, 1), Edge(22, 1)],
    # 19
    [Edge(15, 1)],
    # 20
    [Edge(16, 1)],
    # 21
    [Edge(17, 1)],
    # 22
    [Edge(18, 1)],
]


def print_pods(pods):

    pos = {p: i for i, p in enumerate(pods)}
    print("#############")
    print("#", end="")
    if 0 in pos:
        print(f"{pos[0]}", end="")
    else:
        print(".", end="")
    if 1 in pos:
        print(f"{pos[1]}", end="")
    else:
        print(".", end="")
    print(".", end="")

    if 4 in pos:
        print(f"{pos[4]}", end="")
    else:
        print(".", end="")
    print(".", end="")
    if 7 in pos:
        print(f"{pos[7]}", end="")
    else:
        print(".", end="")
    print(".", end="")
    if 10 in pos:
        print(f"{pos[10]}", end="")
    else:
        print(".", end="")
    print(".", end="")
    if 13 in pos:
        print(f"{pos[13]}", end="")
    else:
        print(".", end="")
    if 14 in pos:
        print(f"{pos[14]}", end="")
    else:
        print(".", end="")
    print("#")
    # // #01.4.7.0.34#

    print("###", end="")
    if 2 in pos:
        print(f"{pos[2]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 5 in pos:
        print(f"{pos[5]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 8 in pos:
        print(f"{pos[8]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 11 in pos:
        print(f"{pos[11]}", end="")
    else:
        print(".", end="")
    print("###")
    # // ###2#5#8#1###
    print("  #", end="")
    if 3 in pos:
        print(f"{pos[3]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 6 in pos:
        print(f"{pos[6]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 9 in pos:
        print(f"{pos[9]}", end="")
    else:
        print(".", end="")
    print("#", end="")
    if 12 in pos:
        print(f"{pos[12]}", end="")
    else:
        print(".", end="")
    print("#")
    print("   #######")

    # //   #3#6#9#2#
    # //   #########


def explore(pods):
    start = tuple(pods)
    energy = {}
    queue = []
    finals = set()
    queue.append(start)
    energy[start] = 0
    count = 0

    # BFS - Dijsktra
    while len(queue) > 0:
        pods2 = queue.pop(0)
        if is_final(pods2):
            print("Final position found!!!", pods2)
            finals.add(pods2)
        e = energy[pods2]

        # each pod
        for (id, pos) in enumerate(pods2):

            # possible moves
            for edge in cave[pos]:
                # if it's not forbidden
                if not is_valid_move(id, edge.dest, pods2):
                    continue
                pod_eng = 10 ** (id // 2)  # depending on the pod moved
                new_energy = e + edge.eng * pod_eng  # energy required
                # new positions
                new_pods = list(pods2)
                new_pods[id] = edge.dest
                new_pods = tuple(new_pods)
                # if no path or better energy path
                if new_pods not in energy:
                    energy[new_pods] = new_energy
                    queue.append(new_pods)
                elif new_energy < energy[new_pods]:
                    # queue.append(new_pods)
                    energy[new_pods] = new_energy
        count += 1
        # if count % 100 == 0:
        #     print("Count ", count)
    # get the combinations with lowest energy
    return min(energy[pods2] for pods2 in finals)


def explore_large(pods):
    start = tuple(pods)
    energy = {}
    queue = []
    finals = set()
    queue.append(start)
    energy[start] = 0
    count = 0

    # BFS - Dijsktra
    while len(queue) > 0:
        pods2 = queue.pop(0)
        if is_final_large(pods2):
            print("Final position found!!!", pods2)
            finals.add(pods2)
        e = energy[pods2]

        # each pod
        for (id, pos) in enumerate(pods2):

            # possible moves
            for edge in large_cave[pos]:
                # if it's not forbidden
                if not is_valid_move_large(id, edge.dest, pods2):
                    continue
                pod_eng = 10 ** (id // 4)  # depending on the pod moved
                new_energy = e + edge.eng * pod_eng  # energy required
                # new positions
                new_pods = list(pods2)
                new_pods[id] = edge.dest
                new_pods = tuple(new_pods)
                # if no path or better energy path
                if new_pods not in energy:
                    energy[new_pods] = new_energy
                    queue.append(new_pods)
                elif new_energy < energy[new_pods]:
                    # queue.append(new_pods)
                    energy[new_pods] = new_energy
        count += 1
        if count % 100000 == 0:
            print("Count ", count)
    # get the combinations with lowest energy
    return min(energy[pods2] for pods2 in finals)


def score(pods, steps):
    """
    Scores a solution
    """
    energy = 0
    for (id, dest) in steps:
        print_pods(pods)
        if not is_valid_move(id, dest, pods):
            raise Exception(f"Not valid move {id}: {dest}")
        pod_eng = 10 ** (id // 2)  # depending on the pod moved
        origin = pods[id]
        # add energy of move
        edge = filter(lambda e: e.dest == dest, cave[origin]).__next__()
        energy += pod_eng * edge.eng
        # change the position of the pods
        pods[id] = dest
    print_pods(pods)
    if not is_final(pods):
        raise Exception("Not a final state")

    return energy


def score_large(pods, steps):
    """
    Scores a solution based on the larger cave
    """
    energy = 0
    for (id, dest) in steps:
        # print_pods(pods)
        if not is_valid_move_large(id, dest, pods):
            raise Exception(f"Not valid move {id}: {dest}")
        pod_eng = 10 ** (id // 4)  # depending on the pod moved
        origin = pods[id]
        # add energy of move
        try:
            edge = next(filter(lambda e: e.dest == dest, large_cave[origin]))
            energy += pod_eng * edge.eng
            # change the position of the pods
            pods[id] = dest
        except StopIteration:
            raise Exception(f"Edge not found {id}: ({origin} -> {dest})")
    # print_pods(pods)
    if not is_final_large(pods):
        raise Exception("Not a final state")

    return energy


def is_final(pods):

    # // A A B B C C D D
    # // 0 1 2 3 4 5 6 7

    # // A: 2, 3
    if pods[0] != 2 and pods[0] != 3:
        return False

    if pods[1] != 2 and pods[1] != 3:
        return False

    # // B: 5, 6
    if pods[2] != 5 and pods[2] != 6:
        return False

    if pods[3] != 5 and pods[3] != 6:
        return False
    # // C: 8, 9
    if pods[4] != 8 and pods[4] != 9:
        return False

    if pods[5] != 8 and pods[5] != 9:
        return False

    # // D: 11, 12
    if pods[6] != 11 and pods[6] != 12:
        return False
    if pods[7] != 11 and pods[7] != 12:
        return False

    return True


def is_final_large(pods):
    # // #############
    # // #01.2.3.4.56#
    # // ###7#8#9#0###
    # //   #1#2#3#4#
    # //   #5#6#7#8#
    # //   #9#0#1#2#
    # //   #########
    # //
    for id, pos in enumerate(pods):
        # A
        if 0 <= id <= 3 and pos not in [7, 11, 15, 19]:
            return False
        # B
        elif 4 <= id <= 7 and pos not in [8, 12, 16, 20]:
            return False
        # C
        elif 8 <= id <= 11 and pos not in [9, 13, 17, 21]:
            return False
        # D
        elif 12 <= id <= 15 and pos not in [10, 14, 18, 22]:
            return False
    return True


def is_valid_move(id, end, pods):

    # if it's not already occupied.
    if end in pods:
        return False
    start = pods[id]

    # // #############
    # // #...........#
    # // ###A#B#C#D###
    # //   #A#B#C#D#
    # //   #########

    # // #############
    # // #01.4.7.0.34#
    # // ###2#5#8#1###
    # //   #3#6#9#2#
    # //   #########

    # A -> cannot move non-A slots, except to get out
    if id == 0 or id == 1:
        if end == 5 and start != 6:
            return False
        if end == 8 and start != 9:
            return False
        if end == 11 and start != 12:
            return False
        # cannot move if occupied by a non-A
        if end == 2 and 3 in pods and pods.index(3) != 0 and pods.index(3) != 1:
            return False

    # B
    if id == 2 or id == 3:
        if end == 2 and start != 3:
            return False
        if end == 8 and start != 9:
            return False
        if end == 11 and start != 12:
            return False
        if end == 5 and 6 in pods and pods.index(6) != 2 and pods.index(6) != 3:
            return False

    # C
    if id == 4 or id == 5:
        if end == 2 and start != 3:
            return False
        if end == 5 and start != 6:
            return False
        if end == 11 and start != 12:
            return False
        if end == 8 and 9 in pods and pods.index(9) != 4 and pods.index(9) != 5:
            return False
    # D
    if id == 6 or id == 7:
        if end == 2 and start != 3:
            return False
        if end == 5 and start != 6:
            return False
        if end == 8 and start != 9:
            return False
        if end == 11 and 12 in pods and pods.index(12) != 6 and pods.index(12) != 7:
            return False
    return True


def is_valid_move_large(id, end, pods):

    # if it's not already occupied.
    if end in pods:
        return False
    start = pods[id]

    # // #############
    # // #01.2.3.4.56#
    # // ###7#8#9#0###
    # //   #1#2#3#4#
    # //   #5#6#7#8#
    # //   #9#0#1#2#
    # //   #########
    # //

    # A -> cannot move non-A slots, except to get out
    if 0 <= id <= 3:
        if start < end and end in [
            # 7, 11, 15, 19, # A
            8,
            12,
            16,
            20,  # B
            9,
            13,
            17,
            21,  # C
            10,
            14,
            18,
            22,  # D
        ]:
            return False
        # cannot move in if occupied by a non-A
        if (
            start < end
            and end
            in [
                7,
                11,
                15,  # A
                # 8, 12, 16, # B
                # 9, 13, 17, # C
                # 10, 14, 18, # D
            ]
            and any(
                p in pods and pods.index(p) not in [0, 1, 2, 3] for p in [11, 15, 19]
            )
        ):
            return False

    # B -> cannot move non-B slots, except to get out
    if 4 <= id <= 7:
        if (
            end
            in [
                7,
                11,
                15,
                19,  # A
                # 8, 12, 16, 20, # B
                9,
                13,
                17,
                21,  # C
                10,
                14,
                18,
                22,  # D
            ]
            and start < end
        ):
            return False
        # cannot move if occupied by a non-A
        if (
            start < end
            and end
            in [
                # 7, 11, 15, # A
                8,
                12,
                16,  # B
                # 9, 13, 17, # C
                # 10, 14, 18, # D
            ]
            and any(
                p in pods and pods.index(p) not in [4, 5, 6, 7] for p in [12, 16, 20]
            )
        ):
            return False
    # C -> cannot move non-C slots, except to get out
    if 8 <= id <= 11:
        if (
            end
            in [
                7,
                11,
                15,
                19,  # A
                8,
                12,
                16,
                20,  # B
                # 9, 13, 17, 21, # C
                10,
                14,
                18,
                22,  # D
            ]
            and start < end
        ):
            return False
        # cannot move if occupied by a non-A
        if (
            start < end
            and end
            in [
                # 7, 11, 15, # A
                # 8, 12, 16, # B
                9,
                13,
                17,  # C
                # 10, 14, 18, # D
            ]
            and any(
                p in pods and pods.index(p) not in [8, 9, 10, 11] for p in [13, 17, 21]
            )
        ):
            return False
    # D -> cannot move non-D slots, except to get out
    if 12 <= id <= 15:
        if (
            end
            in [
                7,
                11,
                15,
                19,  # A
                8,
                12,
                16,
                20,  # B
                9,
                13,
                17,
                21,  # C
                # 10, 14, 18, 22 # D
            ]
            and start < end
        ):
            return False
        # cannot move if occupied by a non-A
        if (
            start < end
            and end
            in [
                # 7, 11, 15, # A
                # 8, 12, 16, # B
                # 9, 13, 17, # C
                10,
                14,
                18,  # D
            ]
            and any(
                p in pods and pods.index(p) not in [12, 13, 14, 15]
                for p in [14, 18, 22]
            )
        ):
            return False

    return True


if __name__ == "__main__":
    # #############
    # #...........#
    # ###B#C#B#D###
    #   #D#C#B#A#
    #   #D#B#A#C#
    #   #A#D#C#A#
    #   #########

    # #############
    # #01.2.3.4.56#
    # ###7#8#9#0###
    #   #1#2#3#4#
    #   #5#6#7#8#
    #   #9#0#1#2#
    #   #########

    pods = [
        # A
        10,
        14,
        17,
        22,
        # B
        7,
        13,
        16,
        21,
        # C
        9,
        12,
        18,
        19,
        # D
        8,
        11,
        15,
        20,
    ]
    r = explore_large(pods)
    print(f"Min energy = {r}")
