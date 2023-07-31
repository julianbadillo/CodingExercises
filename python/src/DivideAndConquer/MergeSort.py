#!/usr/env/bin python

'''
Created on Apr 8, 2013

@author: jabadillo
'''

def mergeSort(A):
    if(len(A)>1):
        m = len(A)//2
        A[:m] = mergeSort(A[:m])
        A[m:] = mergeSort(A[m:])
        A = merge(A[:m],A[m:])
    return A

def merge(X,Y):
    Z = []
    i,j = 0,0
    while(i < len(X) or j < len(Y)):
        if( i == len(X)):
            Z.append(Y[j])
            j=j+1
        elif( j == len(Y)):
            Z.append(X[i])
            i=i+1
        elif( X[i] <= Y[j]):
            Z.append(X[i])
            i=i+1
        else:
            Z.append(Y[j])
            j=j+1
    return Z    

Arr = [1,30,4,14,32,23,7,3,43,98,32,42,8,0,12,28,10,22,21,17]
print(Arr)
Arr = mergeSort(Arr)
print(Arr)
