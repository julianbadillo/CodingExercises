import assert from 'assert';

import { solve, Region, getRegion } from '../src/day12-part1';
import { solve as solve2, Region as Region2, getRegion as getRegion2, scanSides } from '../src/day12-part2';

describe('day12', () => {
    const s1 = `AAAA
BBCD
BBCC
EEEC`;

    const s3 = `OOOOO
OXOXO
OOOOO
OXOXO
OOOOO`;

    const s4 = `EEEEE
EXXXX
EEEEE
EXXXX
EEEEE`;
    it('getRegion', () => {
        const m1 = s1.split('\n');
        let reg: Region = getRegion(m1, 0, 0, new Set<string>());
        assert.equal(reg.code, 'A');
        assert.equal(reg.area, 4);
        assert.equal(reg.per, 10);

        reg = getRegion(m1, 1, 0, new Set<string>());
        assert.equal(reg.code, 'B');
        assert.equal(reg.area, 4);
        assert.equal(reg.per, 8);

        reg = getRegion(m1, 1, 2, new Set<string>());
        assert.equal(reg.code, 'C');
        assert.equal(reg.area, 4);
        assert.equal(reg.per, 10);

        reg = getRegion(m1, 1, 3, new Set<string>());
        assert.equal(reg.code, 'D');
        assert.equal(reg.area, 1);
        assert.equal(reg.per, 4);

        reg = getRegion(m1, 3, 0, new Set<string>());
        assert.equal(reg.code, 'E');
        assert.equal(reg.area, 3);
        assert.equal(reg.per, 8);

        const m3 = s3.split('\n');
        reg = getRegion(m3, 0, 0, new Set<string>());
        assert.equal(reg.code, 'O');
        assert.equal(reg.area, 21);
        assert.equal(reg.per, 36);
    });

    const s2 = `RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE`;

    it('solve-part1', () => {
        assert.equal(solve(s1), 140);
        assert.equal(solve(s2), 1930);
    });

    it('getRegion2 + scan sides', () => {
        const m1 = s1.split('\n');
        let reg: Region2 = getRegion2(m1, 0, 0, new Set<string>());
        assert.equal(reg.code, 'A');
        assert.equal(reg.area, 4);
        scanSides(m1.length, m1[0].length, reg);
        assert.equal(reg.sides, 4);
        reg = getRegion2(m1, 1, 0, new Set<string>());
        assert.equal(reg.code, 'B');
        assert.equal(reg.area, 4);
        scanSides(m1.length, m1[0].length, reg);
        assert.equal(reg.sides, 4);
        reg = getRegion2(m1, 1, 2, new Set<string>());
        assert.equal(reg.code, 'C');
        assert.equal(reg.area, 4);
        scanSides(m1.length, m1[0].length, reg);
        assert.equal(reg.sides, 8);

        const m3 = s3.split('\n');
        reg = getRegion2(m3, 0, 0, new Set<string>());
        assert.equal(reg.code, 'O');
        assert.equal(reg.area, 21);
        scanSides(m3.length, m3[0].length, reg);
        assert.equal(reg.sides, 20);
        
        const m4 = s4.split('\n');
        reg = getRegion2(m4, 0, 0, new Set<string>());
        assert.equal(reg.code, 'E');
        assert.equal(reg.area, 17);
        scanSides(m4.length, m4[0].length, reg);
        assert.equal(reg.sides, 12);
    });

    const s5 = `AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA`;

    it('solve-part2', () => {
        assert.equal(solve2(s1), 80);
        assert.equal(solve2(s3), 436);
        assert.equal(solve2(s4), 236);
        assert.equal(solve2(s5), 368);
        assert.equal(solve2(s2), 1206);
    });

});