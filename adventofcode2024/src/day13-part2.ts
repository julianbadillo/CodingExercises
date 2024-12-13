import { Button, Claw } from './day13-part1';

export class SmarterClaw extends Claw {
    constructor(a: Button, b: Button, prize: { x: number, y: number }) {
        super(a, b, prize);
    }
    getPrize(): number | null {
        // find intersection
        const i1 = this.b.y * this.prize.x - this.b.x * this.prize.y;
        const i2 = this.a.x * this.b.y - this.a.y * this.b.x;
        // not divisible - no integer solution
        if (i1 % i2 !== 0) return null;
        const j1 = this.a.y * this.prize.x - this.a.x * this.prize.y;
        const j2 = this.a.y * this.b.x - this.a.x * this.b.y;
        // not divisible - no integer solution
        if (j1 % j2 !== 0) return null;
        return (i1 / i2) * this.a.cost + (j1 / j2) * this.b.cost;
    }
    static fromLines(data: string): Claw {
        const c = super.fromLines(data);
        return new SmarterClaw(c.a, c.b, { x: c.prize.x + 10000000000000, y: c.prize.y + 10000000000000 })
    }
}

export const solve = (data: string): number => {
    return data.split('\n\n')
        .map(data => SmarterClaw.fromLines(data))
        .map(b => b.getPrize())
        .filter(b => b !== null)
        .reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}