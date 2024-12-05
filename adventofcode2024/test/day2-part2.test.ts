import assert from 'assert';
import { solve, isSafeRemoving } from '../src/day2-part2';

describe('day2-part2', () => {

    const testCases = [
        { nums: [1, 2, 3, 5], safe: true },
        { nums: [7, 6, 4, 2, 1], safe: true },
        { nums: [9, 7, 6, 2, 1], safe: false },
        { nums: [1, 3, 2, 4, 5], safe: true },
        { nums: [1, 2, 3, 2, 4, 5], safe: true },
        { nums: [8, 6, 4, 4, 1], safe: true },
        { nums: [1, 3, 4, 7, 9], safe: true },
    ];
    for (const { nums, safe } of testCases) {
        it(`isSafeRemoving ${nums}`, () => {
            assert.equal(safe, isSafeRemoving(nums));
        });
    }

    it('solve', () => {
        const s = `7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9`;
        assert.equal(4, solve(s));
    })
});