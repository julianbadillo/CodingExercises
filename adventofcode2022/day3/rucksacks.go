package main

import (
	"bufio"
	"fmt"
	"os"
)

func charVal(c byte) int {
	if 'a' <= c && c <= 'z' {
		return int(c - 'a')
	}
	return int(c - 'A') + 26
}

func linePrio(sack1 string, sack2 string) int {
	var let1 [52]bool
	var let2 [52]bool
	for i := 0; i < len(sack1); i++ {
		let1[charVal(sack1[i])] = true
		let2[charVal(sack2[i])] = true
	}
	// intersection
	for i := 0; i < 52; i++ {
		if let1[i] && let2[i] {
			return i + 1
		}
	}
	return 0
}

func addRucksacks(data []string) int {
	prio_sum := 0
	for _, line := range data {
		n := len(line)
		//fmt.Printf("Line '%v' length %v n/2 %v\n", line, n, n/2)
		prio_sum += linePrio(line[0:n/2], line[n/2:n])
	}
	return prio_sum
}

func findBadge(elves []string) int {
	var lets [3][52]bool
	for i, elf := range elves {
		for _, c := range elf {
			lets[i][charVal(byte(c))] = true
		}
	}
	for i := 0; i < 52; i++ {
		if lets[0][i] && lets[1][i] && lets[2][i] {
			return i + 1
		}
	}
	return 0
}

func addBadges(data []string) int {
	badge_sum := 0
	for i := 0; i < len(data); i += 3 {
		badge_sum += findBadge(data[i:i+3])
	}
	return badge_sum
}

func main() {
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result := addRucksacks(data)
	fmt.Printf("Total rucksacks: %v\n", result)
	result = addBadges(data)
	fmt.Printf("Total badges: %v\n", result)
}
