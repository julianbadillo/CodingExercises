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
	list := parseList(data)
	solutionPart1(list)
	//solutionPart2(list)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

func parseList(data []string) []int {
	l := make([]int, len(data))
	for i, line := range data {
		x, _ := strconv.Atoi(line)
		l[i] = x
	}
	return l
}

func IndexOf(arr []int, x int) int {
	for i, y := range arr {
		if x == y {
			return i
		}
	}

	return -1
}

func solutionPart1(original []int) {
	n := len(original)
	
	arr := make([]int, len(original))
	copy(arr, original)
	idx := make([]int, len(original))
	// initial index (positions relative to original)
	for i := range original {
		idx[i] = i
	}
	//fmt.Println(arr)
	for k, x := range original {
		i := IndexOf(idx, k)
		// calculate minium movement and direction
		// if it doesn't cycle
		move := x
		dir := 1
		if 0 < i + x && i + x <= n {
			
		} else {
			// move = (x + 2*n) % n
			// if i + move < n {
			// 	move --
			// } else {
			// 	move = move - n + 1
			// }
			if move < 0 {
				for ; move + i <= 0 ; move += n {}
			} else {
				for ; move + i >= n ; move -= n {}
			}
			if move < 0 {
				move++
			} else {
				move--
			}
		}
		if move < 0 {
			dir = -1	
		}
		fmt.Printf("%v Move %v \n",x, move)

		for j := 0; j != move; j+= dir {
			// swap
			arr[i+j], arr[i+j+dir] = arr[i+j+dir], arr[i+j]
			idx[i+j], idx[i+j+dir] = idx[i+j+dir], idx[i+j]
		}
		//fmt.Println(arr)
	}
	
	//fmt.Println(arr)
	k := IndexOf(original, 0)
	k = IndexOf(idx, k)
	fmt.Printf("Pos of zero is %v, pos + 1000 = %v, pos + 2000 = %v, pos + 3000 = %v, %v + %v + %v = %v\n",
	 k, (k+1000)%n, (k+2000)%n, (k+3000)%n, arr[(k+1000)%n], arr[(k+2000)%n], arr[(k+3000)%n], arr[(k+1000)%n] + arr[(k+2000)%n] + arr[(k+3000)%n])
	// fmt.Print(">")
	// fmt.Println(index)
	
}
