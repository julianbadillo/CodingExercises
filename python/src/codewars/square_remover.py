"""
See SquareRemover.txt
"""
import random

class SquareRemover:

    def playIt(self, colors, board, startSeed):
        self.score = 0    

        self.colors = colors
        self.startBoard = board
        self.board = board
        self.startSeed = startSeed
        self.buffer = startSeed
        self._transformBoardToMatrix()
        moves = []
        cont = True
        sorting = 0
        while cont:
            #calculate if a single swap is enough
            if sorting == 0:
                cont = self.calculateSingleSwap(moves)
            elif sorting == 1:
                cont = self.calculateSortHMoves(moves)
            elif sorting == 2:
                cont = self.calculateSortVMoves(moves)
            if len(moves) == 3*10000:
                return moves
            #try the oposite
            if not cont:
                sorting = (sorting+1)%3
                if sorting == 0:
                    cont = self.calculateSingleSwap(moves)
                elif sorting == 1:
                    cont = self.calculateSortHMoves(moves)
                elif sorting == 2:
                    cont = self.calculateSortVMoves(moves)
        return moves
    
    def calculateSingleSwap(self, moves):
        for r in range(len(self.board)-1):
            for c in range(len(self.board[r])-1):
                if self.swapMakesSquare(r,c,1):
                    #do the swap
                    self.board[r][c],self.board[r][c+1] = self.board[r][c+1],self.board[r][c]
                    #add swap to moves
                    moves.extend([r,c,1])
                    sc = self.score
                    #adjust score
                    self.adjustScore()
                    if sc == self.score:
                        self.printIt()
                        print "ERROR"
                    if len(moves) == 3*10000:
                        return False
                    return True
                elif self.swapMakesSquare(r,c,2):
                    #do the swap
                    self.board[r][c],self.board[r+1][c] = self.board[r+1][c],self.board[r][c]
                    #add swap to moves
                    moves.extend([r,c,2])
                    sc = self.score
                    #adjust score
                    self.adjustScore()
                    if sc == self.score:
                        self.printIt()
                        print "ERROR"
                    if len(moves) == 3*10000:
                        return False
                    return True
                    
    def swapMakesSquare(self, r, c, d):
        #d can only 1 or 2
        movd1 =[ [(0,1),(0,-1),(-1,-1),(-1,0)]  #square above, left
                , [(0,0),(-1,1),(-1,2),(0,2)]   #square above, right
                , [(0,1),(1,0),(1,-1),(0,-1)]  #square below left
                , [(0,0),(0,2),(1,2),(1,1)]]    #square below right
                
        movd2 = [[(1,0),(-1,0),(-1,1),(0,1)] #square above, right
                , [(0,0),(1,1),(2,1),(2,0)]  #square below, right
                , [(0,0),(2,0),(2,-1),(1,-1)]  #square below left
                , [(1,0),(0,-1),(-1,-1),(-1,0) ]]    #square above left]
        #direction of swapping
        if d == 1:
            movel = movd1
        elif d == 2:
            movel = movd2
        
        #check if the swapping makes a square
        for move in movel:
            value = None
            allEqual = True
            for (dr, dc) in move:
                #if outside the boundaries
                if (not (0 <= r+dr < len(self.board)) or
                    not (0 <= c+dc < len(self.board[0]))):
                    allEqual = False
                    break
                elif value == None:
                    value = self.board[r+dr][c+dc]
                #if one of this is different
                elif value != self.board[r+dr][c+dc]:
                    allEqual = False
                    break

            if allEqual:
                return True
        #if finished without finding
        return False

    def calculateSortHMoves(self, moves):
        #calculate the moves for bubble sort each row
        for i in range(len(self.board)):
            for j in range(len(self.board[i])-1, 0,-1):
                for k in range(j):
                    if self.board[i][k] > self.board[i][k+1]:
                        #swap
                        self.board[i][k],self.board[i][k+1] = self.board[i][k+1],self.board[i][k]
                        #add swap to moves
                        moves.extend([i,k,1])
                        #since there was a swap, we need to recalculate
                        #TODO only recalculate with the swap position, faster
                        self.adjustScore()
                        
                        #start sorting from the beggining
                        #TODO only from this point
                        #if we already have all the moves
                        if len(moves) == 3*10000:
                            return False
                        return True

    def calculateSortVMoves(self, moves):
        #calculate the moves for bubble sort each column
        for i in range(len(self.board[0])):
            for j in range(len(self.board)-1, 0,-1):
                for k in range(j):
                    if self.board[k][i] > self.board[k+1][i]:
                        #swap
                        self.board[k][i],self.board[k+1][i] = self.board[k+1][i],self.board[k][i]
                        #add swap to moves
                        moves.extend([k,i,2])
                        #since there was a swap, we need to recalculate
                        #TODO only recalculate with the swap position, faster
                        self.adjustScore()
                        #if we already have all the moves
                        if len(moves) > 3*10000:
                            return False
                        #start sorting from the beggining
                        #TODO only from this point
                        return True
        #if it finished sorting
        return False
        

    def _transformBoardToMatrix(self):
        #converts the board in a int matrix (rather than a string tuple)
        #so it can be swapped
        self.board = [[int(c) for c in row] for row in self.board]

    def restart(self):
        #restarts th square
        self.board = self.startBoard
        self.buffer = self.startSeed
        self.score = 0
        self._transformBoardToMatrix()

    def adjustScore(self):
        squares = True
        while squares:
            squares = False
            i = 0
            while not squares and i < len(self.board)-1:
                j = 0
                while not squares and j < len(self.board[i])-1:
                    #if equals, increase score and fill with next tiles
                    if( self.board[i][j] == self.board[i+1][j] 
                        and self.board[i+1][j] == self.board[i][j+1]
                        and self.board[i][j+1] == self.board[i+1][j+1]):
                        self.score += 1
                        self.board[i][j]     = self.nextColor()
                        self.board[i][j+1]   = self.nextColor()
                        self.board[i+1][j]   = self.nextColor()
                        self.board[i+1][j+1] = self.nextColor()
                        squares = True
                    j += 1
                i += 1
                    
        return self.score
    
    def nextColor(self):
        c = self.buffer % self.colors
        self.buffer = (self.buffer*48271)%2147483647
        return c

    def simulateGame(self, moves):
        self.restart()
        """
        Calculates the total score of a game, given the moves
        array
        """
        for i in range(0,len(moves),3):
            row, col, d = moves[i:i+3]
            #self.printIt()
            #print "move",row,col,d
            #do the swapping
            #up            
            if d == 0:
                t = self.board[row][col]
                self.board[row][col] = self.board[row-1][col]
                self.board[row-1][col] = t
            #right
            elif d == 1:
                t = self.board[row][col]
                self.board[row][col] = self.board[row][col+1]
                self.board[row][col+1] = t
            #down
            elif d == 2:
                t = self.board[row][col]
                self.board[row][col] = self.board[row+1][col]
                self.board[row+1][col] = t
            #left
            elif d == 3:
                t = self.board[row][col]
                self.board[row][col] = self.board[row][col-1]
                self.board[row][col-1] = t
            #eliminate squares and get the points.
            #self.printIt()
            self.adjustScore()
        return self.score
    
    def printIt(self):
        for s in self.board:
            print ''.join(str(c) for c in s)


def generateRandomMoves(sr):
    moves = []
    for i in range(10000):
        r = random.randrange(1,len(sr.board)-1)
        c = random.randrange(1,len(sr.board[0])-1)
        d = random.randrange(4)
        moves.extend(r,c,d)
    return moves

def bestRandom(sr):
    """
    Best random score : 788
    """        
    bestscore = 0
    for i in range(100):
        moves = generateRandomMoves(sr)
        score = sr.simulateGame(moves)
        if score > bestscore:
            bestscore = score
        #sr.printIt()
        print score
    print "best:",bestscore
 

def main():
    #top sorting H and V: 801 (30000
    #top sorting and best move 807
    sr = SquareRemover()
    print "calculating"
    """    moves = sr.playIt(7,   ["122333440466",
                   "661122335425",
                    "556611513340",
                    "445566112230",
                    "313540125122",
                    "223304556611",
                    "121613401566",
                    "611122334055",], 1)
    """
    moves = sr.playIt(7, ["112233445566",
                    "223344556611",
                    "334455661122",
                    "445566112233",
                    "556611223344",
                    "661122334455",
                    "112233445566",
                    "223344556611",], 1)
    print "-"*12
    sr.printIt()
    print "score",sr.score
    print "moves #",len(moves)
    print "start"
    score = sr.simulateGame(moves)
    print "end, score",score
    sr.printIt()
    #print bestmoves


if __name__ == "__main__":
    main()


