package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"time"
)

func IndexOf(arr []string, x string) int {
	for i, y := range arr {
		if x == y {
			return i
		}
	}
	return -1
}

type Instruct struct {
	move int
	from int
	to   int
}

func printStacks(stacks [][]string) {
	for _, stack := range stacks {
		fmt.Printf("Stack %v\n", stack)
	}
	fmt.Println()
}

func NewInstruct(line string) Instruct {
	parts := strings.Split(line, " ")
	move, _ := strconv.Atoi(parts[1])
	from, _ := strconv.Atoi(parts[3])
	to, _ := strconv.Atoi(parts[5])
	return Instruct{move, from - 1, to - 1}
}

func makeStacks(data []string) [][]string {
	fmt.Printf("Last: %v\n", data[len(data)-1])
	n := len(strings.Split(data[len(data)-1], "  "))
	fmt.Printf("stacks: %v\n", n)
	stacks := make([][]string, n)
	for i := range stacks {
		stacks[i] = make([]string, 0)
	}
	h := len(data)
	for i := h - 2; i >= 0; i-- {
		for j := 0; j < n; j++ {
			k := 1 + j*4
			if k < len(data[i]) && data[i][k:k+1] != " " {
				stacks[j] = append(stacks[j], data[i][k:k+1])
			}
		}
	}
	return stacks
}

func makeInstructs(data []string) []Instruct {
	instructs := make([]Instruct, 0)
	for _, line := range data {
		instructs = append(instructs, NewInstruct(line))
	}
	return instructs
}

func processStacks(stacks [][]string, instructs []Instruct) [][]string {
	for _, inst := range instructs {
		for i := 0; i < inst.move; i++ {
			// move one block at a atime
			s := len(stacks[inst.from])
			block := stacks[inst.from][s-1]
			stacks[inst.from] = stacks[inst.from][:s-1]
			stacks[inst.to] = append(stacks[inst.to], block)
		}
	}
	return stacks
}

func processStacks2(stacks [][]string, instructs []Instruct) [][]string {
	for _, inst := range instructs {
		s := len(stacks[inst.from])
		// move several blocks at a time
		blocks := stacks[inst.from][s-inst.move : s]
		stacks[inst.from] = stacks[inst.from][:s-inst.move]
		stacks[inst.to] = append(stacks[inst.to], blocks...)
		printStacks(stacks)
	}
	return stacks
}

func solveStacks(data []string, new bool) string {
	split := IndexOf(data, "")
	stacks := makeStacks(data[:split])
	instructs := makeInstructs(data[split+1:])

	if new {
		stacks = processStacks2(stacks, instructs)
	} else {
		stacks = processStacks(stacks, instructs)
	}
	printStacks(stacks)
	var result string
	for _, stack := range stacks {
		result += stack[len(stack)-1]
	}
	return result
}

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	result1 := solveStacks(data, false)
	fmt.Printf("Top of stacks I: %v\n", result1)
	result2 := solveStacks(data, true)
	fmt.Printf("Top of stacks II: %v\n", result2)

	fmt.Printf("%s elapsed\n", time.Since(tstart))
}
