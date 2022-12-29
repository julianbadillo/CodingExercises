package main

import (
	"adventofcode2022/day6/set"
	"bufio"
	"fmt"
	"os"
	"time"
)

var data []string

func main() {
	tstart := time.Now()
	data = make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	for _, line := range data {
		fmt.Printf("Start of 4-Packet: %v\n", startOfPacket(line, 4))
		fmt.Printf("Start of 14-Packet: %v\n", startOfPacket(line, 14))
	}
	set.TryFunction()
	s := set.NewByteSet('a', 'b', 'c')
	fmt.Println(s)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

func startOfPacket(line string, l int) int {
	n := len(line)
	for i := 0; i < n-l+1; i++ {
		s := set.NewByteSetFromString(line[i:i+l])
		//fmt.Printf("Set: %v [%v]\n", m, len(m))
		if s.Len() == l {
			return i + l
		}
	}
	return n
}
