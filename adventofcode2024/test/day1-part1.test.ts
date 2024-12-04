import assert from 'assert';
import { distance, solve } from '../src/day1-part1';

describe('day1-part1', () => {
    it('distance', () => {
        assert.equal(0, distance([1, 2, 3], [1, 2, 3], 0));
    });
    it('solve', () => {
        const s = `3   4
4   3
2   5
1   3
3   9
3   3`
        assert.equal(11, solve(s));
    })
});