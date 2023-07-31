"""
LONGEST COMMON SUBSEQUENCE
"""
from itertools import product

def longestCommonSubSequence(s1, s2):
    #first we build matrix
    n = len(s1)
    m = len(s2)
    LCS = [[0 for j in range(0,m+1)] for i in range(0,n+1)]
    
    #go from easy to difficult
    for i,j in product(range(1,n+1),range(1,m+1)):
        if s1[i-1] == s2[j-1]:
            LCS[i][j] = 1 + LCS[i-1][j-1]
        else:
            LCS[i][j] = max(LCS[i-1][j],LCS[i][j-1])
    return LCS[n][m]

    
def longestCommonSubSequence2(s1, s2):
    #first we build matrix
    n = len(s1)
    m = len(s2)
    LCS = [[0 for j in range(0,m+1)] for i in range(0,n+1)]
    #store decitions
    PREV = [[0 for j in range(0,m+1)] for i in range(0,n+1)]
    #go from easy to difficult
    for i,j in product(range(1,n+1),range(1,m+1)):
        if s1[i-1] == s2[j-1]:
            LCS[i][j] = 1 + LCS[i-1][j-1]
            #one for up_left
            PREV[i][j] = 1
        elif LCS[i-1][j] > LCS[i][j-1]:
            LCS[i][j] = LCS[i-1][j]
            #two for up
            PREV[i][j] = 2
        else:
            LCS[i][j] = LCS[i][j-1]
            #three for left
            PREV[i][j] = 3
    
    #rebuild sequence
    i = n
    j = m
    result = ''
    while PREV[i][j] != 0:
        #up left
        if PREV[i][j] == 1:
            result = s1[i-1]+result
            i = i-1
            j = j-1
        #up
        elif PREV[i][j] == 2:
            i = i-1
        #left
        elif PREV[i][j] == 3:
            j = j-1
    return result    
    
#Main
s1 = "abcdefg"
s2 = "acfe"

print(longestCommonSubSequence(s1,s2))
print(longestCommonSubSequence2(s1,s2))
