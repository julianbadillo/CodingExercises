package main

// Node for a file system hierarchy
type Node struct {
	name     string
	dir      bool
	size     int
	parent   *Node
	children []*Node
}
