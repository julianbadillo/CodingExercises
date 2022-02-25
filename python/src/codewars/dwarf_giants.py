# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.


def get_height(tree, parent):
    roots = get_roots(tree, parent)

    # recursive height
    maxh = 1
    for r in roots:
        maxh = max(maxh, height(tree, r))
    return maxh


def get_roots(tree, parent):
    # traverse all nodes
    roots = []
    for n in tree:
        if n not in parent:
            roots.append(n)
    return roots


def height(tree, r):
    h = 1
    if r in tree:
        for child in tree[r]:
            h = max(h, 1 + height(tree, child))
    return h

n = int(raw_input())  # the number of relationships of influence
tree = {}
parent = {}
for i in xrange(n):
    # x: a relationship of influence between two people (x influences y)
    x, y = [int(j) for j in raw_input().split()]
    if x in tree:
        tree[x].append(y)
    else:
        tree[x] = [y]
    parent[y] = x

h = get_height(tree, parent)
# The number of people involved in the longest succession of influences
print h
