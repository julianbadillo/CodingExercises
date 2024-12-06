import * as process from 'node:process';

export const distance = (a: Array<number>, b: Array<number>, i: number) => {
    return a[i] < b[i] ? (b[i] - a[i]) : (a[i] - b[i]);
};

export const solve = (data: string): number => {
    const lines = data.split('\n');
    const l1 = new Array(lines.length);
    const l2 = new Array(lines.length);
    lines.forEach((line: string, i: number) => {
        const parts = line.split('   ');
        l1[i] = Number.parseInt(parts[0]);
        l2[i] = Number.parseInt(parts[1]);
    });
    l1.sort();
    l2.sort();
    let s = 0;
    for (let i = 0; i < l1.length; i++) {
        s += distance(l1, l2, i);
    }
    return s;
};

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}
