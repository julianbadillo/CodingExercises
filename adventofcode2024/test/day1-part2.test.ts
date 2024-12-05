import assert from 'assert';
import { solve } from '../src/day1-part2';

describe('day1-part2', () => {
    it('solve', () => {
        const s = `3   4
4   3
2   5
1   3
3   9
3   3`;
        assert.equal(31, solve(s));
    })
});