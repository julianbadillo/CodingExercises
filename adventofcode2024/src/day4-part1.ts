import * as process from 'node:process';

const XMAS = 'XMAS';

export const D = {
    N: [-1, 0],
    NE: [-1, 1],
    E: [0, 1],
    SE: [1, 1],
    S: [1, 0],
    SW: [1, -1],
    W: [0, -1],
    NW: [-1, -1],
}

export const findXmas = (mat: string[], r: number, c: number, d: number[]): boolean => {
    const R = mat.length;
    const C = mat[0].length;
    for (let i = 0; i < XMAS.length; i++) {
        const ri = r + i * d[0];
        const ci = c + i * d[1];
        if (ri < 0 || R <= ri) return false;
        if (ci < 0 || C <= ci) return false;
        if (mat[ri][ci] !== XMAS[i]) return false;
    }
    return true;
}

export const solve = (data: string): number => {
    let s = 0;
    const mat = data.split('\n');
    const R = mat.length;
    const C = mat[0].length;
    // console.log(R, C);
    for (let i = 0; i < R; i++) {
        for (let j = 0; j < C; j++) {
            for (const d of Object.values(D)) {
                if (findXmas(mat, i, j, d)) {
                    s += 1;
                }
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