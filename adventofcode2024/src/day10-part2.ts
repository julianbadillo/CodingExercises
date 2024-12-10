import * as process from 'node:process';
import { D, parseMap } from './day10-part1';

export const scoreTrailBetter = (map: number[][], r: number, c: number): number => {
    const start = [r, c];
    const queue = [start];
    const R = map.length;
    const C = map[0].length;
    let score = 0;
    while (queue.length > 0) {
        const pos = queue.shift() as number[];
        // end trail
        if (map[pos[0]][pos[1]] === 9) {
            score++;
            continue;
        }
        Object.values(D).map(d => [pos[0] + d[0], pos[1] + d[1]])
            // in bounds
            .filter(pos2 => 0 <= pos2[0] && pos2[0] < R && 0 <= pos2[1] && pos2[1] <= C)
            // ascending by 1
            .filter(pos2 => map[pos2[0]][pos2[1]] === map[pos[0]][pos[1]] + 1)
            .forEach(pos2 => queue.push(pos2));
    }
    return score;
}

export const solve = (data: string): number => {
    const map = parseMap(data);
    return map.map((row, r) =>
        row.map((h, c) =>
            h === 0 ? scoreTrailBetter(map, r, c) : 0
        ).reduce((a, b) => a + b)
    ).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}