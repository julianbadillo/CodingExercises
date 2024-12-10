import assert from 'assert';
import { solve as solve1, P, parseMap} from '../src/day6-part1';
import { solve as solve2} from '../src/day6-part2';
describe('day06', () => {

    it('class P', () => {
        // test they are treated as the same
        const p1 = new P(1, 2);
        const p2 = new P(1, 2);
        // in a map
        const s = new Map<string,P>();
        s.set(p1.toString(), p1);
        s.set(p2.toString(), p2);
        assert.equal(s.size, 1);
        const p3 = new P(1, 2);
        assert.ok(s.has(p3.toString()));
    });

    it('parseMap', () => {
        const map = new Map<string, P>();
        const lines = [
            '...#',
            '..#.',
            '.#.^',
        ]
        const [pos, dir] = parseMap(lines, map);
        assert.equal(pos.r, 2);
        assert.equal(pos.c, 3);
        assert.ok(map.has('0,3'));
        assert.ok(map.has('1,2'));
        assert.ok(map.has('2,1'));
        assert.equal(dir.toString(), '-1,0');
    });

    it('rotate', () => {
        let dir = new P(-1, 0);
        dir = new P(dir.c, -dir.r);
        assert.equal(dir.toString(), '0,1');
        dir = new P(dir.c, -dir.r);
        assert.equal(dir.toString(), '1,0');
        dir = new P(dir.c, -dir.r);
        assert.equal(dir.toString(), '0,-1');
        dir = new P(dir.c, -dir.r);
        assert.equal(dir.toString(), '-1,0');
        
    });
    const s = `....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...`;
    it('solve-part1', () => {
        assert.equal(solve1(s), 41);
    });
    it('solve-part2', () => {
        assert.equal(solve2(s), 6);
    });
});