import assert from 'assert';
import { solve } from '../src/day1-part2';

describe('day01-part2', () => {
    it('solve', () => {
        const s = `3   4
4   3
2   5
1   3
3   9
3   3`;
        assert.equal(solve(s), 31);
    })
});