def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, "r") as file:
        list = [int(x) for x in file.readlines()]
        return list


def larger_than_prev(input):
    """
    Counts how many measurements are larger than the previous
    @param :input: array[int]

    """
    return sum(1 if input[i - 1] < input[i] else 0 for i in range(1, len(input)))


def larger_than_prev_window(input):
    """
    Same, but a sliding window
    """
    return sum(1 if input[i - 3] < input[i] else 0 for i in range(3, len(input)))
