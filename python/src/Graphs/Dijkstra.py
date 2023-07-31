from Graph import Graph
from collections import deque
"""
Dijkstra's Algorithm
"""
def dijkstra( graph, startName ):
    #start vertex
    s = graph.vertex[startName]
    ##dictionary for distance
    distance = {s:0}
    ##set for visited
    visited = set()
    #simple queue for now
    queue = deque([s])
    
    while len(queue) > 0:
        #get the first (should be sorted from closer to farther)
        v = queue.popleft()
        #if not visited
        if v not in visited:
            #distance to v
            d = distance[v]
            #all edges
            for e in v.edges:
                u = e.toV
                #infinite
                if u not in distance:
                    distance[u] = d + e.cost
                #if better cost
                else:
                    d2 = distance[u]
                    if d + e.cost < d2:
                        distance[u] = d + e.cost
                #to queue
                queue.append(u)
        visited.add(v)
    return distance
                
def dijkstraPrev( graph, startName ):
    #start vertex
    s = graph.vertex[startName]
    ##dictionary for distance
    distance = {s:0}
    ##some other dictionary for previous vertex
    prev = {}
    ##set for visited
    visited = set()
    #simple queue for now
    queue = deque([s])
    
    while len(queue) > 0:
        #get the first (should be sorted from closer to farther)
        v = queue.popleft()
        #if not visited
        if v not in visited:
            #distance to v
            d = distance[v]
            #all edges
            for e in v.edges:
                u = e.toV
                #infinite
                if u not in distance:
                    distance[u] = d + e.cost
                    #keep record of previous vertex
                    prev[u] = v
                #if better cost
                else:
                    d2 = distance[u]
                    if d + e.cost < d2:
                        distance[u] = d + e.cost
                        #keep record of previous vertex
                        prev[u] = v
                #to queue
                queue.append(u)
        visited.add(v)
    return prev
    
def reconstructPath( start, end, prev ):
    path = deque()
    cur = end
    path.append(end)
    while cur is not start:
        cur = prev[cur]
        path.appendleft(cur)
    return path
    
    
"""
MAIN, testing
"""
g = Graph("my graph")
#vertex fro a to h 
for c in range(ord('A'), ord('I')):
    g.addVertex(chr(c))

g.addEdge("A","B", cost=2)
g.addEdge("A","E", cost=6)
g.addEdge("A","D", cost=3)
g.addEdge("B","D", cost=1)
g.addEdge("B","C", cost=2)
g.addEdge("D","C", cost=1)
g.addEdge("D","F", cost=4)
g.addEdge("D","G", cost=8)
g.addEdge("D","E", cost=2)
g.addEdge("E","C", cost=7)
g.addEdge("E","G", cost=1)
g.addEdge("G","F", cost=3)
g.addEdge("C","F", cost=1)
g.addEdge("D","H", cost=3)
g.addEdge("B","H", cost=2)
g.addEdge("E","H", cost=5)

dist = dijkstra(g,'A')
prev = dijkstraPrev(g,'A')
path = reconstructPath(g.vertex['A'], g.vertex['H'], prev)
#{A=0, B=2, C=4, D=3, E=5, F=5, G=6, H=4}
print([k.name+' '+str(v) for k, v in sorted(dist.items(), key=lambda p:p[0].name)])
print(path)


