import { doorPad, doorPadPos, robotPad, robotPadPos, P, pKey, D } from "./day21-part1";


export class RobotLongerChain {
    N: number;
    p1: P = { r: 3, c: 2 }; // pointing to A in the doorKeyPad
    ps: P[];// pointing to A in robotKeyPad

    out: string = '';

    clone(): RobotLongerChain {
        const r = new RobotLongerChain(this.N);
        r.p1 = { ...this.p1 };
        r.ps = Array.from(this.ps, (p) => { return { ...p } });
        r.out = this.out;
        return r;
    }

    constructor(N: number) {
        // p2: P = { r: 0, c: 2 }; // pointing to A in robotKeyPad
        this.N = N;
        this.ps = Array.from({ length: N }, () => { return { r: 0, c: 2 } });
    }

    key(): string {
        return `${pKey(this.p1)}:${this.ps.map(p => pKey(p)).join(':')}:${this.out}`;
    }

    isValid(): boolean {
        // out of bounds
        if (this.p1.r < 0 || this.p1.r >= doorPad.length ||
            this.p1.c < 0 || this.p1.c >= doorPad[0].length)
            return false;
        // forbidden key
        if (doorPad[this.p1.r][this.p1.c] === ' ') return false;
        for (const p of this.ps) {
            if (p.r < 0 || p.r >= robotPad.length ||
                p.c < 0 || p.c >= robotPad[0].length)
                return false;
            if (robotPad[p.r][p.c] === ' ')
                return false;
        }
        return true;
    }

    press1(key: string) {
        if (key == 'A') {
            this.out += doorPad[this.p1.r][this.p1.c];
            return;
        }
        const d = D.get(key) as P;
        this.p1.r += d.r;
        this.p1.c += d.c;
    }

    press(seq: string, level: number = this.N - 1) {
        for (const key of seq.split('')) {
            // console.log('key=', key, 'level=', level);
            if (key == 'A' && level == 0) {
                this.press1(robotPad[this.ps[level].r][this.ps[level].c]);
                continue;
            }
            if (key == 'A') {
                this.press(robotPad[this.ps[level].r][this.ps[level].c], level - 1);
                continue;
            }
            const d = D.get(key) as P;
            this.ps[level].r += d.r;
            this.ps[level].c += d.c;
        }
    }
}

export const shortestSeqLengh = (code: string, n: number): number => {
    // start with a default robot chain state
    const start = new RobotLongerChain(n);
    const queue: RobotLongerChain[] = [];
    // link to previous state
    // const pred = new Map<string, RobotLongerChain>();
    const dist = new Map<string, number>();
    dist.set(start.key(), 0);
    // keys pressed
    // const predKey = new Map<string, string>();
    queue.push(start);
    let state = start;
    let stateDis = 0;
    while (queue.length > 0) {
        state = queue.shift() as RobotLongerChain;
        stateDis = dist.get(state.key()) as number;
        // end state reached
        if (state.out === code) break;
        // try all buttons
        for (const key2 of ['<', '>', '^', 'v', 'A']) {
            const state2 = state.clone();
            state2.press(key2);
            // discard invalid
            if (!state2.isValid()) continue;
            // if going to the wrong direction
            if (state2.out.length != 0 && !code.startsWith(state2.out)) {
                // console.log('Wrong direction!', key, ' != ', state2.out);
                continue;
            }
            // already in the queue
            if (dist.has(state2.key())) continue;
            dist.set(state2.key(), stateDis + 1);
            // pred.set(state2.key(), state);
            // predKey.set(state2.key(), key2);
            queue.push(state2);
        }
    }
    console.log('stateDis', stateDis);
    return stateDis;
}

export const solve = (data: string, n: number) => {
    return data.split('\n').map(code => {
        return shortestSeqLengh(code, n) * Number.parseInt(code.substring(0, 4))
    }).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString(), 25);
        process.stdout.write(`r = ${r}\n`);
    });
}