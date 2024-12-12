
export const D = {
    N: [-1, 0],
    E: [0, 1],
    S: [1, 0],
    W: [0, -1],
}

export type Region = {
    code: string;
    area: number;
    per: number;
}

export const getRegion = (map: string[], r: number, c: number, visited: Set<string>): Region => {
    const reg: Region = { code: map[r][c], area: 0, per: 0 };
    const R = map.length;
    const C = map[0].length;
    const queue = [];
    queue.push([r, c]);

    while (queue.length > 0) {
        const pos = queue.shift() as number[];
        // if already visited
        if (visited.has(pos.join())) continue;
        visited.add(pos.join());
        reg.area++;
        for (const d of Object.values(D)) {
            const pos2 = [pos[0] + d[0], pos[1] + d[1]];
            // outside of bounds
            if (!(0 <= pos2[0] && pos2[0] < R && 0 <= pos2[1] && pos2[1] < C)) {
                reg.per++;
                continue;
            }
            // if border with a different region
            if (map[pos2[0]][pos2[1]] !== reg.code) {
                reg.per++;
                continue;
            }
            // if visited
            if (visited.has(pos2.join())) continue;
            queue.push(pos2);
        }
    }
    return reg;
}

export const solve = (data: string): number => {
    const map = data.split('\n');
    const R = map.length;
    const C = map[0].length;
    const visited = new Set<string>();
    const regions: Region[] = [];
    for (let i = 0; i < R; i++) {
        for (let j = 0; j < C; j++) {
            if (visited.has([i, j].join())) continue;
            regions.push(getRegion(map, i, j, visited));
        }
    }
    // console.log('regions=', regions);
    return regions.map(reg => reg.per * reg.area).reduce((a, b) => a + b);
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}