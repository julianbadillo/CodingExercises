export type P = {
    r: number,
    c: number,
}
export const D = new Map<string, P>([
    ['^', { r: -1, c: 0 }],
    ['>', { r: 0, c: 1 }],
    ['v', { r: 1, c: 0 }],
    ['<', { r: 0, c: -1 }],
])

export const pKey = (p: P): string => `${p.r},${p.c}`;

export const doorPad = [
    `789`,
    `456`,
    `123`,
    ` 0A`,
];



export const robotPad = [
    ` ^A`,
    `<v>`,
];

export const doorPadPos = new Map<string, P>();

doorPad.forEach((line, r) => line
    .split('')
    .forEach((key, c) => {
        doorPadPos.set(key, { r: r, c: c });
    })
);

export const robotPadPos = new Map<string, P>();
robotPad.forEach((line, r) => line
    .split('')
    .forEach((key, c) => {
        robotPadPos.set(key, { r: r, c: c });
    })
);
export class RobotChain {

    p1: P = { r: 3, c: 2 }; // pointing to A in the doorKeyPad
    p2: P = { r: 0, c: 2 }; // pointing to A in robotKeyPad
    p3: P = { r: 0, c: 2 }; // pointing to A in robotKeyPad
    out: string = '';

    clone(): RobotChain {
        const r = new RobotChain();
        r.p1 = { ...this.p1 };
        r.p2 = { ...this.p2 };
        r.p3 = { ...this.p3 };
        r.out = this.out;
        return r;
    }

    key(): string {
        return `${pKey(this.p1)}:${pKey(this.p2)}:${pKey(this.p3)}:${this.out}`;
    }

    isValid(): boolean {
        // out of bounds
        if (this.p1.r < 0 || this.p1.r >= doorPad.length ||
            this.p1.c < 0 || this.p1.c >= doorPad[0].length ||
            this.p2.r < 0 || this.p2.r >= robotPad.length ||
            this.p2.c < 0 || this.p2.c >= robotPad[0].length ||
            this.p3.r < 0 || this.p3.r >= robotPad.length ||
            this.p3.c < 0 || this.p3.c >= robotPad[0].length) return false;
        // forbidden keys
        if (doorPad[this.p1.r][this.p1.c] === ' ' ||
            robotPad[this.p2.r][this.p2.c] === ' ' ||
            robotPad[this.p3.r][this.p3.c] === ' ') return false;
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

    press2(key: string) {
        if (key == 'A') {
            this.press1(robotPad[this.p2.r][this.p2.c]);
            return;
        }
        const d = D.get(key) as P;
        this.p2.r += d.r;
        this.p2.c += d.c;
    }

    press3(seq: string) {
        for (const key of seq.split('')) {
            if (key == 'A') {
                this.press2(robotPad[this.p3.r][this.p3.c]);
                continue;
            }
            const d = D.get(key) as P;
            this.p3.r += d.r;
            this.p3.c += d.c;
        }
    }
}

export const shortestSeq = (code: string): string => {
    let seq = '';
    // start with a default robot chain state
    const start = new RobotChain();
    const queue: RobotChain[] = [];
    // link to previous state
    const pred = new Map<string, RobotChain>();
    // keys pressed
    const predKey = new Map<string, string>();
    queue.push(start);
    let state = start;
    while (queue.length > 0) {
        state = queue.shift() as RobotChain;
        // end state reached
        if (state.out === code) break;
        // try all buttons
        for (const key2 of ['<', '>', '^', 'v', 'A']) {
            const state2 = state.clone();
            state2.press3(key2);
            // discard invalid
            if (!state2.isValid()) continue;
            // if going to the wrong direction
            if (state2.out.length != 0 && !code.startsWith(state2.out)) {
                // console.log('Wrong direction!', key, ' != ', state2.out);
                continue;
            }
            // already in the queue
            if (pred.has(state2.key())) continue;
            pred.set(state2.key(), state);
            predKey.set(state2.key(), key2);
            queue.push(state2);
        }
    }
    // backtrack the sequence
    while (state.key() != start.key()) {
        const prevState = pred.get(state.key()) as RobotChain;
        seq = predKey.get(state.key()) + seq;
        state = prevState;
    }
    return seq;
}

export const solve = (data: string) => {
    return data.split('\n').map(code => {
        return shortestSeq(code).length * Number.parseInt(code.substring(0, 4))
    }).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}