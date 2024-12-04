import * as process from 'node:process';

export const solve = (data: string): number => {
    const lines = data.split('\n');
    const l1 = new Array<number>(lines.length);
    const l2 = new Array<number>(lines.length);
    lines.forEach((line: string, i: number) => {
        const parts = line.split('   ');
        l1[i] = Number.parseInt(parts[0]);
        l2[i] = Number.parseInt(parts[1]);
    });
    return l1.map(x1 => x1 * l2.filter(x2 => x1 == x2).length).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}`);
        process.stdout.write('\n');
    });
}
