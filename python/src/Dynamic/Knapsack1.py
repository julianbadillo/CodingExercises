"""
KNAP SACK WITN MULTIPLE ITEMS
C array of costs
G array of gain
W maximum capacity
"""
def knapsack1(C, G, W):
    #first we build MaxG array
    MaxG = [0 for i in range(0,W+1)] 
    #easy case already dealt with
    #MaxG[0] = 0
    #from easy to complex
    for w in range(1, W+1):
        #choose item that maximices gain
        max = 0
        for i in range(len(C)):
            #if item fits on knapsack
            if C[i] <= w:
                #gain
                g = G[i] + MaxG[w-C[i]]
                if g > max:
                    max = g
        MaxG[w] = max
    return MaxG[W]

def knapsack2(C, G, W):
    #first we build MaxG array
    MaxG = [0 for i in range(0,W+1)] 
    #array for decisions
    MaxI = [0 for i in range(0,W+1)]
    #easy case already dealt with
    #MaxG[0] = 0
    #from easy to complex
    for w in range(1, W+1):
        #choose item that maximices gain
        max = 0
        iMax = -1
        for i in range(len(C)):
            #if item fits on knapsack
            if C[i] <= w:
                #gain
                g = G[i] + MaxG[w-C[i]]
                if g > max:
                    max = g
                    iMax = i
        MaxG[w] = max
        MaxI[w] = iMax
    #to store solution
    Q = [0 for i in C]
    w = W
    #trace back solution
    while w > 0:
        Q[ MaxI[w]]+=1
        w = w - C[MaxI[w]]
    return Q
    
#Main
cost = [3, 2, 4, 2]
gain = [15, 9, 5, 10]
capacity = 8
print( knapsack2(cost,gain,capacity))

cost = [6, 3, 4, 2 ]
gain = [30, 14, 16, 9]
capacity = 10
print( knapsack2(cost,gain,capacity))

