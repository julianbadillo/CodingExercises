import * as process from 'node:process';

export const D = {
    N: [-1, 0],
    E: [0, 1],
    S: [1, 0],
    W: [0, -1],
}
export const scoreTrail = (map: number[][], r: number, c: number): number => {
    const start = [r, c];
    const queue = [start];
    const R = map.length;
    const C = map[0].length;
    const visited = new Set<string>();
    let score = 0;
    while (queue.length > 0) {
        const pos = queue.shift() as number[];
        // end trail
        if (map[pos[0]][pos[1]] === 9) {
            if (!visited.has(pos.join())) score++;
            visited.add(pos.join());
            continue;
        }
        // visited already
        if (visited.has(pos.join())) continue;
        visited.add(pos.join());
        Object.values(D).map(d => [pos[0] + d[0], pos[1] + d[1]])
            // in bounds
            .filter(pos2 => 0 <= pos2[0] && pos2[0] < R && 0 <= pos2[1] && pos2[1] <= C)
            // ascending by 1
            .filter(pos2 => map[pos2[0]][pos2[1]] === map[pos[0]][pos[1]] + 1)
            // hasn't been visited
            .filter(pos2 => !visited.has(pos2.join()))
            .forEach(pos2 => queue.push(pos2));
    }
    return score;
}

export const parseMap = (data: string): number[][] =>
    data.split('\n')
        .map(line =>
            line.split('').map(x =>
                // handle periods to use examples
                x === '.' ? -1 : Number.parseInt(x)
            )
        );

export const solve = (data: string): number => {
    const map = parseMap(data);
    return map.map((row, r) =>
        row.map((h, c) =>
            h === 0 ? scoreTrail(map, r, c) : 0
        ).reduce((a, b) => a + b)
    ).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}