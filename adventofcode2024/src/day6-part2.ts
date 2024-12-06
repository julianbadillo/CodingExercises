import * as process from 'node:process';
import { P, parseMap } from './day6-part1';

export const moveLoop = (obs: Map<string, P>, R: number, C: number, pos: P, dir: P): boolean => {
    const visited = new Map<string, P>();
    const visited_dirs = new Map<string, P[]>();
    while (true) {
        // console.log(`pos ${pos} dir ${dir}`);
        // if previously visited on the same direction
        if (visited.has(pos.toString())
            && (visited_dirs.get(pos.toString()) ?? [])
                .filter(d => d.toString() === dir.toString()).length > 0)
            return true;
        visited.set(pos.toString(), pos);
        if (visited_dirs.has(pos.toString())) {
            visited_dirs.get(pos.toString())?.push(dir);
        } else {
            visited_dirs.set(pos.toString(), [dir]);
        }
        // try to move
        const next = new P(pos.r + dir.r, pos.c + dir.c);
        // out of bounds
        if (next.r < 0 || next.c < 0 || R <= next.r || C <= next.c) return false;

        if (obs.has(next.toString())) {
            dir = new P(dir.c, -dir.r);
        } else {
            pos = next;
        }
    }
}

export const solve = (data: string): number => {
    const lines = data.split('\n');
    const R = lines.length;
    const C = lines[0].length;
    const obs = new Map<string, P>();
    const [pos, dir] = parseMap(lines, obs);
    let loops = 0;
    for (let i = 0; i < R; i++) {
        for (let j = 0; j < C; j++) {
            if (i == pos.r && j == pos.c) continue;
            const newObs = new P(i, j);
            if (obs.has(newObs.toString())) continue;
            obs.set(newObs.toString(), newObs);
            if (moveLoop(obs, R, C, pos, dir)) {
                // console.log(`new obstacle at ${newObs}`);
                loops++;
            }
            obs.delete(newObs.toString());
        }
    }
    return loops;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}