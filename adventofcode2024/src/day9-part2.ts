import * as process from 'node:process';
import { Block, parseBlocks } from './day9-part1';

export const squeezeFull = (blocks: Block[]): Block[] => {
    let i = 0;
    const files = blocks.filter(b => b.id !== null);
    let j = files.length - 1;
    while (j > 0) {
        const file = files[j];
        // find space
        for (i = 0; i < blocks.length && (blocks[i].id !== null || file.size > blocks[i].size); i++);
        // block doesn't fit any empty space
        if (i >= blocks.length || blocks[i].pos >= file.pos) {
            j--;
            continue;
        }
        const space = blocks[i];
        // insert a block
        blocks.splice(i, 0, new Block(file.id, space.pos, file.size));
        // reduce space
        space.size -= file.size;
        space.pos += file.size;
        // file becomes empty space
        file.id = null;
        if (space.size == 0) {
            // remove space block
            blocks.splice(i + 1, 1);
        }
        j--;
        // console.log(blocks.map(b => b.print()).join(''));
    }
    return blocks;
}

export const solve = (data: string): number => {
    const blocks = parseBlocks(data);
    return squeezeFull(blocks).map(b => b.checksum()).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}