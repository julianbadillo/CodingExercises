package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

// Get the value of the max calories
func max_calories(data []string) int {
	sum, max_sum := 0, 0
	for _, line := range data {
		//fmt.Printf("Line %v", line)
		// elves are separated by blank lines
		if line != "" {
			cal, _ := strconv.Atoi(line)
			// fmt.Printf("cal %v %v", cal, e)
			sum += cal
			// fmt.Printf("sum %v", sum)
		} else {
			if sum > max_sum {
				max_sum = sum
			}
			sum = 0
		}
	}
	if sum > max_sum {
		max_sum = sum
		sum = 0
	}
	return max_sum
}

func main1() {
	reader := bufio.NewReader(os.Stdin)
	data := make([]string, 0) // new array of strings
	for {
		line, e := reader.ReadString('\n')
		if e != nil {
			break
		}
		data = append(data, strings.TrimSpace(line))
	}
	result := max_calories(data)
	fmt.Printf("Result %v", result)

}
