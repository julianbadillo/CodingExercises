package utils

import "testing"

func TestAbs(t *testing.T) {
	cases := []struct {
		x, expected int
	} {
		{1, 1},
		{-1, 1},
	}
	for _, c := range cases{
		if result := Abs(c.x); result != c.expected {
			t.Errorf("ABS(%q) == %q, want %q", c.x, result, c.expected)
		}
	}
}

func TestMax(t *testing.T) {
	cases := []struct {
		x, y, expected int
	} {
		{1, 1, 1},
		{-1, 1, 1},
		{30, 2, 30},
		{-30, 2, 2},
		{30, -2, 30},		
	}
	for _, c := range cases{
		if result := Max(c.x, c.y); result != c.expected {
			t.Errorf("Max(%q) == %q, want %q", c.x, result, c.expected)
		}
	}
}

func TestMin(t *testing.T) {
	cases := []struct {
		x, y, expected int
	} {
		{1, 1, 1},
		{-1, 1, -1},
		{30, 2, 2},
		{-30, 2, -30},
		{30, -2, -2},	
	}
	for _, c := range cases{
		if result := Min(c.x, c.y); result != c.expected {
			t.Errorf("Min(%q) == %q, want %q", c.x, result, c.expected)
		}
	}
}