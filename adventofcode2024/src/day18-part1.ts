
export const DIRS = {
    E: { dr: 0, dc: 1 },
    S: { dr: 1, dc: 0 },
    W: { dr: 0, dc: -1 },
    N: { dr: -1, dc: 0 },
}

export const solve = (data: string, R: number, C: number, N: number): number => {
    const falling = data.split('\n')
        .map(line => line.split(',')
            .map(x => Number.parseInt(x))
        );
    // const R = 7;
    // const C = 7;
    // const n = 12; // first n corrupted bytes
    const corrupted = new Set<string>(falling.slice(0, N).map(x => x.join()));
    const start = [0, 0];
    const end = [R - 1, C - 1];
    const queue = [];
    const visited = new Set<string>();
    const cost = new Map<string, number>();
    queue.push(start);
    cost.set(start.join(), 0);
    while (queue.length > 0) {
        const pos = queue.shift() as number[];
        if (visited.has(pos.join())) continue;
        const posCost = cost.get(pos.join()) as number;
        if (pos[0] == end[0] && pos[1] == end[1]) return posCost;
        for (const d of Object.values(DIRS)) {
            const pos2 = [pos[0] + d.dr, pos[1] + d.dc];
            // out of bounds
            if (pos2[0] < 0 || R <= pos2[0] || pos2[1] < 0 || C <= pos2[1]) continue;
            // corrupted
            if (corrupted.has(pos2.join())) continue;
            const pos2Cost = cost.get(pos2.join());
            if (pos2Cost === undefined || posCost + 1 < pos2Cost) {
                cost.set(pos2.join(), posCost + 1);
                queue.push(pos2);
            }
        }
    }
    return -1;
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const args = process.argv.slice(2).map(x => Number.parseInt(x));
        const r = solve(data.toString(), args[0], args[1], args[2]);
        process.stdout.write(`r = ${r}\n`);
    });
}