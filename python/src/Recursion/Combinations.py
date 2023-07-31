#!/usr/bin/python
'''
Created on Mar 24, 2013

@author: jabadillo
'''


def printCombinations(chars, used, ini):
    if ini == len(chars):
        for i in range(len(chars)):
            if used[i]:
                print ( chars[i], end="")
        print("")
    else:
        used[ini]=True
        printCombinations(chars, used, ini+1)
        used[ini]=False
        printCombinations(chars, used, ini+1)
        
def printCombinationsW(chars):
    used = []
    for i in range(len(chars)):
        used.append(False)
    printCombinations(chars, used, 0)
    
a = "1234"
printCombinationsW(a)