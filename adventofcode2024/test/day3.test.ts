import assert from 'assert';
import { mult_re as mult_re1, solve as solve1 } from '../src/day3-part1';
import { mult_re as mult_re2, solve as solve2 } from '../src/day3-part2';

describe('day3', () => {
    it('mult_re-part1-match', () => {
        assert.deepEqual('mul(3,4)'.match(mult_re1), ['mul(3,4)']);
        assert.deepEqual('mul(13,24)'.match(mult_re1), ['mul(13,24)']);
        assert.equal('mul(13, 24)'.match(mult_re1), undefined);
        assert.equal('mul[13, 24)'.match(mult_re1), undefined);
        assert.equal('mul[3,7]'.match(mult_re1), undefined);
    });
    it('mult_re-part-1-groups', () => {
        let res = [...'xmul(2,4)%&mul[3,7]'.matchAll(mult_re1)];
        assert.equal(res.length, 1);
        assert.equal(res[0].index, 1);
        assert.equal(res[0][0], 'mul(2,4)');
        assert.equal(res[0][1], '2');
        assert.equal(res[0][2], '4');
        res = [...'do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))'.matchAll(mult_re1)];
        assert.equal(res.length, 3);
        assert.equal(res[0][0], 'mul(5,5)');
        assert.equal(res[1][0], 'mul(11,8)');
        assert.equal(res[2][0], 'mul(8,5)');
    });

    it('mult_re-part-2-groups', () => {
        let res = [...'xmul(2,4)%&mul[3,7]'.matchAll(mult_re2)];
        assert.equal(res.length, 1);
        assert.equal(res[0].index, 1);
        assert.equal(res[0][0], 'mul(2,4)');
        assert.equal(res[0][2], '2');
        assert.equal(res[0][3], '4');
        res = [..."do_not_mul(5,5)+mul(32,64]do()then(mul(11,8)..sjssdon't()mul(8,5))".matchAll(mult_re2)];
        assert.equal(res.length, 5);
        assert.equal(res[0][0], 'mul(5,5)');
        assert.equal(res[1][0], 'do()');
        assert.equal(res[2][0], 'mul(11,8)');
        assert.equal(res[3][0], "don't()");
        assert.equal(res[4][0], 'mul(8,5)');
    });
    it('solve-part1', () => {
        const s = `xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))`;
        assert.equal(161, solve1(s));
    });
    it('solve-part2', () => {
        const s = `xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))`;
        assert.equal(48, solve2(s));
    })
});