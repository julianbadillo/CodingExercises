import * as process from 'node:process';

export class Block {
    id: number | null = null;
    pos: number = 0;
    size: number = 0;
    constructor(id: number | null, pos: number, size: number) {
        this.id = id;
        this.pos = pos;
        this.size = size;
    }
    print = (): string => new Array<string | number>(this.size).fill(this.id ?? '.').join('');
    checksum(): number {
        if (this.id === null) return 0;
        // Gauss sum
        return (this.pos * this.size + (this.size - 1) * this.size / 2) * this.id;
    }
}

export const parseBlocks = (data: string): Block[] => {
    let p = 0;
    const res = data.trim()
        .split('')
        .map(c => Number.parseInt(c))
        .map((size, i) => {
            if (size == 0) return null;
            const b = new Block(i % 2 == 0 ? i / 2 : null, p, size);
            p += size;
            return b;
        })
        .filter(x => x !== null);

    return res;
}

export const squeeze = (blocks: Block[]): Block[] => {
    let i = 0;
    let j = blocks.length - 1;
    while (true) {
        // move to the closest empty block
        while (blocks[i].id !== null && i < blocks.length) i++;
        if (i >= blocks.length - 1) break;
        while (blocks[j].id === null && j > 0) j--;
        if (j < 0) break;
        if (i >= j) break;
        const space = blocks[i];
        const file = blocks[j];
        const used = Math.min(space.size, file.size);
        // insert a block
        blocks.splice(i, 0, new Block(file.id, space.pos, used));
        // reduce space
        space.size -= used;
        space.pos += used;
        // reduce file - we don't care about space left after the file
        file.size -= used;
        if (file.size == 0) {
            // remove file block
            blocks.splice(j + 1, 1);
        }
        if (space.size == 0) {
            // remove space block
            blocks.splice(i + 1, 1);
            i--;
        }
        j = blocks.length - 1;
        // console.log(blocks.map(b => b.print()).join(''));
    }
    return blocks;
}

export const solve = (data: string): number => {
    const blocks = parseBlocks(data);
    return squeeze(blocks).map(b => b.checksum()).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}