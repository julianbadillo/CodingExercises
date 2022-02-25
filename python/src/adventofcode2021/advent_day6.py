def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, "r") as file:
        list = [int(x) for x in file.read().split(",")]
        return list


def lanternfish_progression(lf_list, days):

    while days > 0:
        days -= 1
        for i in range(len(lf_list)):
            if lf_list[i] == 0:
                lf_list[i] = 6
                lf_list.append(8)
            else:
                lf_list[i] -= 1
        yield lf_list


def lanterfish_progression_dict(lf_list, days):
    lf_dict = [0 for i in range(9)]
    # dictionary
    for l in lf_list:
        # count in days
        lf_dict[l] += 1

    while days > 0:
        days -= 1
        # the ones about to reproduce
        rep = lf_dict[0]
        # move each group to one less
        for k in range(1, 9):
            lf_dict[k - 1] = lf_dict[k]
        # the ones that just reproduced - reset to 6
        lf_dict[6] += rep
        # create new ones
        lf_dict[8] = rep
        yield sum(lf_dict)
