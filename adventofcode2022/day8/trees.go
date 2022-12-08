package main

import (
	"bufio"
	"fmt"
	"os"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([][]int, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		line := scanner.Text()
		row := make([]int, len(line))
		for i, c := range line {
			row[i] = int(c - '0')
		}
		data = append(data, row)
	}
	//fmt.Println(data)
	fmt.Printf("Visible %v\n", countVisible(data))
	//fmt.Printf("scenic score %v\n",  scenicScore(data, 1, 2))
	//fmt.Printf("scenic score %v\n",  scenicScore(data, 3, 2))
	fmt.Printf("Max scenic score %v\n",  maxScenicScore(data))
	
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Move struct {
	r, c     int
	dr, dc   int
	dr2, dc2 int
}

func countVisible(data [][]int) int {
	R, C := len(data), len(data[0])
	moves := [4]Move{
		Move{0, 0, 0, 1, 1, 0},
		Move{0, 0, 1, 0, 0, 1},
		Move{R - 1, 0, -1, 0, 0, 1},
		Move{R - 1, C - 1, 0, -1, -1, 0},
	}
	visible := make([][]bool, R)
	for i := 0; i < R; i++ {
		visible[i] =make([]bool, C)
	}

	count := 0
	
	for _, move := range moves {
		for i := 0; true; i++ {
			maxi := -1
			for j := 0; true; j++ {
				if r, c := move.r + i*move.dr2 + j*move.dr, move.c + i*move.dc2 + j*move.dc
					r < 0 || r >= R || c < 0 || c >= C {
					break
				} else if maxi < data[r][c] {
					maxi = data[r][c]
					if !visible[r][c] {
						visible[r][c] = true
						count += 1
					}
				}
			}
		}
	}
	// for _, line := range visible {
	// 	fmt.Println(line)
	// }
	return count
}

type LMove struct {
	dr, dc   int
}

var MOVES = [4]LMove {
	LMove{-1, 0},
	LMove{0, -1},
	LMove{1, 0},
	LMove{0, 1},	
}

func scenicScore(data[][]int, r, c int) int {
	R, C := len(data), len(data[0])
	score := 1 
	for _, move := range MOVES {
		i := 0
		for ; true ; i++ {
			//fmt.Printf(">%v %v\n", r2, c2)
			if r2, c2 := r + move.dr*(i+1), c + move.dc*(i+1)
				r2 < 0 || r2 >= R || c2 < 0 || c2 >= C {
				break
			} else if data[r][c] <= data[r2][c2] {
				i++
				break
			}
		}
		//fmt.Printf("[%v] \n", i)
		score *= i
	}
	return score
}

func maxScenicScore(data [][]int) int {
	maxScore := 0
	for i := range data{
		for j := range data[i] {
			score := scenicScore(data, i, j)
			if score > maxScore {
				maxScore = score
			}
		}
	}
	return maxScore
}