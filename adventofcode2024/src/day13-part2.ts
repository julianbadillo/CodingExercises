
export type Button = {
    x: number;
    y: number;
    cost: number;
}

export class Claw {
    a: Button;
    b: Button;
    prize: { x: number, y: number };
    constructor(a: Button, b: Button, prize: { x: number, y: number }) {
        this.a = a;
        this.b = b;
        this.prize = prize;
    }
    getPrize(): number | null {
        // find intersection
        const i1 = this.b.y * this.prize.x - this.b.x * this.prize.y;
        const i2 = this.a.x * this.b.y - this.a.y * this.b.x;
        if (i1 % i2 !== 0) return null;

        const j1 = this.a.y * this.prize.x - this.a.x * this.prize.y;
        const j2 = this.a.y * this.b.x - this.a.x * this.b.y;
        if (j1 % j2 !== 0) return null;
        return (i1 / i2) * this.a.cost + (j1 / j2) * this.b.cost;

    }
}

export const re1 = /Button [AB]: X\+(\d+), Y\+(\d+)/;
export const re2 = /Prize: X=(\d+), Y=(\d+)/;

export const solve = (data: string): number => {
    const groups = data.split('\n\n');
    const claws = groups.map(l => {
        const lines = l.split('\n');
        const ba = lines[0].match(re1) as RegExpMatchArray;
        const bb = lines[1].match(re1) as RegExpMatchArray;
        const p = lines[2].match(re2) as RegExpMatchArray;
        return new Claw(
            { x: Number.parseInt(ba[1]), y: Number.parseInt(ba[2]), cost: 3 },
            { x: Number.parseInt(bb[1]), y: Number.parseInt(bb[2]), cost: 1 },
            { x: Number.parseInt(p[1]) + 10000000000000, y: Number.parseInt(p[2]) + 10000000000000 },
        )
    });
    // console.log('regions=', regions);
    return claws.map(b => b.getPrize()).filter(b => b !== null).reduce((a, b) => a + b);
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}