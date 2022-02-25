"""
Problem Statement
    	Fox Ciel is planning to register on TopFox. Her family name is familyName and her given name is givenName.
She will choose a TopFox handle according to the following rule. Let s be a non-empty prefix of her family name and let t be a non-empty prefix of her given name. Now Fox Ciel may choose the concatenation of s and t as her handle.
For example, suppose Fox Ciel's family name is "fox" and her given name is "ciel". She may choose the handle "foxciel", "fc", or "foxc". She may not choose "ox", "ciel", or "jiro".
You are told Fox Ciel's family name and given name. Return the number of possible handles Fox Ciel may choose.
"""


def allPossibleNames(familyName, givenName):

    names = set()
    for i in range(1, len(familyName) + 1):
        for j in range(1, len(givenName) + 1):
            s = familyName[:i]
            t = givenName[:j]
            # print(s+t)
            names.add(s + t)
    # print(names)
    return len(names)


print(allPossibleNames("fox", "ciel"))
print(allPossibleNames("ab", "cd"))
print(allPossibleNames("abbbb", "bbbbbbbc"))
print(allPossibleNames("abxy", "xyxyxc"))
print(allPossibleNames("ababababab", "bababababa"))
print(allPossibleNames("aadcabbc", "bbcbbcbdbc"))
