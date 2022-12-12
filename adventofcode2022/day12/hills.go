package main

import (
	"bufio"
	"fmt"
	"os"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	s, e := findStartEnd(data)
	fmt.Println(s, e)
	fmt.Println("Part one")
	fmt.Println(findPath(data, s, e))
	fmt.Println("Part two")
	starts := findAllStarts(data)
	fmt.Println(findBestPath(data, starts, e))
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Pos struct {
	r, c int
}

func findStartEnd(data []string) (Pos, Pos){
	R, C := len(data), len(data[0])
	var start, end Pos
	for r:=0; r<R;r++ {
		for c:=0; c<C;c++{
			if data[r][c:c+1] == "S"{
				start.r, start.c = r, c
			} else if data[r][c:c+1] == "E" {
				end.r, end.c = r, c
			}
		}
	}
	return start, end
}

func findAllStarts(data []string) []Pos{
	R, C := len(data), len(data[0])
	starts := make([]Pos, 0)
	for r:=0; r<R;r++ {
		for c:=0; c<C;c++{
			if data[r][c:c+1] == "S" || data[r][c:c+1] == "a"{
				starts = append(starts, Pos{r, c})
			}
		}
	}
	return starts
}

var moves = [] Pos{
	{1,0},
	{0,1},
	{-1,0},
	{0,-1},
}

func findPath(data []string, start, end Pos) int {
	R, C := len(data), len(data[0])
	distance := make(map[Pos]int)
	height := make(map[Pos]int)
	distance[start] = 0
	
	queue := make([]Pos, 0)
	queue = append(queue, start)
	
	for r, line := range data {
		for c, char := range line {
			height[Pos{r, c}] = int(char)
		}
	}
	height[start] = int('a')
	height[end] = int('z')
	

	for len(queue) > 0 {
		pos := queue[0]
		queue = queue[1:]
		d := distance[pos]
		//fmt.Printf("Pos %v, d = %v h(%v)\n", pos, d, height[pos])
		for _, m := range moves {
			pos2 := Pos{pos.r + m.r, pos.c + m.c}
			//fmt.Printf(" Pos2 %v h(%v)\n", pos2, height[pos2])
			if pos2.r < 0 || pos2.r >= R ||
				pos2.c < 0 || pos2.c >= C {
				continue
			}
			// no adjacent
			if height[pos] + 1 < height[pos2] {
				continue
			}
			if _, found := distance[pos2]; found {
				continue
			}
			if pos2 == end {
				return d + 1
			}
			distance[pos2] = d + 1
			queue = append(queue, pos2)
		}
	}
	return -1
}

func findBestPath(data []string, starts []Pos, end Pos) int {
	minpath := -1
	for _, p := range starts {
		d := findPath(data, p, end)
		fmt.Printf("From %v d=%v\n", p, d)
		if minpath == -1 {
			minpath = d
		} else if d != -1 && d < minpath {
			minpath = d
		}
	}
	return minpath
}
