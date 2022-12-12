package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	processSignal(data)
	printSignal(data)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

func processSignal(data []string){
	i, x, s := 1, 1, 0
	for _, inst := range data {
		switch {
		case inst == "noop":
			i++;
			s += evalCycle(i, x)
		case inst[:4] == "addx":
			n, _ := strconv.Atoi(inst[5:])
			i++
			s += evalCycle(i, x)
			x += n
			i++
			s += evalCycle(i, x)
		default:
			fmt.Println("ERROR!!!!")
		}
	}
	fmt.Printf("Total strength: %v\n", s)
}

func printSignal(data []string){
	i, x := 1, 1
	for _, inst := range data {
		switch {
		case inst == "noop":
			i++;
			drawSignal(i-1, x)
		case inst[:4] == "addx":
			n, _ := strconv.Atoi(inst[5:])
			i++
			drawSignal(i-1, x)
			x += n
			i++
			drawSignal(i-1, x)
		default:
			fmt.Println("ERROR!!!!")
		}
	}
}


func evalCycle(i, x int) int {
	switch i{
	case 20, 60, 100, 140, 180, 220:
		fmt.Printf("Cycle %v, x = %v, strength=%v\n", i, x, i * x)
		return i * x
	}
	return 0
}

func drawSignal(i, x int) {
	lit := false
	if i % 40 == x || i % 40 == x - 1 || i % 40 == x + 1 {
		lit = true
	}
	if lit {
		fmt.Print("#")
	} else {
		fmt.Print(" ")
	}
	if i % 40 == 0 {
		fmt.Println()
	}
	// fmt.Printf("Pos = %v, x = %v, lit = %v\n", i % 40, x, lit)
}