import assert from 'assert';
import { isSafe, solve as solve1 } from '../src/day2-part1';
import { solve as solve2, isSafeRemoving } from '../src/day2-part2';

describe('day02-part1', () => {
    const testCases = [
        { nums: [1, 2, 3, 5], safe: true },
        { nums: [7, 6, 4, 2, 1], safe: true },
        { nums: [9, 7, 6, 2, 1], safe: false },
        { nums: [1, 2, 3, 2, 4, 5], safe: false },
        { nums: [8, 6, 4, 4, 1], safe: false },
        { nums: [1, 3, 4, 7, 9], safe: true },
    ];
    for (const { nums, safe } of testCases) {
        it(`isSafe ${nums}`, () => {
            assert.equal(safe, isSafe(nums));
        });
    }

    it('solve-part1', () => {
        const s = `7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9`;
        assert.equal(solve1(s), 2);
    })
});

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
            assert.equal(isSafeRemoving(nums), safe);
        });
    }

    it('solve-part2', () => {
        const s = `7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9`;
        assert.equal(solve2(s), 4);
    })
});