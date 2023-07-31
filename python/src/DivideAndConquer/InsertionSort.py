#!/usr/env/bin python

'''
Created on Apr 8, 2013

@author: jabadillo
'''

def insertionSort(A):
    for j in range(1,len(A)):
        k = A[j]
        i = j-1
        while i>=0 and A[i]>k:
            A[i+1]=A[i]
            i=i-1
        A[i+1]=k
        
    return A

Arr = [1,30,4,14,32,23,7,3,43,98,32,42,8,0,12,28,10,22,21,17]
print(Arr)
insertionSort(Arr)
print(Arr)
