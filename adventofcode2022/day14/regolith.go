package main

import (
	"bufio"
	"fmt"
	"math"
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
	//solutionPart1(data)
	solutionPart2(data)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Pos struct {
	x, y int
}

type Mov struct {
	dx, dy int
}

var moves = []Mov{{0, 1}, {-1, 1}, {1, 1}}

func (grain *Pos) Fall(start Pos, rock map[Pos]bool, sand map[Pos]bool, maxy int) bool {
	grain.x, grain.y = start.x, start.y
	moving := true
	for grain.y <= maxy {
		moving = false
		for _, mov := range moves {
			if !rock[Pos{grain.x + mov.dx, grain.y + mov.dy}] && !sand[Pos{grain.x + mov.dx, grain.y + mov.dy}] {
				grain.x += mov.dx
				grain.y += mov.dy
				moving = true
				break
			}
		}
		if !moving {
			break
		}
	}
	//fmt.Printf("Final pos: $%v\n", grain)
	if grain.y <= maxy {
		sand[*grain] = true
		return true
	}
	return false
}

func (grain *Pos) FallFloor(start Pos, rock map[Pos]bool, sand map[Pos]bool, maxy int) bool {
	grain.x, grain.y = start.x, start.y
	if sand[*grain] {
		return false
	}
	moving := true
	for grain.y < maxy+2 {
		moving = false
		for _, mov := range moves {
			if !rock[Pos{grain.x + mov.dx, grain.y + mov.dy}] && !sand[Pos{grain.x + mov.dx, grain.y + mov.dy}] &&
				grain.y+mov.dy < maxy+2 {
				grain.x += mov.dx
				grain.y += mov.dy
				moving = true
				break
			}
		}
		if !moving {
			break
		}
	}
	sand[*grain] = true
	return true
}

func Minx(s map[Pos]bool) int {
	minx := math.MaxInt64
	for p := range s {
		if p.x < minx {
			minx = p.x
		}
	}
	return minx
}

func solutionPart1(data []string) {
	rock := make(map[Pos]bool)
	sand := make(map[Pos]bool)
	minx, maxy := math.MaxInt64, 0
	for _, line := range data {
		wall := parseLine(line)
		x, y := fillMap(wall, rock)
		if y > maxy {
			maxy = y
		}
		if x < minx {
			minx = x
		}
	}
	printMap(rock, sand, minx, maxy)

	fmt.Printf("Min x:= %v\n", minx)
	fmt.Printf("Max y:= %v\n", maxy)
	var start = Pos{500, 0}
	for i := 0; ; i++ {
		var g1 = Pos{500, 0}
		if !g1.Fall(start, rock, sand, maxy) {
			fmt.Printf("Stop at %v\n", i)
			printMap(rock, sand, minx, maxy)
			break
		}
	}
}

func solutionPart2(data []string) {
	rock := make(map[Pos]bool)
	sand := make(map[Pos]bool)
	minx, maxy := math.MaxInt64, 0
	for _, line := range data {
		wall := parseLine(line)
		x, y := fillMap(wall, rock)
		if y > maxy {
			maxy = y
		}
		if x < minx {
			minx = x
		}
	}
	printMapFloor(rock, sand, minx, maxy)

	fmt.Printf("Min x:= %v\n", minx)
	fmt.Printf("Max y:= %v\n", maxy)
	var start = Pos{500, 0}
	for i := 0; ; i++ {
		var g1 = Pos{500, 0}
		if !g1.FallFloor(start, rock, sand, maxy) {
			fmt.Printf("Stop at %v\n", i)
			minx = Minx(sand)
			printMapFloor(rock, sand, minx, maxy)
			break
		}
	}
}

func printMap(rock map[Pos]bool, sand map[Pos]bool, minx int, maxy int) {
	for y := 0; y <= maxy; y++ {
		for x := minx; x < 600; x++ {
			if rock[Pos{x, y}] {
				fmt.Print("#")
			} else if sand[Pos{x, y}] {
				fmt.Print("o")
			} else {
				fmt.Print(" ")
			}
		}
		fmt.Println()
	}
}

func printMapFloor(rock map[Pos]bool, sand map[Pos]bool, minx int, maxy int) {
	for y := 0; y <= maxy+2; y++ {
		for x := minx; x < 600; x++ {
			if rock[Pos{x, y}] {
				fmt.Print("#")
			} else if sand[Pos{x, y}] {
				fmt.Print("o")
			} else if y == maxy+2 {
				fmt.Print("#")
			} else {
				fmt.Print(" ")
			}
		}
		fmt.Println()
	}
}

func parseLine(line string) []Pos {
	res := make([]Pos, 0)
	for _, s := range strings.Split(line, " -> ") {
		p := strings.Split(s, ",")
		x, _ := strconv.Atoi(p[0])
		y, _ := strconv.Atoi(p[1])
		res = append(res, Pos{x, y})
	}
	return res
}

func fillMap(wall []Pos, rock map[Pos]bool) (int, int) {
	maxy, minx := 0, math.MaxInt64
	for i := 0; i < len(wall)-1; i++ {
		dx, dy := 0, 0
		switch {
		case wall[i].x < wall[i+1].x:
			dx = 1
		case wall[i].x > wall[i+1].x:
			dx = -1
		}
		switch {
		case wall[i].y < wall[i+1].y:
			dy = 1
		case wall[i].y > wall[i+1].y:
			dy = -1
		}
		// all points between wall[i] and wall[i+1]
		for x, y := wall[i].x, wall[i].y; true; x, y = x+dx, y+dy {
			rock[Pos{x, y}] = true
			if y > maxy {
				maxy = y
			}
			if x < minx {
				minx = x
			}
			// hit the limit
			if dx != 0 && x == wall[i+1].x {
				break
			}
			if dy != 0 && y == wall[i+1].y {
				break
			}
		}
	}
	return minx, maxy
}

func svg(map[Pos]bool) {

}
