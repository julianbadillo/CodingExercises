package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
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
	//fmt.Println(data)

	// fmt.Printf("%v = -1\n", compareN(NewN(1), NewN(2)))
	// fmt.Printf("%v = 1\n", compareN(NewN(2), NewN(1)))
	// fmt.Printf("%v = -1\n", compareN(&N{l: []*N{NewN(1)}}, NewN(2)))
	// fmt.Printf("%v = 1\n", compareN(NewN(2), &N{l: []*N{NewN(1)}}))
	// fmt.Printf("%v = 0\n", compareN(&N{l: []*N{NewN(1)}}, &N{l: []*N{NewN(1)}}))
	// fmt.Printf("%v = -1\n", compareN(&N{l: []*N{NewN(1)}}, &N{l: []*N{NewN(1), NewN(2)}}))
	// fmt.Printf("%v = 1\n", compareN(&N{l: []*N{NewN(1), NewN(2)}}, &N{l: []*N{NewN(1)}}))
	// n1 := &N{l: []*N{{l: []*N{{l: []*N{}}}}}}
	// n2 := &N{l: []*N{{l: []*N{}}}}
	// fmt.Printf("%v = ?\n", compareN(n1, n2))
	// fmt.Printf("%v = ?\n", compareN(n2, n1))
	solutionPart1(data)
	solutionPart2(data)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type N struct {
	val *int
	l   []*N
}

func (n1 N) Print() {
	if n1.val != nil {
		fmt.Printf("%v ", *n1.val)
	} else {
		fmt.Printf("{")
		for _, n2 := range n1.l {
			n2.Print()
		}
		fmt.Printf("}")
	}
}

func NewN(val int) *N {
	return &N{val: &val}
}

func solutionPart1(data []string) {
	s := 0
	for i := 0; i*3 < len(data); i ++ {
		n1, n2 := parseN(data[3*i]), parseN(data[3*i+1])
		//n1.Print()
		//n2.Print()
		if c := compareN(n1, n2); c < 0{
			s += (i+1)
		}
	}
	fmt.Printf("Solution I %v\n", s)
}

func solutionPart2(data []string) {
	// divider packets
	d1 := parseN("[[2]]")
	d2 := parseN("[[6]]")
	// No need to sort, just see how many are smaller
	//list := make([]*N, 0)
	//list = append(list, d1)
	//list = append(list, d2)
	// for _, line := range data{
	// 	if line != "" {
	// 		list = append(list, parseN(line))
	// 	}
	// }
	// sort.Slice(list, func(i, j int) bool {
	// 	return compareN(list[i], list[j]) < 0
	// })
	// r := 1
	// for i, n1 := range list {
	// 	n1.Print()
	// 	fmt.Println()
	// 	if n1 == d1 || n1 == d2 {
	// 		r *= (i+1)
	// 	}
	// }

	r1, r2 := 0, 0
	for _, line := range data {
		//n1.Print()
		//fmt.Println()
		if line == "" {
			continue
		}
		n1 := parseN(line)
		if compareN(n1, d1) < 0 {
			r1 ++
		}
		if compareN(n1, d2) < 0 {
			r2 ++
		}
	}
	fmt.Printf("%v %v ", r1, r2)
	r := (r1 + 1) * (r2 + 2)
	
	fmt.Printf("Solution II %v\n", r)
}

func parseN(str string) *N {
	pat, err := regexp.Compile(`(\[|\]|,|\d+)`)
	if err != nil {
		panic(err)
	}
	tokens := pat.FindAllString(str, -1)
	stack := make([]*N, 0)
	var root *N
	for i:=0; i < len(tokens); {
		// fmt.Printf("Token '%v'\n", tokens[i])
		switch tokens[i] {
		case "[":
			// fmt.Printf("Case 1\n")
			n2 := &(N{l: make([]*N, 0)}) 
			if len(stack) > 0{
				top := stack[len(stack) - 1]
				top.l = append(top.l, n2)	
			}
			stack = append(stack, n2)
			i += 1
		case ",":
			// fmt.Printf("Case 2\n")
			i += 1
		case "]":
			// fmt.Printf("Case 3\n")
			root = stack[len(stack) - 1]
			stack = stack[:len(stack) - 1]
			i += 1
		default:
			// fmt.Printf("Case 4\n")
			val, _ := strconv.Atoi(tokens[i])
			n2 := N{val: &val}
			top := stack[len(stack) - 1]
			top.l = append(top.l, &n2)
			i += 1
		}
	}
	return root
}


func compareN(n1 *N, n2 *N) int {
	// both elemnts are int
	if n1.val != nil && n2.val != nil {
		if *n1.val < *n2.val {
			return -1
		}
		if *n1.val > *n2.val {
			return 1
		}
		return 0
	}
	// both elements are lists
	if n1.l != nil && n2.l != nil {
		for i := 0; i < len(n1.l) && i < len(n2.l); i++ {
			if d := compareN(n1.l[i], n2.l[i]); d != 0 {
				return d
			}
		}
		if len(n1.l) < len(n2.l) {
			return -1
		}
		if len(n1.l) > len(n2.l) {
			return 1
		}
		return 0
	}
	// if only one of them is value
	if n1.val != nil && n2.l != nil {
		n1_l := N{} // wrap in a list
		n1_l.l = []*N{n1}
		return compareN(&n1_l, n2)
	}
	if n1.l != nil && n2.val != nil {
		n2_l := N{} // wrap in a list
		n2_l.l = []*N{n2}
		return compareN(n1, &n2_l)
	}
	return 0
}
