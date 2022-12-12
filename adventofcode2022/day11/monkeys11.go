package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	monkeys := parseMonkeys(data)
	fmt.Println("First part: ")
	rounds(monkeys, 20, true)
	monkeys2 := parseMonkeys(data)
	fmt.Println("Second part: ")
	rounds(monkeys2, 10000, false)
	//fmt.Println(monkeys)
	//fmt.Println(evalPackage(10, "old * old"))
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Monkey struct {
	items []int
	op string
	test int
	mtrue int
	mfalse int
	inspected int
}

func parseMonkeys(data []string) []*Monkey {
	monkeys := make([]*Monkey, 0)
	for i:=0; i<len(data); {
		monkey := Monkey {items: make([]int, 0)}
		i++
		parts := strings.Split(strings.Replace(data[i], "  Starting items: ", "", 1), ", ")
		for _, p := range parts {
			x, _ := strconv.Atoi(p)
			monkey.items = append(monkey.items, x)
		}
		i++
		// 
		monkey.op = strings.Replace(data[i], "  Operation: new = ", "", 1)
		i++
		
		d, _ := strconv.Atoi(strings.Replace(data[i], "  Test: divisible by ", "", 1))
		monkey.test = d
		
		i++
		t, _ := strconv.Atoi(strings.Replace(data[i], "    If true: throw to monkey ", "", 1))
		monkey.mtrue = t
		
		i++
		f, _ := strconv.Atoi(strings.Replace(data[i], "    If false: throw to monkey ", "", 1))
		monkey.mfalse = f
		i+=2
		monkeys = append(monkeys, &monkey)
	}

	return monkeys
}

func evalPackage(val int, exp string) int {
	exp = strings.Replace(exp, "old", strconv.Itoa(val), -1)
	if strings.Contains(exp, "*") {
		parts := strings.Split(exp, " * ")
		p1, _ := strconv.Atoi(parts[0])
		p2, _ := strconv.Atoi(parts[1])
		return p1 * p2
	} else {
		parts := strings.Split(exp, " + ")
		p1, _ := strconv.Atoi(parts[0])
		p2, _ := strconv.Atoi(parts[1])
		return p1 + p2
	}
} 

func rounds(monkeys []*Monkey, n int, divide bool) {
	ring := 1
	for _, monkey := range monkeys {
		ring *= monkey.test
	}

	for i:=0; i< n; i++ {
		for _, monkey := range monkeys {
			monkey.inspected += len(monkey.items) // count
			for len(monkey.items) > 0 {
				item := monkey.items[0]
				//fmt.Printf(" Monkey %v item %v ", m, item)
				monkey.items = monkey.items[1:]// pop
				item = evalPackage(item, monkey.op)
				//fmt.Printf(" worry %v ", item)
				if divide {
					item = item / 3
				}
				// TODO modulo!!!
				item = item % ring
				//fmt.Printf(" bored %v ", item)
				if item % monkey.test == 0 {
					//fmt.Printf(" True %v ", monkey.mtrue)
					monkeys[monkey.mtrue].items = append(monkeys[monkey.mtrue].items, item)
				} else {
					//fmt.Printf(" False %v ", monkey.mfalse)
					monkeys[monkey.mfalse].items = append(monkeys[monkey.mfalse].items, item)
				}
				//fmt.Println()
			}
		}
	}
	for _, monkey := range monkeys {
		fmt.Println(monkey.inspected)
	}
	sort.Slice(monkeys, func(i, j int) bool {
		return monkeys[i].inspected > monkeys[j].inspected
	})
	fmt.Printf("Answer is %v x %v = %v\n", monkeys[0].inspected, monkeys[1].inspected, monkeys[0].inspected*monkeys[1].inspected)
}