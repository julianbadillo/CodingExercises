from collections import deque
from SortedSet import SortedSet
"""
Graph Lybrary
"""

class Vertex:
    #initialization
    def __init__(self, name):
        self.name = name
        #set of sorted edges from lower to greater
        self.edges = set() ##SortedSet(comparison=lambda edge:edge.cost)
    def __repr__(self):
        return self.name
class Edge:
    # origin and destiny vertex
    def __init__(self, name, fromV, toV, cost=0):
        self.name = name
        self.fromV = fromV
        self.toV = toV
        self.cost = cost

class Graph:   
    #initialization
    def __init__(self, name):
        self.name = name
        # vertex map (name to vertex)
        self.vertex = {}
        # edges map (name to edge)
        self.edges = {}
        
    #add a vertex
    def addVertex(self, name ):
        v = Vertex(name)
        self.vertex[name] = v
    
    #adds an edge
    def addEdge(self, nameFrom, nameTo, name=None, cost=0):
        #Default edge name eN
        if name is None:
            numberEdges = len(self.edges.items())
            name = 'e'+str(numberEdges)
            
        fromV = self.vertex[nameFrom]
        toV = self.vertex[nameTo]
        #add to edge map
        e = Edge(name,fromV,toV,cost)
        self.edges[name] = e
        #add to edge collection on vertex
        fromV.edges.add( e )
    
    #prints graph info
    def printGraph(self):
        print(self.name)
        print(' '.join([n for n, v in self.vertex.items()]))
        print(' '.join([n+'('+e.fromV.name+','+e.toV.name+')' for n, e in self.edges.items()]))

    def dfs(self, startName):
        s = self.vertex[startName]
        visited = set()
        #put first in stack
        stack = [s]
        #while stack has something
        while len(stack)  > 0:
            #destack
            v = stack.pop()
            #print vertex
            if v not in visited:
                print( v.name+', ', end='')
                #mark as visited
                visited.add(v)
                #everyone to stack
                for e in v.edges:
                    toV = e.toV
                    if toV not in visited:
                        stack.append(toV)
        print()        
    
    def bfs(self, startName):
        s = self.vertex[startName]
        visited = set()
        #put first in stack
        queue = deque([s])
        #while stack has something
        while len(queue)  > 0:
            #dequeue
            v = queue.popleft()
            #print vertex
            if v not in visited:
                print( v.name+', ', end='')
                #mark as visited
                visited.add(v)
                #everyone to stack
                for e in v.edges:
                    toV = e.toV
                    if toV not in visited:
                        queue.append(toV)
        print()        
        
"""
MAIN, testing
"""
def test():
    g = Graph("my graph")
    g.addVertex('A')
    g.addVertex('B')
    g.addVertex('C')
    g.addVertex('D')
    g.addVertex('E')
    g.addEdge('A','B')
    g.addEdge('A','C')
    g.addEdge('B','C')
    g.addEdge('D','A')
    g.addEdge('A','D')
    g.addEdge('C','E')
    g.addEdge('E','B')

    g.printGraph()

    g.dfs('A')
    g.bfs('A')
"""
test()
"""