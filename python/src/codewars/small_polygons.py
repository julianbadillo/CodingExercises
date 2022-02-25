import sys, math, random
import numpy as np

def area(p, poly):
    """
    trapezium method
    """
    s = 0
    n = len(poly)
    for i in range(n):
        s += (p[poly[(i + 1) % n]][1] + p[poly[i]][1]) * (p[poly[(i + 1) % n]][0] - p[poly[i]][0]) / 2.0

    return abs(s)


def score(pts, sol):
    """
    Calculate score
    """
    score = 0
    for shape in sol:
        score += area(pts, shape)
            
def kmeans(pts, cen, cl):
    """
    K-means algorith
    """
    go = True
    while go:
        #calculate closest centroid
        for i in range(len(pts)):
            #against all centroids, distance vector
            dist = ((pts[i]-cen)**2).sum(1)#sum by rows
            #get the min index
            cl[i] = dist.argmin()
            
        #re-calculate centroids
        change = False
        for i in range(len(cen)):
            #only the ones closest to i centroid
            sel = (cl == i)
            
            #ban centroids with lss than 3 points
            if sel.sum() < 3:
                if cen[i,0] != -1000:
                    cen[i,0] = -1000
                    cen[i,1] = -1000
                    change = True
            else:
                #average by columns
                temp = np.average(pts[sel], 0)
                #check if changed
                change = not np.allclose(temp, cen[i])
                cen[i] = temp
        #go if at least one centroid changed
        go = change


def buildShapes(pts):
    #choose centroids at random
    cen = pts[random.sample(range(len(pts)),N)]

    #closest centroid index for each
    cl = np.array([0 for i in range(len(pts))])

    #clustering
    kmeans(pts, cen, cl)

    #build solution
    sol = []
    ind = np.array(range(len(pts)))
    for i in range(N):
        #wich indices have i
        sel = ind[cl == i]
        #skip empty shapes
        if len(sel) == 0:
            continue
        #calculate arctangetns against centroid
        tan = np.arctan2(pts[sel,0]-cen[i][0], pts[sel,1]-cen[i][1])
        #sort them from a centroid
        order = np.argsort(tan)
        #sort them
        sel = sel[order]
        sol.append(sel)
    
    return sol


def choosePolygons(points, N):
    pts = np.zeros((len(points)/2, 2))
    #list of points
    for i in range(len(points)/2):
        pts[i] = np.array(points[2*i:2*i+2])

    bestscore = False
    bestsol = None
    #try several times until best score
    for i in range(20):
        sol = buildShapes(pts)
        #score the solution
        sc = score(pts, sol)
        if not bestscore or sc < bestscore:
            bestsol = sol
            bestscore = sc

    #the result
    res = []
    for shape in bestsol:
        res.append(' '.join(str(j) for j in shape))
    
    return res

#read NP
NP = int(sys.stdin.readline())
#read points
points = [int(sys.stdin.readline()) for i in range(NP)]
N = int(sys.stdin.readline())

r = choosePolygons(points, N)

#print output
print len(r)
for rl in r:
    print rl


