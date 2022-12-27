package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
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
	solutionPart1(data)
	//solutionPart2(data)
	fmt.Printf("%s elapsed\n", time.Since(tstart))
}

type Valve struct {
	name string
	flow int
	adj  []string
}

func (v *Valve) Print() {
	fmt.Printf("(%v, %v) -> %v\n", v.name, v.flow, v.adj)
}

func solutionPart1(data []string) {
	graph := parseTunnels(data)
	for _, v := range graph {
		v.Print()
	}
	findBestPath(graph)
}

func parseTunnels(data []string) map[string]*Valve {
	graph := make(map[string]*Valve)
	pat := regexp.MustCompile(`^Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? (.+)$`)
	for _, line := range data {
		groups := pat.FindAllStringSubmatch(line, -1)[0]
		name := groups[1]
		flow, _ := strconv.Atoi(groups[2])
		adj := strings.Split(groups[3], ", ")
		graph[name] = &Valve{name, flow, adj}
	}
	return graph
}

func findBestPath(graph map[string]*Valve) {
	// only valves that have non-zero flow
	goodValves := goodValves(graph)
	for _, v := range goodValves {
		fmt.Printf("%v\t%v\n", v, graph[v].flow)
	}
	// precalculate distance all to all
	dist := calculateDistance(graph)
	// for n, d := range dist {
	// 	fmt.Printf("%v [%v]\n", n, d)
	// }
	// Try recursion
	time := 30
	maxPressure := findBestPathRec(graph, dist, goodValves, make(map[string]int), time, "AA")
	fmt.Printf("MaxPressure %v\n", maxPressure)
}

func calculateDistance(graph map[string]*Valve) map[string]int {
	dist := make(map[string]int, 0)
	for start := range graph {
		queue := make([]string, 0)
		dist[start+"->"+start] = 0
		queue = append(queue, start)
		for len(queue) > 0 {
			node := queue[len(queue)-1]
			queue = queue[:len(queue)-1] // pop
			d := dist[start+"->"+node]
			for _, node2 := range graph[node].adj {
				// if distance already known - and shorter path already known
				if d2, found := dist[start+"->"+node2]; found && d2 < d + 1{
					continue
				}
				dist[start+"->"+node2] = d + 1
				queue = append(queue, node2)
			}
		}
	}
	return dist
}

// Only important valves
func goodValves(graph map[string]*Valve) []string {
	good := make([]string, 0)
	for _, v := range graph {
		if v.flow > 0 {
			good = append(good, v.name)
		}
	}
	return good
}

// Recursive solution, worst - just left it here because it's a reference for the recursive function
func findBestPathRec(graph map[string]*Valve, dist map[string]int, goodValves []string, open map[string]int, time int, node string) int {
	//fmt.Printf("Node %v time %v\n", node, time)
	maxPressure := 0
	if len(open) == len(goodValves) {
		printSolution(graph, open)
		return 0
	}
	for _, node2 := range goodValves {
		d := dist[node+"->"+node2]
		if node2 == node {
			continue
		}
		// time out
		if time - d - 1 < 0 {
			continue
		}
		// open already - don't go there
		if _, ok := open[node2]; ok {
			continue
		}
		open[node2] = time-d-1
		pressure := (time-d-1)*graph[node2].flow + findBestPathRec(graph, dist, goodValves, open, time-d-1, node2)
		delete(open, node2)
		if pressure > maxPressure {
			maxPressure = pressure
		}
	}
	if maxPressure == 0 {
		printSolution(graph, open)
	}
	return maxPressure
}

func printSolution(graph map[string]*Valve, open map[string]int) {
	r := 0
	for k, t := range open {
		fmt.Printf("%v [%v],", k, t)
		r += graph[k].flow * t
	}
	fmt.Printf("total=%v\n", r)
}