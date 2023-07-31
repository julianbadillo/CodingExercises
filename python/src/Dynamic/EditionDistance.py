
from itertools import product

def minEditingDistance(str1, str2):
    #create a matrix with first row and first column
    #if i == 0, then i
    #if j == 0, then j
    #else 0
    n = len(str1)
    m = len(str2)
    ME = [ [i if j==0 else j if i==0 else 0 for j in range(m+1)] for i in range(n+1)]
    
    #now recursive case
    for i,j in product(range(1,n+1), range(1,m+1)):
        #diff(i,j) + ME(i-1,j-1)
        l1 = (0 if str1[i-1] == str2[j-1] else 1)+ME[i-1][j-1]
        #removing or deleting
        l2 = 1+ME[i-1][j]
        l3 = 1+ME[i][j-1]
        #min
        ME[i][j] = min(l1,l2,l3)
    
    #pretty painting a matrix
    print( '\n'.join([str(x) for x in ME]) )
    
    ##print( '\n'.join([str(elem) for row in ME for elem in row]))
    return ME[n][m]

##MAIN CODE
str1 = 'sunny'
str2 = 'snowys'
print( '"'+str1+'" vs "'+str2+'" distance = ',minEditingDistance(str1,str2))
