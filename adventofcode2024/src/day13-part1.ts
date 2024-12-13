
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
    getPrize(): Button | null {
        const data: Button[][] = new Array(101);
        for (let i = 0; i <= 100; i++) data[i] = new Array(101);
        for (let i = 0; i <= 100; i++) {
            for (let j = 0; j <= 100; j++) {
                data[i][j] = {
                    x: i * this.a.x + j * this.b.x,
                    y: i * this.a.y + j * this.b.y,
                    cost: i * this.a.cost + j * this.b.cost
                };
            }
        }
        let minButton: Button | null = null;
        for (let i = 0; i <= 100; i++) {
            for (let j = 0; j <= 100; j++) {
                if (data[i][j].x == this.prize.x && data[i][j].y == this.prize.y
                    && (minButton === null || data[i][j].cost < minButton.cost)) {
                    minButton = data[i][j]
                }
            }
        }
        return minButton;
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
            { x: Number.parseInt(p[1]), y: Number.parseInt(p[2]) },
        )
    });
    // console.log('regions=', regions);
    return claws.map(b => b.getPrize()).filter(b => b !== null).map(b => b.cost).reduce((a, b) => a + b);
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}