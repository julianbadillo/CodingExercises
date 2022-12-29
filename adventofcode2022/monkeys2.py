from sys import stdin
import re

data = [line.strip() for line in stdin.readlines()]

# Split data
monkeys = {}
for line in data:
    parts = line.split(": ")
    monkeys[parts[0]] = parts[1]

unsolved = {k: v for k, v in monkeys.items() if not v.isnumeric()} 
solved = {k: v for k, v in monkeys.items() if v.isnumeric()}
 
while unsolved:
    for name in list(unsolved.keys()):
        for name2, value2 in list(solved.items()):
            if name2 in unsolved[name]:
                unsolved[name] = unsolved[name].replace(name2, str(value2))
        if re.search(r'[a-z]+', unsolved[name]) is None:
            print(f"Can evaluate {name}: {unsolved[name]}")
            # can evaluate
            solved[name] = int(eval(unsolved[name]))
            del unsolved[name]
    # print(unsolved)
    # print(solved)
    # print("-"*100)

print(solved['root'])
