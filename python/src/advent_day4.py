
class Bingo:
    size = 5
    def __init__(self) -> None:
        self.numbers = []
        self.marked = [[False for i in range(Bingo.size)] for j in range(Bingo.size)]
        self.finished = False

    def print(self):
        for r in self.numbers:
            print(r)

    def mark_number(self, n):
        # if already finished, we don't care
        if self.finished:
            return None
        for i in range(Bingo.size):
            for j in range(Bingo.size):
                if self.numbers[i][j] == n:
                    self.marked[i][j] = True
                    # Check row or column all true
                    if all(self.marked[i]) or \
                        all(self.marked[k][j] for k in range(Bingo.size)):
                        # mark as finished
                        self.finished = True
                        # sum all unmarked
                        s = 0
                        for k in range(Bingo.size):
                            for l in range(Bingo.size):
                                s += self.numbers[k][l] \
                                    if not self.marked[k][l] \
                                    else 0
                        return s
        return None


def read_file(file_name):
    """
    Reads the text file
    """
    with open(file_name, 'r') as file:
        list = [line.strip() for line in file.readlines()]
        return list

def load_bingos(lines):
    bingos = []
    bingo = None
    for line in lines:
        if line == "":
            if bingo is not None:
                bingos.append(bingo)
            bingo = Bingo()
        else:
            bingo.numbers.append([int(x) for x in filter(None, line.split(" "))])
    # if not appended
    if bingos[-1] != bingo:
        bingos.append(bingo)
    return bingos

def play_game(numbers, bingos):
    for n in numbers:
        for b in bingos:
            # if it has won
            res = b.mark_number(n)
            if res:
                print("Bingo!")
                return (n, res)


def play_game_loser(numbers, bingos):
    for n in numbers:
        for b in bingos:
            # if it has won
            res = b.mark_number(n)
            # if all marked, return answer
            if all(b.finished for b in bingos):
                return (n, res)



if __name__ == "__main__":
    lines = read_file("test/advent_day4_test.txt")
    numbers = []
    
    numbers = [int(x) for x in filter(None, lines[0].split(","))]
    bingos = load_bingos(lines[1:])
    for b in bingos:
        b.print()
        print()

