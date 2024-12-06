import assert from 'assert';
import { findXmas as findXmas1, solve as solve1, D } from '../src/day4-part1';
import { findXmas as findXmas2, solve as solve2 } from '../src/day4-part2';

describe('day4', () => {
    it('findXmas-part1', () => {
        let m = [
            'XMAS',
            'MMAA',
            'AAAA',
            'SAAS',
        ];
        assert.equal(findXmas1(m, 0, 0, D.E), true);
        assert.equal(findXmas1(m, 1, 0, D.E), false);
        assert.equal(findXmas1(m, 0, 0, D.S), true);
        assert.equal(findXmas1(m, 0, 1, D.S), false);
        assert.equal(findXmas1(m, 0, 0, D.SE), true);

        m = [
            'SAAS',
            'MAAA',
            'AAMM',
            'SAMX',
        ];
        assert.equal(findXmas1(m, 3, 3, D.N), true);
        assert.equal(findXmas1(m, 3, 2, D.N), false);
        assert.equal(findXmas1(m, 3, 3, D.W), true);
        assert.equal(findXmas1(m, 2, 3, D.W), false);
        assert.equal(findXmas1(m, 3, 3, D.NW), true);
        m = [
            'SAAS',
            'MAAA',
            'AMMM',
            'XAMX',
        ];
        assert.equal(findXmas1(m, 3, 0, D.NE), true);
        m = [
            'SAAX',
            'MAMA',
            'AAMM',
            'SAMX',
        ];
        assert.equal(findXmas1(m, 0, 3, D.SW), true);
    });
    it('findXmas-part2', () => {
        let m = [
            'MXS',
            'MAA',
            'MXS',
        ];
        assert.equal(findXmas2(m, 1, 1), true);
        m = [
            'SXS',
            'MAA',
            'MXM',
        ];
        assert.equal(findXmas2(m, 1, 1), true);
        m = [
            'SXM',
            'MAA',
            'SXM',
        ];
        assert.equal(findXmas2(m, 1, 1), true);
        m = [
            'MXM',
            'MAA',
            'SXS',
        ];
        assert.equal(findXmas2(m, 1, 1), true);

        m = [
            'MXS',
            'MAA',
            'SXM',
        ];
        assert.equal(findXmas2(m, 1, 1), false);

    });
    it('solve-part1', () => {
        const s = `MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX`;
        assert.equal(solve1(s), 18);
    });

    it('solve-part2', () => {
        const s = `MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX`;
        assert.equal(solve2(s), 9);
    });


});