
export type Button = {
    x: number;
    y: number;
    cost: number;
}

export const re1 = /Button [AB]: X\+(\d+), Y\+(\d+)/;
export const re2 = /Prize: X=(\d+), Y=(\d+)/;

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
        for (let i = 0; i <= 100; i++) {
            for (let j = 0; j <= 100; j++) {
                if (this.prize.x === i * this.a.x + j * this.b.x && this.prize.y === i * this.a.y + j * this.b.y)
                    return i * this.a.cost + j * this.b.cost;
            }
        }
        return null;
    }

    static fromLines(data: string): Claw {
        const lines = data.split('\n');
        const ba = lines[0].match(re1) as RegExpMatchArray;
        const bb = lines[1].match(re1) as RegExpMatchArray;
        const p = lines[2].match(re2) as RegExpMatchArray;
        return new Claw(
            { x: Number.parseInt(ba[1]), y: Number.parseInt(ba[2]), cost: 3 },
            { x: Number.parseInt(bb[1]), y: Number.parseInt(bb[2]), cost: 1 },
            { x: Number.parseInt(p[1]), y: Number.parseInt(p[2]) },
        )
    }
}

export const solve = (data: string): number => {
    // console.log('regions=', regions);
    return data.split('\n\n')
        .map(data => Claw.fromLines(data))
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