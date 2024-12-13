import { D } from './day12-part1';

export type Region = {
    code: string;
    area: number;
    sides: number;
    // keep the area points
    contained: Set<string>;
}

export const getRegion = (map: string[], r: number, c: number, visited: Set<string>): Region => {
    const reg: Region = { code: map[r][c], area: 0, sides: 0, contained: new Set<string>() };
    const R = map.length;
    const C = map[0].length;
    const queue = [];
    queue.push([r, c]);
    while (queue.length > 0) {
        const pos = queue.shift() as number[];
        // if already visited
        if (visited.has(pos.join())) continue;
        visited.add(pos.join());
        reg.contained.add(pos.join());
        reg.area++;
        for (const d of Object.values(D)) {
            const pos2 = [pos[0] + d[0], pos[1] + d[1]];
            // outside of bounds
            if (!(0 <= pos2[0] && pos2[0] < R && 0 <= pos2[1] && pos2[1] < C)) continue;
            // if border with a different region
            if (map[pos2[0]][pos2[1]] !== reg.code) continue;
            // if visited
            if (visited.has(pos2.join())) continue;
            queue.push(pos2);
        }
    }
    return reg;
}

export const scanSides = (R: number, C: number, reg: Region): Region => {
    let edges = new Set<string>();
    // scan vertically -
    for (let i = 0; i < R; i++) {
        let inReg = false;
        // make a list if in -> out or out -> to identify where are the sides
        const edges2: string[] = [];
        for (let j = 0; j < C; j++) {
            if (reg.contained.has([i, j].toString()) !== inReg) {
                edges2.push(`${j}:${inReg ? '<' : '>'}`);
                inReg = !inReg;
            }
        }
        if (inReg) edges2.push(`${C}:<`);
        // only count new sides
        reg.sides += edges2.filter(e => !edges.has(e)).length;
        edges = new Set(edges2);
    }
    edges = new Set<string>();
    // scan horizontally
    for (let j = 0; j < C; j++) {
        let inReg = false;
        // make a list if in -> out or out -> to identify where are the sides
        const edges2: string[] = [];
        for (let i = 0; i < R; i++) {
            if (reg.contained.has([i, j].toString()) !== inReg) {
                edges2.push(`${i}:${inReg ? '<' : '>'}`);
                inReg = !inReg;
            }
        }
        if (inReg) edges2.push(`${R}:<`);
        // only count new sides
        reg.sides += edges2.filter(e => !edges.has(e)).length;
        edges = new Set(edges2);
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
            const reg = getRegion(map, i, j, visited);
            scanSides(R, C, reg);
            regions.push(reg);
        }
    }
    // console.log('regions=', regions);
    return regions.map(reg => reg.sides * reg.area).reduce((a, b) => a + b);
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}