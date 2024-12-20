import assert from "assert";
import { solve, Computer } from "../src/day17-part1";
// import { solve as solve2 } from "../src/day16-part2";
describe('day17', () => {
    it('Computer.cycle', () => {
        let comp = new Computer();
        // basic adiv
        comp.a = 8;
        comp.prog = [0, 1];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.a, 4);

        comp = new Computer();
        comp.a = 7;
        comp.prog = [0, 1];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.a, 3);

        // bdiv
        comp = new Computer();
        comp.a = 10;
        comp.prog = [6, 0];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.b, 10);

        // cdiv
        comp = new Computer();
        comp.a = 10;
        comp.b = 2;
        comp.prog = [7, 5];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.c, 2);

        // If register C contains 9, the program 2,6 would set register B to 1.
        comp = new Computer();
        comp.c = 9;
        comp.prog = [2, 6];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.b, 1);

        // If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
        comp = new Computer();
        comp.a = 10;
        comp.prog = [5, 0, 5, 1, 5, 4];
        assert.equal(comp.cycle(), true);
        assert.equal(comp.cycle(), true);
        assert.equal(comp.cycle(), true);
        assert.equal(comp.cycle(), false);
        assert.deepEqual(comp.out, [0, 1, 2])

        // If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.
        comp = new Computer();
        comp.a = 2024;
        comp.prog = [0, 1, 5, 4, 3, 0];
        while (comp.cycle());
        assert.equal(comp.cycle(), false);
        assert.equal(comp.a, 0);
        assert.deepEqual(comp.out, [4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0]);

        // If register B contains 29, the program 1,7 would set register B to 26.
        comp = new Computer();
        comp.b = 29;
        comp.prog = [1,7]
        assert.equal(comp.cycle(), true);
        assert.equal(comp.b, 26);

        // If register B contains 2024 and register C contains 43690, the program 4,0 would set register B to 44354.
        comp = new Computer();
        comp.b = 2024;
        comp.c = 43690;
        comp.prog = [4, 0]
        assert.equal(comp.cycle(), true);
        assert.equal(comp.b, 44354);
        
    })
    const s = `Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0`;

    const s2 = `Register A: 117440
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0`
    it('solve', () => {
        assert.equal(solve(s), '4,6,3,5,6,3,5,2,1,0');
        assert.equal(solve(s2), '0,3,5,4,3,0');
    })
});