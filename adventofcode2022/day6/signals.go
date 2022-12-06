package main

import (
	"bufio"
	"fmt"
	"os"
	"time"
)

func main() {
	tstart := time.Now()
	data := make([]string, 0)
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		data = append(data, scanner.Text())
	}
	for _, line := range data {
		fmt.Printf("Start of 4-Packet: %v\n", startOfPacket(line, 4))
		fmt.Printf("Start of 14-Packet: %v\n", startOfPacket(line, 14))
	}
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

func startOfPacket(line string, l int) int {
	n := len(line)
	for i := 0; i < n-l+1; i++ {
		m := make(map[byte]byte)
		for j := 0; j < l; j++ {
			m[line[i+j]] = line[i+j]
		}
		//fmt.Printf("Set: %v [%v]\n", m, len(m))
		if len(m) == l {
			return i + l
		}
	}
	return n
}
