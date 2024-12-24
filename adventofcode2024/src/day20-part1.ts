import { DIRS } from "./day18-part1";

export class Coord {
    r: number = 0;
    c: number = 0;
    cheats: number = 0;
    constructor(r: number, c: number, cheats: number) {
        this.c = c;
        this.r = r;
        this.cheats = cheats;
    }
    key(): string { return `${this.r},${this.c},${this.cheats}`; }
    static fromString(str: string): Coord {
        const parts = str.split(',').map(x => Number.parseInt(x));
        return new Coord(parts[0], parts[1], parts[2]);
    }
}

export const bestPath = (map: string[], start: Coord, end: Coord, prevCheats: Set<string>): number => {
    const cost = new Map<string, number>();
    const R = map.length;
    const C = map[0].length;
    const visited = new Set<string>();
    const prev = new Map<string, Coord>();
    const queue: Coord[] = [];
    queue.push(start);
    cost.set(start.key(), 0);
    let posCost = 0;
    let pos = start;
    while (queue.length > 0) {
        pos = queue.shift() as Coord;
        posCost = cost.get(pos.key()) as number;
        if (pos.r == end.r && pos.c == end.c) break;
        if (visited.has(pos.key())) continue;
        visited.add(pos.key());
        for (const d of Object.values(DIRS)) {
            const pos2 = new Coord(pos.r + d.dr, pos.c + d.dc, pos.cheats);
            if (pos2.r < 0 || R <= pos2.r || pos2.c < 0 || C <= pos2.c) continue;
            if (map[pos2.r][pos2.c] === '#')
                pos2.cheats--;
            if (pos2.cheats < 0) continue;
            // can't reuse cheats
            if (prevCheats.has(pos2.key())) continue;
            const pos2Cost = cost.get(pos2.key());
            if (pos2Cost === undefined || posCost + 1 < pos2Cost) {
                cost.set(pos2.key(), posCost + 1);
                prev.set(pos2.key(), pos);
                queue.push(pos2);
            }
        }
    }
    // follow path back
    while (pos.r != start.r || pos.c != start.c) {
        const prevPos = prev.get(pos.key()) as Coord;
        // console.log(prevPos, '=>', pos);
        if (prevPos.cheats != pos.cheats) {
            // console.log('cheated here!');
            prevCheats.add(pos.key());
        }
        pos = prevPos;
    }
    return posCost;
}



export const solve = (data: string, threshold: number): number => {
    const map = data.split('\n');
    const start = map.map((line, r) => [r, line.indexOf('S')])
        .filter(x => x[1] != -1)
        .map(x => new Coord(x[0], x[1], 0))[0];
    const end = map.map((line, r) => [r, line.indexOf('E')])
        .filter(x => x[1] != -1)
        .map(x => new Coord(x[0], x[1], 0))[0];
    const allCheats = new Set<string>();
    const baseline = bestPath(map, start, end, allCheats);
    // console.log(`baseline = ${baseline}`);
    let cost = 0;
    let i = 0;
    while (true) {
        cost = bestPath(map, new Coord(start.r, start.c, 1), end, allCheats);
        // console.log(`cost = ${cost}, saves=${baseline - cost}`)
        if (baseline - cost >= threshold) {
            i++;
        } else break;
    }
    return i;
}
if (require.main === module) {
    process.stdin.on('data', (data) => {
        const args = process.argv.slice(2).map(x => Number.parseInt(x));
        const r = solve(data.toString(), args[0]);
        process.stdout.write(`r = ${r}\n`);
    });
}