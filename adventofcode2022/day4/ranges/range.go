package ranges

type Range struct {
	First int
	Last int
}

func (r1 Range) FullyContains(r2 Range) bool {
	return r1.First <= r2.First && r2.Last <= r1.Last
}

func (r1 Range) Overlaps(r2 Range) bool {
	return (r1.First <= r2.First && r2.First <= r1.Last) || (r1.First <= r2.Last && r2.Last <= r1.Last)
}
