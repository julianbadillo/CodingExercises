import assert from "assert";
import { countPatterns, fitsPattern, solve } from "../src/day19";

describe('day19', () => {
    it('fitsPattern', () => {
        const towels = new Set<string>(['r', 'wr', 'b', 'g', 'bwu', 'rb', 'gb', 'br']);
        assert.equal(fitsPattern(towels, 'brwrr'), true, 'brwrr');
        assert.equal(fitsPattern(towels, 'bggr'), true, 'bggr');
        assert.equal(fitsPattern(towels, 'gbbr'), true, 'gbbr');
        assert.equal(fitsPattern(towels, 'rrbgbr'), true, 'rrbgbr');
        assert.equal(fitsPattern(towels, 'ubwu'), false, 'ubwu');
        assert.equal(fitsPattern(towels, 'bwurrg'), true, 'bwurrg');
        assert.equal(fitsPattern(towels, 'brgr'), true, 'brgr');
        assert.equal(fitsPattern(towels, 'bbrgwb'), false, 'bbrgwb');
    });

    it('countPatterns', () => {
        const countMap = new Map<string, number>();
        const towels = new Set<string>(['r', 'wr', 'b', 'g', 'bwu', 'rb', 'gb', 'br']);
        assert.equal(countPatterns(towels, 'brwrr', countMap), 2, 'brwrr');
        assert.equal(countPatterns(towels, 'bggr', countMap), 1, 'bggr');
        assert.equal(countPatterns(towels, 'gbbr', countMap), 4, 'gbbr');
        assert.equal(countPatterns(towels, 'rrbgbr', countMap), 6, 'rrbgbr');
        assert.equal(countPatterns(towels, 'ubwu', countMap), 0, 'ubwu');
        assert.equal(countPatterns(towels, 'bwurrg', countMap), 1, 'bwurrg');
        assert.equal(countPatterns(towels, 'brgr', countMap), 2, 'brgr');
        assert.equal(countPatterns(towels, 'bbrgwb', countMap), 0, 'bbrgwb');
    });

    const s = `r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb`;

    it('solve', () => {
        assert.deepEqual(solve(s), [6, 16]);
    });
})