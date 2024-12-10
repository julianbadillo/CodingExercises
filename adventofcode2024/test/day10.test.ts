import assert from 'assert';
import { scoreTrail, solve, parseMap } from '../src/day10-part1';
import { solve as solve2, scoreTrailBetter } from '../src/day10-part2';

describe('day10', () => {
    it('scoreTrail', () => {
        let map = parseMap(`0123
1234
8765
9876`);
        assert.equal(scoreTrail(map, 0, 0), 1);
        map = parseMap(`...0...
...1...
...2...
6543456
7.....7
8.....8
9.....9`);
        assert.equal(scoreTrail(map, 0, 3), 2);
        map = parseMap(`..90..9
...1.98
...2..7
6543456
765.987
876....
987....`);
        assert.equal(scoreTrail(map, 0, 3), 4);
        map = parseMap(`10..9..
2...8..
3...7..
4567654
...8..3
...9..2
.....01`);
        assert.equal(scoreTrail(map, 0, 1), 1);
        assert.equal(scoreTrail(map, 6, 6), 2);
    });

    it(`scoreTrailBetter`, () => {
        let map = parseMap(`.....0.
..4321.
..5..2.
..6543.
..7..4.
..8765.
..9....`);
        assert.equal(scoreTrailBetter(map, 0, 5), 3);
        map = parseMap(`012345
123456
234567
345678
4.6789
56789.`);
        assert.equal(scoreTrailBetter(map, 0, 0), 227);
    });
    const s = `89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732`;

    it('solve-part1', () => {
        assert.equal(solve(s), 36);
    });
    it('solve-part2', () => {
        assert.equal(solve2(s), 81);
    });

});