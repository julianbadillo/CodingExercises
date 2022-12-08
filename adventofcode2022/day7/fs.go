package main

import (
	"bufio"
	"fmt"
	"os"
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
	root := parseOutput(data)
	//fmt.Println("-------------------")
	//root.print("")
	updateSize(&root)
	//fmt.Println("-------------------")
	//root.print("")
	fmt.Printf("Total size %v\n", sumSizes(&root, 100000))
	freeSpace := 70000000 - root.size
	neededSpace := 30000000 - freeSpace
	node := smallestToDelete(&root, neededSpace)
	fmt.Printf("Smallest to delete %v\n", node.size)
	fmt.Printf("%s elapsed\n", time.Since(tstart))

}

type Node struct {
	name     string
	dir      bool
	size     int
	parent   *Node
	children []*Node
}

func (node Node) getChild(name string) *Node {
	for _, child := range node.children {
		if child.name == name {
			return child
		}
	}
	panic("Node not found!")
}

func (node Node) print(d string) {
	fmt.Printf("%v%v [%v]\n", d, node.name, node.size)
	for _, child := range node.children {
		child.print(d +"  ")
	}
}

func parseOutput(data []string) Node {
	root := Node{name: "/", dir: true, children: make([]*Node, 0)}
	currentNode := &root
	listing := false
	for _, line := range data {
		if line[0:1] == "$" {
			listing = false
			if line == "$ cd /" {
				continue
			} else if line == "$ cd .." {
				// change to next node
				currentNode = currentNode.parent
			} else if line[0:4] == "$ cd" {
				currentNode = currentNode.getChild(line[5:])
			}  else if line == "$ ls" {
				listing = true
			}
		} else if listing {
			// dir or file
			if line[0:3] == "dir" {
				newNode := Node{name: line[4:], dir: true, children: make([]*Node, 0), parent: currentNode}
				currentNode.children = append(currentNode.children, &newNode)
			} else {
				parts := strings.Split(line, " ")
				size, _ := strconv.Atoi(parts[0])
				newNode := Node{name: parts[1], dir: false, size: size, parent: currentNode}
				currentNode.children = append(currentNode.children, &newNode)
			}
		}
		
	}
	return root
}

func updateSize(node *Node) int {
	if node.dir {
		size := 0
		for _, child := range node.children {
			size += updateSize(child)
		}
		node.size = size
	} 
	return node.size	
}

func sumSizes(root *Node, threshold int) int {
	queue := make([]*Node,0)
	queue = append(queue, root)
	totalSum := 0
	for len(queue) > 0 {
		node := queue[0]
		queue = queue[1:] // pop
		if node.dir && node.size <= threshold {
			totalSum += node.size
		}
		for _, child := range node.children {
			queue = append(queue, child)
		}
	}
	return totalSum
}

func smallestToDelete(root *Node, needed int) *Node {
	var minNode *Node
	queue := make([]*Node,0)
	queue = append(queue, root)
	for len(queue) > 0 {
		node := queue[0]
		queue = queue[1:] // pop
		if node.dir && node.size >= needed {
			if minNode == nil || node.size < minNode.size {
				minNode = node
			}
		}
		for _, child := range node.children {
			queue = append(queue, child)
		}
	}
	return minNode
}
