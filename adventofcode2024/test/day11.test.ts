import assert from 'assert';

import { solve, Rock } from '../src/day11';

describe('day11', () => {
    it('Rock', () => {
        const r = new Rock(89);
        const r2 = r.blink();
        assert.equal(r2.length, 2);
        assert.equal(r2[0].n, 8);
        assert.equal(r2[1].n, 9);
    });

    it('solve-part1', () => {
        assert.equal(solve('0 1 10 99 999', 1), 7);
        assert.equal(solve('125 17', 6), 22);
        assert.equal(solve('125 17', 25), 55312);
    });
})