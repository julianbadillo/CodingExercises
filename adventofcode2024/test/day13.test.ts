import assert from 'assert';

import { solve, re1, re2, Claw } from '../src/day13-part1';
import { solve as solve2, SmarterClaw } from '../src/day13-part2';

describe('day12', () => {

    it('regex1', () => {
        let m = 'Button A: X+94, Y+34'.match(re1) as RegExpMatchArray;
        assert.equal(m[1], '94');
        assert.equal(m[2], '34');
        m = 'Button B: X+22, Y+67'.match(re1) as RegExpMatchArray;
        assert.equal(m[1], '22');
        assert.equal(m[2], '67');

        m = 'Prize: X=8400, Y=5400'.match(re2) as RegExpMatchArray;
        // console.log(m);
        assert.equal(m[1], '8400');
        assert.equal(m[2], '5400');
    });

    it('Claw', () => {
        let claw = new Claw({ x: 94, y: 34, cost: 3 }, { x: 22, y: 67, cost: 1 }, { x: 8400, y: 5400 });
        let b = claw.getPrize()
        assert.equal(b, 280);
        claw = new Claw({ x: 17, y: 86, cost: 3 }, { x: 84, y: 37, cost: 1 }, { x: 7870, y: 6450 });
        b = claw.getPrize()
        assert.equal(b, 200);
    });
    it('Claw2', () => {
        let claw = new SmarterClaw({ x: 94, y: 34, cost: 3 }, { x: 22, y: 67, cost: 1 }, { x: 8400, y: 5400 });
        let b = claw.getPrize()
        assert.equal(b, 280);
        claw = new SmarterClaw({ x: 17, y: 86, cost: 3 }, { x: 84, y: 37, cost: 1 }, { x: 7870, y: 6450 });
        b = claw.getPrize()
        assert.equal(b, 200);
        claw = new SmarterClaw({ x: 17, y: 86, cost: 3 }, { x: 84, y: 37, cost: 1 }, { x: 7870, y: 6450 });
        b = claw.getPrize()
        assert.equal(b, 200);

        // Button A: X+26, Y+66
        // Button B: X+67, Y+21
        // Prize: X=12748, Y=12176
        claw = new SmarterClaw({ x: 26, y: 66, cost: 3 }, { x: 67, y: 21, cost: 1 }, { x: 12748, y: 12176 });
        b = claw.getPrize()
        assert.equal(b, null);

    });

    const s = `Button A: X+94, Y+34
Button B: X+22, Y+67
Prize: X=8400, Y=5400

Button A: X+26, Y+66
Button B: X+67, Y+21
Prize: X=12748, Y=12176

Button A: X+17, Y+86
Button B: X+84, Y+37
Prize: X=7870, Y=6450

Button A: X+69, Y+23
Button B: X+27, Y+71
Prize: X=18641, Y=10279`;

    it('solve-part1', () => {
        assert.equal(solve(s), 480);
    });


    it('solve-part2', () => {
        assert.equal(solve2(s), 875318608908);
    });

});