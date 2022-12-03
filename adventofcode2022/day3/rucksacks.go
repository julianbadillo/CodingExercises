package main

import (
	"bufio"
	"fmt"
	"os"
)

func char_val(c byte) int {
	if 'a' <= c && c <= 'z' {
		return int(c - 'a')
	}
	return int(c - 'A') + 26
}

func line_prio(sack1 string, sack2 string) int {
	var let1 [52]bool
	var let2 [52]bool
	for i := 0; i < len(sack1); i++ {
		c1, c2 := char_val(sack1[i]), char_val(sack2[i])
		let1[c1] = true
		let2[c2] = true
	}
	for i := 0; i < 52; i++ {
		if let1[i] && let2[i] {
			return i + 1
		}
	}
	return 0
}

func add_rucksacks(data []string) int {
	prio_sum := 0
	for _, line := range data {
		n := len(line)
		//fmt.Printf("Line '%v' length %v n/2 %v\n", line, n, n/2)
		prio_sum += line_prio(line[0:n/2], line[n/2:n])
	}
	return prio_sum
}

func find_badge(elves []string) int {
	var lets [3][52]bool
	for i, elf := range elves {
		for _, c := range elf {
			lets[i][char_val(byte(c))] = true
		}
	}
	for i := 0; i < 52; i++ {
		if lets[0][i] && lets[1][i] && lets[2][i] {
			return i + 1
		}
	}
	return 0
}

func add_badges(data []string) int {
	badge_sum := 0
	for i := 0; i < len(data); i += 3 {
		badge_sum += find_badge(data[i:i+3])
	}
	return badge_sum
}

func main() {
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result := add_rucksacks(data)
	fmt.Printf("Total rucksacks: %v\n", result)
	result = add_badges(data)
	fmt.Printf("Total badges: %v\n", result)
}
