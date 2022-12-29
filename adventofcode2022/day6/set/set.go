package set

import "strings"

type ByteSet struct {
	inner map[byte]bool
}

func NewByteSet(bytes ...byte) ByteSet {
	s := ByteSet{inner: make(map[byte]bool, 0)}
	for _, b := range bytes {
		s.Add(b)
	}
	return s
}

func NewByteSetFromString(str string) ByteSet {
	return NewByteSet([]byte(str)...)
}

func (bs ByteSet) String() string {
	var sb strings.Builder
	sb.WriteString("{")
	i := 0
	for b := range bs.inner {
		if i > 0 {
			sb.WriteString(", ")
		}
		sb.WriteByte(b)
		i++
	}
	sb.WriteString("}")
	return sb.String()
}


func (bs ByteSet) Len() int {
	return len(bs.inner)
}

func (bs ByteSet) Add(b byte) {
	bs.inner[b] = true
}

func (bs ByteSet) Has(b byte) bool {
	_, has := bs.inner[b]
	return has
}

func (bs ByteSet) Del(b byte) {
	if bs.Has(b) {
		delete(bs.inner, b)
	}
}

func (bs ByteSet) Union(bs2 ByteSet) ByteSet {
	s := NewByteSet()
	for b := range bs.inner {
		s.Add(b)
	}
	for b := range bs2.inner {
		s.Add(b)
	}
	return s
}

func (bs ByteSet) Intersection(bs2 ByteSet) ByteSet {
	s := NewByteSet()
	for b := range bs.inner {
		if bs2.Has(b) {
			s.Add(b)
		}
	}
	return s
}
