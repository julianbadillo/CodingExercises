#! python

def existeIgual(A):
    imin = 0
    imax = len(A)-1
    while (A[imin]-imin)*(A[imax]-imax) <= 0 and imin <= imax:
        imid = (imin+imax)//2
        if( A[imin] == imin or A[imax] == imax or A[imid] == imid ):
            return True
        if(A[imin]-imin)*(A[imid]-imid) <= 0:
            imax = imid - 1
        elif (A[imid]-imid)*(A[imax]-imax) <= 0:
            imin = imid + 1
    
    return False

    
arr = [-1,0,1,2,4,5,6,8,9,10,11]
arr2 = [1,2,3,4,5,6,7,8,9]
arr3 = [-1,0,1,2,3,4,5,6,7,8]
e = existeIgual(arr)
print(e)
e = existeIgual(arr2)
print(e)
e = existeIgual(arr3)
print(e)