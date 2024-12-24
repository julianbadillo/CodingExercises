import assert from "assert";
import { doorPadPos, P, RobotChain } from "../src/day21-part1";

describe('day21', () => {
    it('doorPadPos', () => {
        let p = doorPadPos.get('0') as P;
        assert.equal(p.r, 3);
        assert.equal(p.c, 1);
        p = doorPadPos.get('7') as P;
        assert.equal(p.r, 0);
        assert.equal(p.c, 0);

    })

    it('RobotChain.shortestSeq1', () => {
        let robot = new RobotChain();
        assert.equal(robot.shortestSeq1('029A'), '<A^A>^^AvvvA');
    });

    it('RobotChain.shortestSeq2', () => {
        let robot = new RobotChain();
        assert.equal(robot.shortestSeq2('029A'), '<<vA>>^A<A>AvA<^AA>A<vAAA>^A');
    });

    // it('RobotChain.shortestSeq3', () => {
    //     let robot = new RobotChain();
    //     assert.equal(robot.shortestSeq3('029A'), '<<vAA>A>^AvAA<^A>A<<vA>>^AvA^A<vA>^A<<vA>^A>AAvA^A<<vA>A>^AAAvA<^A>A');
    //     robot = new RobotChain()
    //     assert.equal(robot.shortestSeq3('029A').length, 68);
    //     robot = new RobotChain();
    //     assert.equal(robot.shortestSeq3('980A').length, 60, '980A');
    //     robot = new RobotChain();
    //     assert.equal(robot.shortestSeq3('179A').length, 68, '179A');
    //     robot = new RobotChain();
    //     assert.equal(robot.shortestSeq3('456A').length, 64, '456A');
    //     robot = new RobotChain();
    //     assert.equal(robot.shortestSeq3('379A').length, 64, '379A');


    // });

    it('RobotChain.press3', () => {
        
        // simultate
        let robot = new RobotChain();
        robot.press3('<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out.join(''), '029A');
        
        robot = new RobotChain();
        robot.press3('<v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A');
        assert.equal(robot.out.join(''), '980A');
        
        robot = new RobotChain();
        robot.press3('<v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out.join(''), '179A');

        robot = new RobotChain();
        robot.press3('<v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A');
        assert.equal(robot.out.join(''), '456A');
        
        robot = new RobotChain();
        robot.press3('<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A');
        assert.equal(robot.out.join(''), '379A');

        robot = new RobotChain();
        let seq = robot.shortestSeq3('379A');
        robot = new RobotChain();
        robot.press3(seq);
        assert.equal(robot.out.join(''), '379A');
    });

});