# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.


def next_seq(seq):
    res = ''
    num = [int(n) for n in seq.split(' ')]
    i = 0
    while i < len(num):
        j = i+1
        n = 1
        while j < len(num) and num[j] == num[i]:
            j += 1
            n += 1
        res += '%s %s ' % (n, num[i])
        i = j
    # Trim the last space
    return res[:-1]

r = int(raw_input())
l = int(raw_input())

i = 1
seq = str(r)

while i < l:
    seq = next_seq(seq)
    i += 1

print seq
