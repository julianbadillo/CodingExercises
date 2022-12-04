package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"time"
)

type Range struct {
	first int
	last int
}

func (r1 Range) FullyContains(r2 Range) bool {
	return r1.first <= r2.first && r2.last <= r1.last
}

func (r1 Range) Overlaps(r2 Range) bool {
	return (r1.first <= r2.first && r2.first <= r1.last) || (r1.first <= r2.last && r2.last <= r1.last)
}

func NewRange(s string) Range {
	p := strings.Split(s, "-")
	f, _ := strconv.Atoi(p[0])
	l, _ := strconv.Atoi(p[1])
	return Range{f, l}
}

func parse_line(line string) (Range, Range) {
	arr := strings.Split(line, ",")
	return NewRange(arr[0]),  NewRange(arr[1])
}

func count_contained_and_overlaps(data []string) (int, int) {
	contained, overlapped := 0, 0
	for _, line := range data {
		r1, r2 := parse_line(line)
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
	result1, result2 := count_contained_and_overlaps(data)
	fmt.Printf("Total contained: %v\n", result1)
	fmt.Printf("Total overlaps: %v\n", result2)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}
