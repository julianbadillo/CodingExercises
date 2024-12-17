
import { D, P, DIRS, pKey, MOVES } from "./day16-part1";

export const printMap = (map: string[], visited: Set<string>) => {
    for (let i = 0; i < map.length; i++) {
        for (let j = 0; j < map[0].length; j++) {
            if (visited.has(`${i},${j}`)) {
                process.stdout.write('O');
            } else {
                process.stdout.write(map[i][j]);
            }
        }
        process.stdout.write('\n');
    }
}

export const findEnd = (map: string[], start: P, end: P, pred: Map<string, P[]>): P[] => {
    const queue: P[] = [];
    const cost = new Map<string, number>(); // cost
    const visited = new Set<string>();
    queue.push(start);
    cost.set(pKey(start), 0);
    const endQueue: P[] = [];
    let minCost: number | undefined = undefined;
    // Djikstra... without the prio queue
    while (queue.length > 0) {
        // get the lowest-cost element of the array
        const [posCost, idx] = queue.map((p, i) => [cost.get(pKey(p)) as number, i])
            .reduce((a, b) => a[0] <= b[0] ? a : b);
        const pos = queue.splice(idx, 1)[0];
        // if reached the end, keep it.
        if (pos.r === end.r && pos.c === end.c) {
            endQueue.push(pos);
            if (minCost === undefined || posCost < minCost) minCost = posCost;
            continue;
        };
        if (visited.has(pKey(pos))) continue;
        visited.add(pKey(pos));
        for (const move of MOVES) {
            const pos2 = {
                r: pos.r + move.frw * (pos.d?.dr as number),
                c: pos.c + move.frw * (pos.d?.dc as number),
                d: move.rotate(pos.d as D),
            };
            if (map[pos2.r][pos2.c] === '#') continue; // wall
            const cost2 = cost.get(pKey(pos2));
            if (cost2 === undefined) {
                // not seen before
                queue.push(pos2);
                cost.set(pKey(pos2), posCost + move.cst);
                pred.set(pKey(pos2), [pos]);
            } else if (posCost + move.cst < cost2) {
                // no need to push into the queue - it's already there.
                cost.set(pKey(pos2), posCost + move.cst);
                pred.set(pKey(pos2), [pos]); // replace predecessors - this one is better
            } else if (posCost + move.cst === cost2) {
                // no need to push into the queue - it's already there
                // no need to update cost, it's already the best
                // add predecessor
                pred.get(pKey(pos2))?.push(pos);
            }
        }
    }
    // only the ones with min cost
    return endQueue.filter(p => cost.get(pKey(p)) === minCost);
}

export const solve = (data: string): number => {
    const map: string[] = data.split('\n');
    const end: P = map.map((row, r) => { return { r: r, c: row.indexOf('E') } })
        .filter(x => x.c !== -1)[0] as P;
    const start: P = map.map((row, r) => { return { r: r, c: row.indexOf('S') } })
        .filter(x => x.c !== -1)[0] as P;
    start.d = DIRS.E; // start facing east
    const pred = new Map<string, P[]>(); // links to predecessors

    const endQueue = findEnd(map, start, end, pred);
    // BFS
    const visited = new Set<string>();
    while (endQueue.length > 0) {
        const pos = endQueue.shift() as P;
        visited.add(`${pos.r},${pos.c}`);
        pred.get(pKey(pos))?.map(pos2 => endQueue.push(pos2));
    }
    // printMap(map, visited);
    return visited.size;
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}