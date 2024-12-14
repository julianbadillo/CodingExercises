import assert from "assert";
import { Robot, solve } from "../src/day14-part1"

describe('day14', () => {
    it('Robot.fromLine', () => {
        const r = Robot.fromLine('p=9,5 v=-3,-4');
        assert.equal(r.x, 9);
        assert.equal(r.y, 5);
        assert.equal(r.vx, -3);
        assert.equal(r.vy, -4);
    });
    it('Robot.move', () => {
        let r = Robot.fromLine('p=2,4 v=2,-3');
        const MX = 11;
        const MY = 7;
        assert.equal(r.x, 2);
        assert.equal(r.y, 4);
        r.move(1, MX, MY); // 1 sec
        assert.equal(r.x, 4);
        assert.equal(r.y, 1);
        r.move(1, MX, MY); // 2 sec
        // x = 4 + 2 = 6 + 7
        assert.equal(r.x, 6);
        assert.equal(r.y, 5);
        r.move(1, MX, MY); // 3 sec
        assert.equal(r.x, 8);
        assert.equal(r.y, 2);
        r.move(1, MX, MY); // 4 sec
        assert.equal(r.x, 10);
        assert.equal(r.y, 6);
        r.move(1, MX, MY); // 5 sec
        assert.equal(r.x, 1);
        assert.equal(r.y, 3);

        r = Robot.fromLine('p=2,4 v=2,-3');
        r.move(5, MX, MY);
        assert.equal(r.x, 1);
        assert.equal(r.y, 3);
    })
    it('Robot.quad', () => {
        const MX = 11;
        const MY = 7;
        let r = new Robot(0, 2, 0, 0);
        assert.equal(r.quad(MX, MY), 0);
        r = new Robot(6, 0, 0, 0);
        assert.equal(r.quad(MX, MY), 1);
        r = new Robot(4, 5, 0, 0);
        assert.equal(r.quad(MX, MY), 2);
        r = new Robot(6, 6, 0, 0);
        assert.equal(r.quad(MX, MY), 3);
    });
    const r = `p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3`;
    it('Robot.solve', () => {
        const MX = 11;
        const MY = 7;
        assert.equal(solve(r, MX, MY), 12);
    })
});