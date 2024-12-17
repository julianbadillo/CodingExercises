

export type D = { dr: number, dc: number };
export type P = { r: number, c: number, d?: D };
export const pKey = (p: P) => `${p.r},${p.c}=>${p.d?.dr},${p.d?.dc}`;
export const DIRS = {
    E: { dr: 0, dc: 1 },
    S: { dr: 1, dc: 0 },
    W: { dr: 0, dc: -1 },
    N: { dr: -1, dc: 0 },
}

type M = {
    frw: number,
    cst: number,
    rotate: (d: D) => D,
}

export const MOVES: M[] = [
    {frw: 1, cst: 1, rotate: (d: D) => d},
    {frw: 0, cst: 1000, rotate: (d: D) => { return { dr: d.dc, dc: -d.dr } }}, // turn clockwise
    {frw: 0, cst: 1000, rotate: (d: D) => { return { dr: -d.dc, dc: d.dr } }}, // turn counter-clockwises
]

export const solve = (data: string): number => {
    const map: string[] = data.split('\n');
    const end = map.map((row, r) => { return { r: r, c: row.indexOf('E') } })
        .filter(x => x.c !== -1)[0] as P;
    const start = map.map((row, r) => { return { r: r, c: row.indexOf('S') } })
        .filter(x => x.c !== -1)[0] as P;
    start.d = DIRS.E; // start facing east
    const cost = new Map<string, number>();
    const visited = new Set<string>();
    const queue: P[] = [];
    queue.push(start);
    cost.set(pKey(start), 0);
    // Djikstra... without the prio queue
    while (queue.length > 0) {
        // get the lowest-cost element of the array
        const [posCost, idx] = queue.map((p, i) => [cost.get(pKey(p)) as number, i])
            .reduce((a, b) => a[0] <= b[0] ? a : b);
        const pos = queue.splice(idx, 1)[0];
        if (pos.r === end.r && pos.c === end.c) return posCost;
        if (visited.has(pKey(pos))) continue;
        visited.add(pKey(pos));
        for (const move of MOVES) {
            const pos2 = {
                r: pos.r + move.frw * (pos.d?.dr as number),
                c: pos.c + move.frw * (pos.d?.dc as number),
                d: move.rotate(pos.d as D),
            };
            if (map[pos2.r][pos2.c] === '#') continue; // wall
            const cost2 = cost.get(pKey(pos2))
            if (cost2 === undefined || posCost + move.cst < cost2) {
                queue.push(pos2);
                cost.set(pKey(pos2), posCost + move.cst);
            }
        }
    }
    return -1;
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}