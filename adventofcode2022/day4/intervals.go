package main

import (
	"adventofcode2022/day4/ranges"
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"time"
)


func NewRange(s string) ranges.Range {
	p := strings.Split(s, "-")
	f, _ := strconv.Atoi(p[0])
	l, _ := strconv.Atoi(p[1])
	return ranges.Range{f, l}
}

func parseLine(line string) (ranges.Range, ranges.Range) {
	arr := strings.Split(line, ",")
	return NewRange(arr[0]),  NewRange(arr[1])
}

func countContainedAndOverlaps(data []string) (int, int) {
	contained, overlapped := 0, 0
	for _, line := range data {
		r1, r2 := parseLine(line)
		//fmt.Printf(" %v %v  \n", r1, r2)
		if r1.FullyContains(r2) || r2.FullyContains(r1) {
			contained += 1
		}
		if r1.Overlaps(r2) || r2.Overlaps(r1) {
			overlapped += 1
		}
	}
	return contained, overlapped
}

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result1, result2 := countContainedAndOverlaps(data)
	fmt.Printf("Total contained: %v\n", result1)
	fmt.Printf("Total overlaps: %v\n", result2)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}
