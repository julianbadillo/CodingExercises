'''
Created on Mar 24, 2013

@author: jabadillo
'''


def searchNMinor(A, B, inia, fina, inib, finb):
    if inia == fina and inib == finb:
        return min(A[inia],B[inib])
    ma = (inia+fina)//2;
    mb = (inib+finb)//2;
    if A[ma] == B[mb]:
        return A[ma]
    elif A[ma] < B[mb]:
        return searchNMinor(A,B,ma+1,fina,inib,mb)
    else:
        return searchNMinor(A,B,inia,ma,mb+1,finb)
        
a = [1,2,3,4]
b = [1,2,3,4]

print (searchNMinor(a,b,0,len(a)-1,0,len(b)-1))

a = [1,3,5,7]
b = [2,4,6,8]

print (searchNMinor(a,b,0,len(a)-1,0,len(b)-1))

a = [1,2,3,4,5]
b = [5,6,7,8,9]

print (searchNMinor(a,b,0,len(a)-1,0,len(b)-1))