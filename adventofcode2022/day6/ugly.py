import sys

start_of_packet = lambda s, l: next(i + l for i in range(len(s)) if len(set(s[i:i+l])) == l)

for s in sys.stdin.readlines():
    print(f"4-packet {start_of_packet(s, 4)}")
    print(f"14-packet {start_of_packet(s, 14)}")