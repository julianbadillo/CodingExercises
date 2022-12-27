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
	cubes := parseCubes(data)
	solutionPart1(cubes)
	solutionPart2(cubes)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Cube struct {
	x, y, z int
}

func (cube1 Cube) Touch(cube2 Cube) bool {
	return Abs(cube1.x - cube2.x) + Abs(cube1.y - cube2.y) + Abs(cube1.z - cube2.z) == 1
}

func parseCubes(data []string) []Cube {
	cubes := make([]Cube, 0)
	for _, line := range data {
		p := strings.Split(line, ",")
		x, _ := strconv.Atoi(p[0])
		y, _ := strconv.Atoi(p[1])
		z, _ := strconv.Atoi(p[2])
		cubes = append(cubes, Cube{x, y, z})
	}
	return cubes
}

func solutionPart1(cubes[] Cube) {
	surface := 0
	for _, cube1 := range cubes {
		surface += 6
		for _, cube2 := range cubes {
			if cube1 == cube2 {
				continue
			}
			if cube1.Touch(cube2) {
				surface--
			}
		}
	}
	fmt.Printf("Surface %v\n", surface)
}

var moves =  [][]int{
	{1, 0, 0},
	{-1, 0, 0},
	{0, 1, 0},
	{0, -1, 0},
	{0, 0, 1},
	{0, 0, -1},
}

func solutionPart2(cubes[] Cube) {
	surface := 0
	lava := make(map[Cube]bool, 0)
	for _, cube := range cubes {
		lava[cube] = true
	}
	
	minx, miny, minz, maxx, maxy, maxz := minMaxCoords(cubes)
	// increase the borders to flood.
	minx--
	miny--
	minz--
	maxx++
	maxy++
	maxz++
	// fill the outside with cubes
	outside := make(map[Cube]bool, 0)
	outside[Cube{minx, miny, minz}] = true
	queue := make([]Cube, 0)
	queue = append(queue, Cube{minx, miny, minz})
	for ; len(queue) > 0 ; {
		cube := queue[0]
		queue = queue[1:]
		for _, move := range moves {
			cube2 := Cube{cube.x + move[0], cube.y + move[1], cube.z + move[2]}
			//fmt.Printf("Try %v\n", cube2)
			// if outside of the borders
			if cube2.x < minx || cube2.x > maxx ||
				cube2.y < miny || cube2.y > maxy ||
				cube2.z < minz || cube2.z > maxz {
				//fmt.Println("Outside borders")
				continue
			}
			// already created
			if outside[cube2] {
				//fmt.Println("Alreadt")
				continue
			}
			// lava
			if lava[cube2] {
				//fmt.Println("lava")
				continue
			}
			//fmt.Println("Added!")
			outside[cube2] = true
			queue = append(queue, cube2)
		}
	}
	fmt.Printf("Len of outside cubes %v\n", len(outside))
	for _, cube1 := range cubes {
		// for each of the faces - know if inside, outside or touch
		for cube2 := range outside {
			if cube1.Touch(cube2) {
				surface++
			}
		}
	}
	fmt.Printf("Surface %v\n", surface)
}

func Abs(x int) int {
	if x >= 0 {
		return x
	}
	return -x
}

func minMaxCoords(cubes[] Cube) (minx, miny, minz, maxx, maxy, maxz int) {
	minx, miny, minz = math.MaxInt64, math.MaxInt64, math.MaxInt64
	maxx, maxy, maxz = math.MinInt64, math.MinInt64, math.MinInt64
	for _, c := range cubes {
		if c.x < minx {
			minx = c.x
		}
		if c.y < miny {
			miny = c.y
		}
		if c.z < minz {
			minz = c.z
		}
		if c.x > maxx {
			maxx = c.x
		}
		if c.y > maxy {
			maxy = c.y
		}
		if c.z > maxz {
			maxz = c.z
		}
	}
	//fmt.Println(minx, miny, minz, maxx, maxy, maxz)
	return minx, miny, minz, maxx, maxy, maxz
}