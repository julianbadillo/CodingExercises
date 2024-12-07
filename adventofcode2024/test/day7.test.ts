import assert from 'assert';
import { solve as solve1, canBeSolved, operations, add, mul } from '../src/day7-part1';
import { solve as solve2, canBeSolved as canBeSolved2, operations as operations2, conc, next } from '../src/day7-part2';

describe('day7', () => {

    it('split-equations', () => {
        const data = `190: 10 19
3267: 81 40 27`;
        const equations = data.split('\n')
            .map(l => l.split(': '))
            .map(l => [
                Number.parseInt(l[0]),
                l[1].split(' ').map(x => Number.parseInt(x))
            ]);
        assert.equal(equations.length, 2);
        assert.deepEqual(equations[0], [190, [10, 19]]);
        assert.deepEqual(equations[1], [3267, [81, 40, 27]]);
    });
    it('bit-shift-for-pow', () => {
        // if I have 3 elements, I have 2 operators, hence 2^2 = 4 combinations
        // if I have 4 elements, 3 operators, 2^3 = 8 combinations
        assert.equal(1 << (3 - 1), 4);
        assert.equal(1 << (4 - 1), 8);
    });
    it('operations', () => {
        assert.deepEqual(operations(0b000, 3), [mul, mul, mul]);
        assert.deepEqual(operations(0b001, 3), [add, mul, mul]);
        assert.deepEqual(operations(0b001, 3), [add, mul, mul]);
        assert.deepEqual(operations(0b011, 3), [add, add, mul]);
        assert.deepEqual(operations(0b111, 3), [add, add, add]);
        const ops = operations(0b111, 3);
        assert.equal(ops[0](1, 2), 3);
        assert.equal(ops[1](3, 2), 5);
        assert.equal(ops[2](3, 5), 8);

        const nums = [1, 2, 3, 4];
        assert.equal(nums.reduce((a, b, j) => ops[j - 1](a, b)), 10);

    });
    it('operations2', () => {
        assert.deepEqual(operations2([0, 1, 2]), [add, mul, conc]);
        assert.deepEqual(operations2([1, 0, 2]), [mul, add, conc]);

        const ops = operations2([1, 2, 1]);
        const nums = [6, 8, 6, 15];
        assert.equal(nums.reduce((a, b, j) => ops[j - 1](a, b)), 7290);

    });
    it('canBeSolved', () => {
        assert.equal(canBeSolved([190, [10, 19]]), true, 'test1');
        assert.equal(canBeSolved([3267, [81, 40, 27]]), true, 'test2');
        assert.equal(canBeSolved([292, [11, 6, 16, 20]]), true, 'test3');
        assert.equal(canBeSolved([156, [15, 6]]), false, 'test4');
        assert.equal(canBeSolved([7290, [6, 8, 6, 15]]), false, 'test5');
    });
    it('fill', () => {
        const n = [1, 1, 2];
        n.fill(0, 0, 1);
        assert.deepEqual(n, [0, 1, 2]);
    });
    it('next', () => {
        const n = [0, 0, 0];
        assert.equal(next(n), true, 'first move');
        assert.deepEqual(n, [1, 0, 0]);
        assert.equal(next(n), true);
        assert.deepEqual(n, [2, 0, 0]);
        assert.equal(next(n), true);
        assert.deepEqual(n, [0, 1, 0]);
        for (let i = 0; i < 23; i++) {
            assert.equal(next(n), true, `killed at ${i}`);
        }
        assert.deepEqual(n, [2, 2, 2]);
        assert.equal(next(n), false);
    })
    it('canBeSolved2', () => {
        assert.equal(canBeSolved2([190, [10, 19]]), true, 'test1');
        assert.equal(canBeSolved2([3267, [81, 40, 27]]), true, 'test2');
        assert.equal(canBeSolved2([292, [11, 6, 16, 20]]), true, 'test3');
        assert.equal(canBeSolved2([156, [15, 6]]), true, 'test4');
        assert.equal(canBeSolved2([7290, [6, 8, 6, 15]]), true, 'test5');
        assert.equal(canBeSolved2([192, [17, 8, 14]]), true, 'test6');
    });

    const s = `190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20`;
    it('solve-part1', () => {
        assert.equal(solve1(s), 3749);
    });
    it('solve-part2', () => {
        assert.equal(solve2(s), 11387);
    })
});