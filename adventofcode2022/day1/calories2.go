package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

// adds up the top most calories elves carry
func max_three_calories(data []string) int {
	calories := make([]int, 0)
	sum := 0
	for _, line := range data {
		//fmt.Printf("Line %v", line)
		if line != "" {
			cal, _ := strconv.Atoi(line)
			sum += cal
		} else {
			calories = append(calories, sum)
			sum = 0
		}
	}
	calories = append(calories, sum)
	sort.Sort(sort.IntSlice(calories))
	n := len(calories)
	return calories[n-1] + calories[n-2] + calories[n-3]
}

func main() {
	fmt.Println("Part II")
	reader := bufio.NewReader(os.Stdin)
	data := make([]string, 0) // new array of strings
	for {
		line, e := reader.ReadString('\n')
		if e != nil {
			break
		}
		data = append(data, strings.TrimSpace(line))
	}
	result := max_three_calories(data)
	fmt.Printf("Result %v", result)

}
