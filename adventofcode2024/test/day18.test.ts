import assert from "assert";
import { solve } from "../src/day18-part1";
// import { solve as solve2 } from "../src/day16-part2";
describe('day18', () => {
    
    const s = `5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0`;

    it('solve', () => {
        assert.equal(solve(s, 7, 7, 12), 22);
    });
});