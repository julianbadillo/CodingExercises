import assert from 'assert';
import { solve as solve1, Node, parseMap } from '../src/day8-part1';
import { solve as solve2, allAntinodes } from '../src/day8-part2';
describe('day8', () => {

    it('class Node', () => {
        const n1 = new Node(1, 2, '0');
        assert.equal(n1.key(), `1,2:0`);
        assert.equal(n1.inBounds(3, 4), true);
        assert.equal(new Node(4, 5, '').inBounds(3, 4), false);

    });
    it('Node.antinodes', () => {
        const n1 = new Node(3, 4, 'a');
        const n2 = new Node(5, 5, 'a');
        let anti = n1.antinodes(n2);
        assert.equal(anti[0].key(), '1,3:#');
        assert.equal(anti[1].key(), '7,6:#');
        anti = n2.antinodes(n1);
        assert.equal(anti[0].key(), '7,6:#');
        assert.equal(anti[1].key(), '1,3:#');
    });

    it('All antinodes', () => {

    });

    const inText = `............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............`;

    it('parseMap', () => {
        const map = parseMap(inText);
        assert.equal(map.has('0'), true);
        assert.equal(map.has('A'), true);
        assert.equal(map.get('0')?.length, 4);
        assert.equal(map.get('A')?.length, 3);
        assert.ok(
            map.get('0')?.filter(node => node.key() === '1,8:0').length
        );
        assert.ok(
            map.get('A')?.filter(node => node.key() === '5,6:A').length
        );
    });

    it('solve-part1', () => {
        assert.equal(solve1(inText), 14);
    });
    it('solve-part2', () => {
        assert.equal(solve2(inText), 34);
        const inText2 = `T.........
...T......
.T........
..........
..........
..........
..........
..........
..........
..........`;
        assert.equal(solve2(inText2), 9);
    });
});