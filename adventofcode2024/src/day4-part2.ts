import * as process from 'node:process';
import {D} from './day4-part1';

export const dirs = [
    [D.NW, D.NE],
    [D.NE, D.SE],
    [D.SE, D.SW],
    [D.SW, D.NW],
];

export const findXmas = (mat: string[], r: number, c: number): boolean => {
    if (mat[r][c] != 'A') return false;
    for (const [d1, d2] of dirs) {
        // diagonally opposite
        const d3 = [-d1[0], -d1[1]]
        const d4 = [-d2[0], -d2[1]]
        // d1 and d2 are M's, d3 and d4 are S
        if (mat[r + d1[0]][c + d1[1]] === 'M'
            && mat[r + d2[0]][c + d2[1]] === 'M'
            && mat[r + d3[0]][c + d3[1]] === 'S'
            && mat[r + d4[0]][c + d4[1]] === 'S'){
            return true;
        }
    }
    return false;
}

export const solve = (data: string): number => {
    let s = 0;
    const mat = data.split('\n');
    const R = mat.length;
    const C = mat[0].length;
    //console.log(R, C);
    for (let i = 1; i < R - 1; i++) {
        for (let j = 1; j < C - 1; j++) {
            if (findXmas(mat, i, j)) {
                // console.log(`found at ${i} ${j}`);
                s += 1;
            }
        }
    }
    return s;
}


if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const s = solve(data.toString());
        process.stdout.write(`s = ${s}\n`);
    });
}