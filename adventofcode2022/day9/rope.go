package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	moves := parseData(data)
	// fmt.Println(moves)
	n := simulateMoves(moves, 2)
	fmt.Printf("%v positions (length 2)\n", n)
	n = simulateMoves(moves, 10)
	fmt.Printf("%v positions (length 10)\n", n)
	
	fmt.Printf("%s elapsed\n", time.Since(tstart))

}

type Move struct{
	dir string
	n int
}

type Motion struct {
	dx int
	dy int
}

var motions = map[string]Motion{
	"U":{0, 1},
	"D":{0, -1},
	"L":{-1, 0},
	"R":{1, 0},
}

func parseData(data []string) []Move {
	result := make([]Move, 0)
	for _, line := range data{
		parts := strings.Split(line, " ")
		n, _ := strconv.Atoi(parts[1])
		result = append(result, Move{parts[0], n})
	}
	return result
}

type Pos struct {
	x, y int
}

var followMotions = map[Motion]Motion{
	// same position -> dont move
	{dx: 0, dy: 0}: {dx: 0, dy: 0},
	// still touching -> dont move
	{dx: 0, dy: 1}: {dx: 0, dy: 0},
	{dx: 0, dy: -1}: {dx: 0, dy: 0},
	{dx: 1, dy: 0}: {dx: 0, dy: 0},
	{dx: -1, dy: 0}: {dx: 0, dy: 0},
	{dx: 1, dy: 1}: {dx: 0, dy: 0},
	{dx: 1, dy: -1}: {dx: 0, dy: 0},
	{dx: -1, dy: 1}: {dx: 0, dy: 0},
	{dx: -1, dy: -1}: {dx: 0, dy: 0},
	// 2 steps behind -> 1 step in the same direction
	{dx: 2, dy: 0}: {dx: 1, dy: 0},
	{dx: -2, dy: 0}: {dx: -1, dy: 0},
	{dx: 0, dy: 2}: {dx: 0, dy: 1},
	{dx: 0, dy: -2}: {dx: 0, dy: -1},
	// 2 steps diagonal -> 1 step closer diagonal
	{dx: 2, dy: 1}: {dx: 1, dy: 1},
	{dx: -2, dy: -1}: {dx: -1, dy: -1},
	{dx: 1, dy: 2}: {dx: 1, dy: 1},
	{dx: -1, dy: -2}: {dx: -1, dy: -1},

	{dx: -2, dy: 1}: {dx: -1, dy: 1},
	{dx: 2, dy: -1}: {dx: 1, dy: -1},
	{dx: -1, dy: 2}: {dx: -1, dy: 1},
	{dx: 1, dy: -2}: {dx: 1, dy: -1},
	// 2 steps away -> 1 step closer
	{dx: 2, dy: 2}: {dx: 1, dy: 1},
	{dx: -2, dy: -2}: {dx: -1, dy: -1},
	{dx: 2, dy: -2}: {dx: 1, dy: -1},
	{dx: -2, dy: 2}: {dx: -1, dy: 1},
}
func simulateMoves(moves []Move, l int) int {
	rope := make([] Pos, l)
	for i:=0; i< l; i++{
		rope[i] = Pos{0, 0}
	}
	positions := make(map[Pos]bool)
	for _, move := range moves {
		motion := motions[move.dir]
		for i2:=0; i2 < move.n; i2++ {
			// head
			rope[0].x += motion.dx
			rope[0].y += motion.dy
			// all following knots
			for j:=1; j<l; j++ {
				delta := Motion{rope[j-1].x - rope[j].x, rope[j-1].y - rope[j].y}
				follow := followMotions[delta]
				rope[j].x += follow.dx
				rope[j].y += follow.dy
			}
			// for _, r := range rope{
			// 	fmt.Printf("%v, ",r)
			// }
			// fmt.Println()
			positions[rope[l-1]] = true
		}
		//fmt.Println()
	}
	//fmt.Println(positions)
	return len(positions)
}