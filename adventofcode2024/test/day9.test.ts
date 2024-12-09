import assert from 'assert';
import { solve as solve1, parseBlocks, Block, squeeze } from '../src/day9-part1';
import { solve as solve2, squeezeFull } from '../src/day9-part2';

describe('day9', () => {

    it('class Block', () => {
        assert.equal(new Block(null, 12, 4).print(), '....');
        assert.equal(new Block(7, 12, 3).print(), '777');
    });
    it('parseBlocks-part1', () => {
        let blocks = parseBlocks('12345');
        assert.equal(blocks.length, 5);
        assert.equal(blocks[0].id, 0);
        assert.equal(blocks[0].pos, 0);
        assert.equal(blocks[0].size, 1);
        assert.equal(blocks[1].id, null);
        assert.equal(blocks[1].pos, 1);
        assert.equal(blocks[1].size, 2);
        assert.equal(blocks[2].id, 1);
        assert.equal(blocks[2].pos, 3);
        assert.equal(blocks[2].size, 3);
        assert.equal(blocks[3].id, null);
        assert.equal(blocks[3].pos, 6);
        assert.equal(blocks[3].size, 4);
        assert.equal(blocks[4].id, 2);
        assert.equal(blocks[4].pos, 10);
        assert.equal(blocks[4].size, 5);
        blocks = parseBlocks('2333133121414131402');
        assert.equal(blocks.length, 18);
    });
    it('array splicing', () => {
        const arr = [0, 1, 2, 3];
        arr.splice(1, 0, 5);
        assert.deepEqual(arr, [0, 5, 1, 2, 3]);
        arr.splice(3, 0, 100);
        assert.deepEqual(arr, [0, 5, 1, 100, 2, 3]);
    });
    it('squeezing', () => {
        let blocks = parseBlocks('12345');
        squeeze(blocks);
        assert.equal(blocks.map(b => b.print()).join(''), '022111222.');
        blocks = parseBlocks('2333133121414131402');
        squeeze(blocks);
        assert.equal(blocks.map(b => b.print()).join(''), '0099811188827773336446555566..');
    });
    it('checksum', () => {
        const block = new Block(9, 2, 2);
        assert.equal(block.checksum(), 18 + 27);
        const blocks = parseBlocks('2333133121414131402');
        squeeze(blocks);
        const chs = blocks.map(b => b.checksum()).reduce((a, b) => a + b);
        assert.equal(chs, 1928);
    });
    it('solve-part1', () => {
        assert.equal(solve1('2333133121414131402'), 1928);
        assert.equal(solve1('12345'), 60);
    });
    it('squeezeFull', () => {
        const blocks = parseBlocks('2333133121414131402');
        squeezeFull(blocks);
        assert.equal(blocks.map(b => b.print()).join(''), '00992111777.44.333....5555.6666.....8888..');
    });
    it('solve-part2', () => {
        assert.equal(solve2('2333133121414131402'), 2858);
        assert.equal(solve2('12345'), 132);
    });
});