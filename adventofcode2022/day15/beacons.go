package main

import (
	"bufio"
	"flag"
	"fmt"
	"os"
	"regexp"
	"sort"
	"strconv"
	"time"
)

type Sensor struct {
	sx, sy, bx, by int
	dist           int
}

type Beacon struct {
	bx, by int
}

func Abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

func (b *Sensor) SetDist() {
	b.dist = Abs(b.sx-b.bx) + Abs(b.sy-b.by)
}

type Interval struct {
	a, b int
}

func (b Sensor) Interval(y int) *Interval {
	dy := Abs(b.sy - y)
	if dy > b.dist {
		return nil
	}
	dx := b.dist - dy
	return &Interval{b.sx - dx, b.sx + dx}
}

func (i Interval) Size() int {
	return i.b - i.a + 1
}

func PrintIntervals(l []*Interval) {
	for _, i := range l {
		fmt.Println(i)
	}
}

func main() {
	tstart := time.Now()
	// y from command line arguments
	y := flag.Int("y", 10, "y position to look for")
	maxx := flag.Int("maxx", 20, "max x")
	maxy := flag.Int("maxy", 20, "max y")

	flag.Parse()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	fmt.Println(*y)
	solutionPart1(data, *y)
	solutionPart2(data, *maxx, *maxy)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

func solutionPart1(data []string, y int) {

	// var beacon1 = Beacon{sx: 8, sy: 7, bx: 2, by: 10, dist: 0}
	// beacon1.SetDist()
	// fmt.Println(beacon1)
	// fmt.Println(beacon1.Interval(10))

	intervals := make([]*Interval, 0)
	sensors, beacons := parseData(data)
	for _, s := range sensors {
		interval := s.Interval(y)
		if interval != nil {
			intervals = append(intervals, interval)
		}
	}
	//PrintIntervals(intervals)
	// sort by start
	sort.Slice(intervals, func(i, j int) bool {
		return intervals[i].a < intervals[j].a
	})

	// fmt.Println("After sort")
	// PrintIntervals(intervals)

	stack := mergeIntervals(intervals)
	// fmt.Println("After merge")
	// PrintIntervals(stack)
	s := 0
	// sum size of itervals
	for _, i := range stack {
		s += i.Size()
	}
	fmt.Printf("Size before discount %v\n", s)
	// discout any beacon in the given line
	for b := range beacons {
		if b.by == y {
			contained := false
			for _, i := range stack {
				if i.a <= b.bx && b.bx <= i.b {
					//fmt.Printf("Beacon %v contained in %v\n", b, i)
					contained = true
					break
				}
			}
			if contained {
				s--
			}

		}
	}
	fmt.Printf("Size %v\n", s)
}

func parseData(data []string) ([]Sensor, map[Beacon]bool) {
	sensors := make([]Sensor, 0)
	beacons := make(map[Beacon]bool, 0)
	pat := regexp.MustCompile(`^Sensor at x=(.+), y=(.+): closest beacon is at x=(.+), y=(.+)$`)
	for _, line := range data {
		sb := pat.FindAllStringSubmatch(line, -1)[0]
		sx, _ := strconv.Atoi(sb[1])
		sy, _ := strconv.Atoi(sb[2])
		bx, _ := strconv.Atoi(sb[3])
		by, _ := strconv.Atoi(sb[4])
		s := Sensor{sx, sy, bx, by, 0}
		s.SetDist()
		sensors = append(sensors, s)
		b := Beacon{bx, by}
		beacons[b] = true
	}
	return sensors, beacons
}

func mergeIntervals(intervals []*Interval) []*Interval {
	// merge intervals
	stack := make([]*Interval, 0)
	stack = append(stack, intervals[0])
	for i := 1; i < len(intervals); i++ {
		top := stack[len(stack)-1]
		// no intersection
		if top.b < intervals[i].a {
			stack = append(stack, intervals[i])
		} else if top.b < intervals[i].b {
			// merge
			top.b = intervals[i].b
		}
	}
	return stack
}

func solutionPart2(data []string, maxx, maxy int) {
	sensors, _ := parseData(data)
	for y := 0; y <= maxy; y++ {
		intervals := make([]*Interval, 0)
		for _, s := range sensors {
			interval := s.Interval(y)
			if interval != nil {
				intervals = append(intervals, interval)
			}
		}
		// sort by start
		sort.Slice(intervals, func(i, j int) bool {
			return intervals[i].a < intervals[j].a
		})
		stack := mergeIntervals(intervals)
		// for x:=0; x <= maxx; x++ {
		// 	contained := false
		// 	for _, i := range stack {
		// 		if i.a <= x && x <= i.b {
		// 			contained = true
		// 			if x == 14 && y == 11{
		// 				fmt.Printf("Contained in %v\n", i)
		// 			}
		// 			break
		// 		}
		// 	}
		// 	if !contained {
		// 		fmt.Printf("Beacon can be at %v, %v\n", x, y)
		// 		break
		// 	}
		// }

		for i:=0;i<len(stack) - 1;i++{
			// find a gap between intervals
			if stack[i].b + 1 < stack[i+1].a {
				fmt.Printf("Beacon can be at %v, %v\n", stack[i].b + 1, y)
				fmt.Printf("Tunning Freq %v\n", (stack[i].b + 1) * 4000000 + y)
				return
			}
		}
	}
}
