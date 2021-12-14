
def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, 'r') as file:
        coords = set()
        instructions = []
        for l in file.readlines():
            l = l.strip()
            if ',' in l:
                p = l.split(',')
                coords.add((int(p[0]), int(p[1])))
            elif l:
                instructions.append(l)
        
        return coords, instructions


def fold(p, x = None, y = None):
    if x != None and p[0] > x:
        return (2*x - p[0], p[1])
    if y != None and p[1] > y:
        return (p[0], 2*y - p[1])
    return p

def fold_one(points, inst):
    x = None 
    y = None
    if "fold along y=" in inst:
        y = int(inst.split("=")[1])
    if "fold along x=" in inst:
        x = int(inst.split("=")[1])

    points2 = {fold(p2, x, y) for p2 in points}
    return len(points2)


def fold_all(points, inst):
    
    for i in inst:
        x = None 
        y = None
        if "fold along y=" in i:
            y = int(i.split("=")[1])
        if "fold along x=" in i:
            x = int(i.split("=")[1])
        points = {fold(p2, x, y) for p2 in points}
    
    return points

def print_points(points):
    max_x = max(p[0] for p in points)
    max_y = max(p[1] for p in points)

    for y in range(max_y + 1):
        for x in range(max_x + 1):
            if (x, y) in points:
                print("#", end="")
            else:
                print(" ", end="")
        print()


