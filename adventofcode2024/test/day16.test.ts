import assert from "assert";
import { MOVES, P, DIRS, solve } from "../src/day16-part1";
import { solve as solve2 } from "../src/day16-part2";
describe('day16', () => {
    const s = `###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############`;

    it('parsing', () => {
        const map: string[] = s.split('\n');
        const start = map.map((row, r) => { return { r: r, c: row.indexOf('S') } })
            .filter(x => x.c != -1)[0] as P;
        assert.equal(start.r, 13);
        assert.equal(start.c, 1);
        const end = map.map((row, r) => { return { r: r, c: row.indexOf('E') } })
            .filter(x => x.c != -1)[0] as P;
        assert.equal(end.r, 1);
        assert.equal(end.c, 13);
    });

    it('moves', () => {
        let d = MOVES[1].rotate(DIRS.E);
        assert.equal(d.dr, DIRS.S.dr);
        assert.equal(d.dc, DIRS.S.dc);
        d = MOVES[1].rotate(DIRS.S);
        assert.equal(d.dr, DIRS.W.dr);
        assert.equal(d.dc, DIRS.W.dc);
        d = MOVES[1].rotate(DIRS.W);
        assert.equal(d.dr, DIRS.N.dr);
        assert.equal(d.dc, DIRS.N.dc);
        d = MOVES[1].rotate(DIRS.N);
        assert.equal(d.dr, DIRS.E.dr);
        assert.equal(d.dc, DIRS.E.dc);

        d = MOVES[2].rotate(DIRS.E);
        assert.equal(d.dr, DIRS.N.dr);
        assert.equal(d.dc, DIRS.N.dc);
        d = MOVES[2].rotate(DIRS.N);
        assert.equal(d.dr, DIRS.W.dr);
        assert.equal(d.dc, DIRS.W.dc);
        d = MOVES[2].rotate(DIRS.W);
        assert.equal(d.dr, DIRS.S.dr);
        assert.equal(d.dc, DIRS.S.dc);
        d = MOVES[2].rotate(DIRS.S);
        assert.equal(d.dr, DIRS.E.dr);
        assert.equal(d.dc, DIRS.E.dc); 
    });

    const s2 = `#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################`;

    const s3 = 
`#####
#..E#
#S..#
#####`;
    it('solve', ()=> {
        assert.equal(solve(s), 7036);
        assert.equal(solve(s2), 11048);
    });

    it('solve2', () => {
        assert.equal(solve2(s3), 4);
        assert.equal(solve2(s), 45);
        assert.equal(solve2(s2), 64);
    });
})