import assert from "assert";
import { doorPadPos, P, RobotChain, shortestSeq, solve } from "../src/day21-part1";
import { solve as solve2, RobotLongerChain, shortestSeqLengh } from '../src/day21-part2';

describe('day21', () => {
    it('doorPadPos', () => {
        let p = doorPadPos.get('0') as P;
        assert.equal(p.r, 3);
        assert.equal(p.c, 1);
        p = doorPadPos.get('7') as P;
        assert.equal(p.r, 0);
        assert.equal(p.c, 0);

    })

    it('RobotChain.press3', () => {

        // simultate
        let robot = new RobotChain();
        robot.press3('<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '029A');

        robot = new RobotChain();
        robot.press3('<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A');
        assert.equal(robot.out, '980A');

        robot = new RobotChain();
        robot.press3('<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '179A');

        robot = new RobotChain();
        robot.press3('<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A');
        assert.equal(robot.out, '456A');

        robot = new RobotChain();
        robot.press3('<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '379A');

        // robot = new RobotChain();
        // let seq = robot.shortestSeq3('379A');
        // robot = new RobotChain();
        // robot.press3(seq);
        // assert.equal(robot.out.join(''), '379A');
    });

    it('RobotChain.clone', () => {
        const robot = new RobotChain();
        robot.press3('<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '379A');

        const robot2 = robot.clone();
        assert.equal(robot2.p1.r, 3);
        assert.equal(robot2.p1.c, 2);
    });

    it('shortestSeq1', () => {
        assert.equal(shortestSeq('0'), '<vA<AA>>^AvAA<^A>A');
        assert.equal(shortestSeq('02'), '<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A');
        assert.equal(shortestSeq('029A').length, '<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<v<A>>^AA<vA>A^A<A>A<v<A>A>^AAA<A>vA^A'.length);
    });

    it('shortestSeq2', () => {
        assert.equal(shortestSeq('980A').length, '<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A'.length);
    });

    it('shortestSeq3', () => {
        assert.equal(shortestSeq('179A').length, '<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A'.length);
    });

    it('shortestSeq4', () => {
        assert.equal(shortestSeq('456A').length, '<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A'.length);
    });

    it('shortestSeq5', () => {
        assert.equal(shortestSeq('379A').length, '<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A'.length);
    });

    const s = `029A
980A
179A
456A
379A`;
    it('solve', () => {
        assert.equal(solve(s), 126384);
    });

    it('RobotLongerChain.press', () => {
        const robot = new RobotLongerChain(2);
        console.log(robot.ps);
        robot.press('<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '379A');
    });

    it('RobotLongerChain.clone', () => {
        const robot = new RobotLongerChain(2);
        console.log(robot.ps);
        robot.press('<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out, '379A');

        const robot2 = robot.clone();
        assert.equal(robot2.p1.r, 3);
        assert.equal(robot2.p1.c, 2);
    });
    it('shortestSeqLength', () => {
        assert.equal(shortestSeqLengh('379A', 2), '<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A'.length);
        assert.equal(shortestSeqLengh('029A', 1), 28);
        assert.equal(shortestSeqLengh('029A', 2), 68);
        assert.equal(shortestSeqLengh('029A', 3), 164);
        assert.equal(shortestSeqLengh('029A', 4), 404);

        assert.equal(shortestSeqLengh('980A', 1), 26);
        assert.equal(shortestSeqLengh('980A', 2), 60);
        assert.equal(shortestSeqLengh('980A', 3), 146);
        assert.equal(shortestSeqLengh('980A', 4), 356);

        assert.equal(shortestSeqLengh('179A', 1), 28);
        assert.equal(shortestSeqLengh('179A', 2), 68);
        assert.equal(shortestSeqLengh('179A', 3), 164);
        assert.equal(shortestSeqLengh('179A', 4), 402);


    });


    it('solve-p2', () => {
        assert.equal(solve2(s, 2), 126384);
        // assert.equal(solve2(s, 5), 1881090);
    });
});