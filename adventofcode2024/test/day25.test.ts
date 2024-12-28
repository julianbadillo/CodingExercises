import assert from "assert"
import { solve } from "../src/day25-part1"

describe('day25', () => {
    const s = `#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####`;



    it('solve-part1', () => {
        assert.equal(solve(s), 3);
    });

    it('solve-part2', () => {
        // assert.equal(solve2(s3), 'z00,z01,z02,z05');
    });
})