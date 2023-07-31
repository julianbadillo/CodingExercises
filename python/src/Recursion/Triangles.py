
def getNumber(m, x, y):
    ##make a list of border points
    border = [(x,0) for x in range(m)]
    border.extend([ (m,y) for y in range(m)])
    border.extend([ (x,m) for x in range(m,0,-1)])
    border.extend([ (0,y) for y in range(m,0,-1)])
    triangles = 0
    for i in range(len(border)):
        #pick first point
        first = border[i]
        #pick some other point greater (clock-wise)
        #than first
        for j in range(i+1,len(border)):
            second = border[j]
            #validate if all points inside this side of the triangle
            if allPointsInside(first, second, x, y):
                #pick third point, greater than second
                for k in range(j+1, len(border)):
                    third = border[k]
                    #validate again
                    if allPointsInside(second,third,x,y) and allPointsInside(third,first,x,y):
                        triangles+=1
    return triangles
    
#checks if a collection of points is all
#on the left side of a line
def allPointsInside( p1, p2, x, y):
    #u = P1 to P2
    u = (p2[0] - p1[0], p2[1] - p1[1])
    for i in range(len(x)):
        #v is p1 to xi,yi
        v = (x[i]-p1[0],y[i]-p1[1])
        #check if one is left of the other
        if not checkLeft( u, v ):
            return False
    return True

def checkLeft( u, v ):
    #z component
    z = v[0]*u[1] - v[1]*u[0]
    return z <= 0

m = 2
x =[1]
y = [1]
t =getNumber(m, x, y)
print(t)

m = 3
x =[1,2]
y = [1,2]
t =getNumber(m, x, y)
print(t)


m = 4
x = [1,2,3]
y = [1,3,2]
t =getNumber(m, x, y)
print(t)

m =7
x =[ 1, 1, 6, 6 ]
y = [ 1, 6, 1, 6 ]
t =getNumber(m, x, y)
print(t)

m =4
x =[ 2]
y = [ 2]
t =getNumber(m, x, y)
print(t)

m =10
x =[ 2, 6, 7, 6 ]
y = [7, 7, 9, 3 ]
t =getNumber(m, x, y)
print(t)


m = 15
x = [ 7, 6, 5, 4, 3 ]
y =[ 1, 4, 7, 10, 13 ]
t =getNumber(m, x, y)
print(t)

#this test case wont work!!
"""
m = 58585
x= [14662, 14661, 14648, 14647, 14659, 14658, 14654, 14664, 14651, 14656, 14652, 14657, 14655, 14646, 14650, 14649, 14660, 14665, 14653, 14663]
y = [14902, 14871, 14650, 14647, 14815, 14790, 14710, 14970, 14671, 14746, 14682, 14767, 14727, 14646, 14662, 14655, 14842, 15007, 14695, 14935]
t =getNumber(m, x, y)
print(t)

"""