
from os import X_OK


def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, 'r') as file:
        list = [line.split() for line in file.readlines()]
        return list

def calculate_final_position(input):
    """
        Follows the instructions
        @param :input: array[int]

    """
    y = 0
    x = 0
    for inst in input:
        if inst[0] == 'down':
            y += int(inst[1])
        elif inst[0] == 'up':
            y -= int(inst[1])
        elif inst[0] == 'forward':
            x += int(inst[1])
    return (x, y)


def calculate_final_position_aim(input):
    """
        Follows the instructions with aim
        @param :input: array[int]

    """
    y = 0
    x = 0
    aim = 0
    for inst in input:
        if inst[0] == 'down':
            aim += int(inst[1])
        elif inst[0] == 'up':
            aim -= int(inst[1])
        elif inst[0] == 'forward':
            x += int(inst[1])
            y += aim * int(inst[1])
    return (x, y)
