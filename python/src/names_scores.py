# Using names.txt (right click and 'Save Link/Target As...'), a 46K text file containing over five-thousand first names,
# begin by sorting it into alphabetical order. Then working out the alphabetical value for each name, multiply this value
# by its alphabetical position in the list to obtain a name score.

# For example, when the list is sorted into alphabetical order, COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th
# name in the list. So, COLIN would obtain a score of 938 × 53 = 49714.
# What is the total of all the name scores in the file?
import urllib.request

def name_score(name):
    """
    Score of a single name
    """
    # to upper case - not needed
    name = name.upper()
    # sum each letter score
    total = sum(ord(c) - ord('A') + 1 for c in name)
    return total


def total_name_score(fileName, remote = False):
    """
    Sum of all scores, assumes word position starts with 1 (instead of 0)
    @param filename or url
    """
    total = 0
    content = ''
    # open local file
    if not remote:
        with open(file=fileName) as file:
            content = file.read()
    # open remote file
    else:
        with urllib.request.urlopen(url=fileName) as file:
            content = file.read().decode("UTF-8")
    
    # split by commas and remove the quotes
    nameList = [name.replace("\"", "") for name in content.split(",")]
    # sort
    nameList = sorted(nameList)
    # print(nameList)
    total = sum((i + 1) * name_score(name) # 1-index position times name score
                for i, name in enumerate(nameList))
    return total

if __name__ == '__main__':
    # Use local or remote file
    # fileName = "p022_names.txt"
    # print(totalNameScore(fileName))

    url = "https://projecteuler.net/project/resources/p022_names.txt"
    print(total_name_score(url, remote=True))