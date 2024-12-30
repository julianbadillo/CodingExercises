import * as process from 'node:process';


export class P {
    r: number;
    c: number;
    constructor(r: number, c: number) {
        this.r = r;
        this.c = c;
    }
    toString(): string {
        return `${this.r},${this.c}`;
    }
    key(): string {
        return this.toString();
    }
}

export const D = new Map<string, P>([
    ['^', new P(-1, 0)],
    ['>', new P(0, 1)],
    ['v', new P(1, 0)],
    ['<', new P(0, -1)],
])

export const parseMap = (map: string[], obs: Map<string, P>): [P, P] => {
    const R = map.length;
    const C = map[0].length;
    let start = new P(0, 0);
    let dir: P | undefined = new P(0, 0);
    for (let r = 0; r < R; r++) {
        for (let c = 0; c < C; c++) {
            if (map[r][c] === '#') {
                const p = new P(r, c);
                obs.set(p.toString(), p);
            } else if (map[r][c] !== '.') {
                start = new P(r, c);
                dir = D.get(map[r][c]);
            }
        }
    }
    return [start, dir ?? new P(0, 0)];
}

export const move = (obs: Map<string, P>, visited: Map<string, P>, R: number, C: number, pos: P, dir: P) => {
    while (true) {
        // console.log(`pos ${pos} dir ${dir}`);
        visited.set(pos.toString(), pos);
        // try to move
        const next = new P(pos.r + dir.r, pos.c + dir.c);
        // out of bounds
        if (next.r < 0 || next.c < 0 || R <= next.r || C <= next.c) break;

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
    const visited = new Map<string, P>();
    move(obs, visited, R, C, pos, dir);
    // for (let i = 0; i < R; i++) {
    //     for (let j = 0; j < C; j++) {
    //         if (visited.has(`${i},${j}`)) {
    //             process.stdout.write('X');
    //         } else if (obs.has(`${i},${j}`)) {
    //             process.stdout.write('#');
    //         } else {
    //             process.stdout.write('.');
    //         }
    //     }
    //     process.stdout.write('\n');
    // }
    return visited.size;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}