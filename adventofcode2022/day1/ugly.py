# The hideous one-liner for those that like compact coding.
import sys
cals = sorted([sum(int(cal) for cal in elf.split("\n") if cal) for elf in sys.stdin.read().split(sep="\n\n")])
print(cals[-1])
print(sum(cals[-3:]))
