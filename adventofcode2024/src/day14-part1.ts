

export class Robot {
    x: number;
    y: number;
    vx: number;
    vy: number;
    constructor(x: number, y: number, vx: number, vy: number) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }
    move(t: number, MX: number, MY: number) {
        this.x = ((this.x + this.vx * t) % MX + MX) % MX;
        this.y = ((this.y + this.vy * t) % MY + MY) % MY;
    }
    quad(MX: number, MY: number): number | undefined {
        if (this.x < (MX - 1) / 2 && this.y < (MY - 1) / 2) return 0;
        if (this.x > (MX - 1) / 2 && this.y < (MY - 1) / 2) return 1;
        if (this.x < (MX - 1) / 2 && this.y > (MY - 1) / 2) return 2;
        if (this.x > (MX - 1) / 2 && this.y > (MY - 1) / 2) return 3;
        return undefined;
    }
    static fromLine(line: string): Robot {
        const parts = line.split(' ')
            .map(p => p.split('=')[1])
            .map(p => p.split(',').map(x => Number.parseInt(x)))
            .flat();
        return new Robot(parts[0], parts[1], parts[2], parts[3]);
    }
}

export const solve = (data: string, MX: number, MY: number): number => {
    const counts = [0, 0, 0, 0];
    const robots = data.split('\n').map(line => Robot.fromLine(line));
    robots.forEach(r => {
        r.move(100, MX, MY);
        const q = r.quad(MX, MY);
        if (q !== undefined) counts[q]++
    });
    return counts.reduce((a, b) => a * b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const args = process.argv.slice(2).map(x => Number.parseInt(x));
        const r = solve(data.toString(), args[0], args[1]);
        process.stdout.write(`r = ${r}\n`);
    });
}