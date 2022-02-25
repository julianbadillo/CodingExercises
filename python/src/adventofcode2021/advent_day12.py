class Node:
    def __init__(self, name):
        self.name = name
        self.visited = False
        self.big = "A" <= name[0] <= "Z"
        self.edges = []

    def link(self, node):
        self.edges.append(node)


def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, "r") as file:
        list = [line.strip().split("-") for line in file.readlines()]
        return list


def build_graph(data):
    """
    Makes the graph
    """
    graph = {}
    for name1, name2 in data:
        if name1 not in graph:
            graph[name1] = Node(name1)
        if name2 not in graph:
            graph[name2] = Node(name2)
        n1 = graph[name1]
        n2 = graph[name2]
        n1.link(n2)
        n2.link(n1)
    return graph


def count_paths(graph):
    start = graph["start"]
    return count_paths_r(start, [])


def count_paths_r(node, path):
    # base cases
    if node.name == "end":
        return 1
    # if a small node and already in the path
    if not node.big and node.name in path:
        return 0
    count = 0
    # do recursion
    for node2 in node.edges:
        if node2.name == "start":
            continue
        count += count_paths_r(node2, path + [node.name])
    return count


def count_paths2(graph):
    start = graph["start"]
    return count_paths_r2(start, [])


def any_small_duplicate(path):
    return any("a" <= x[0] <= "z" and path.count(x) == 2 for x in path)


def count_paths_r2(node, path):
    # base cases
    if node.name == "end":
        # print(path + ['end'])
        return 1

    # if a small node and already in the path and there's a duplicate
    if not node.big and node.name in path and any_small_duplicate(path):
        return 0
    count = 0
    # do recursion
    for node2 in node.edges:
        if node2.name == "start":
            continue
        count += count_paths_r2(node2, path + [node.name])
    return count
