package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
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

func parse_line(line string) (Range, Range) {
	arr := strings.Split(line, ",")
	p1 := strings.Split(arr[0], "-")
	p2 := strings.Split(arr[1], "-")
	f1, _ := strconv.Atoi(p1[0])
	l1, _ := strconv.Atoi(p1[1])
	f2, _ := strconv.Atoi(p2[0])
	l2, _ := strconv.Atoi(p2[1])
	return Range{f1, l1}, Range{f2, l2}
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
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result1, result2 := count_contained_and_overlaps(data)
	fmt.Printf("Total contained: %v\n", result1)
	fmt.Printf("Total overlaps: %v\n", result2)
}
