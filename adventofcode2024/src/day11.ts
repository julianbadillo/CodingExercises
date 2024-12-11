export class Rock {
    n: number;
    count: number;
    constructor(n: number, count: number | null = null) {
        this.n = n;
        this.count = count ?? 1;
    }
    blink(): Rock[] {
        if (this.n === 0) {
            this.n++;
            return [this];
        }
        const s = this.n.toString();
        if (s.length % 2 == 0) {
            return [s.substring(0, s.length / 2), s.substring(s.length / 2, s.length)]
                .map(x => new Rock(Number.parseInt(x), this.count));
        }
        this.n *= 2024;
        return [this];
    }
}

export const compressArr = (rocks_arr: Rock[]): Map<number, Rock> => {
    const rocks = new Map<number, Rock>();
    for (const r of rocks_arr) {
        if (rocks.has(r.n)) {
            (rocks.get(r.n) as Rock).count += r.count;
        } else {
            rocks.set(r.n, r);
        }
    }
    return rocks;
}

export const solve = (data: string, blinks: number): number => {
    let rocks_arr = data.trim()
        .split(' ')
        .map(x => new Rock(Number.parseInt(x)));
    let rocks = compressArr(rocks_arr);
    for (let b = 0; b < blinks; b++) {
        // console.log(`ite ${b}, size=${rocks.size}`);
        rocks_arr = [...rocks.values()].map(r => r.blink()).flat();
        rocks = compressArr(rocks_arr);
    }
    // console.log('rocks = ', rocks);
    return [...rocks.values()].map(r => r.count).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        // console.log(`args =`, process.argv);
        const blinks = process.argv.length > 2 ? Number.parseInt(process.argv[2]) : 25;
        // console.log(`blinks =`, blinks);
        const r = solve(data.toString(), blinks);
        process.stdout.write(`r = ${r}\n`);
    });
}