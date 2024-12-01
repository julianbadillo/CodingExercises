import * as process from 'node:process';

const distance = (a: Array<number>, b: Array<number>, i: number) => {
    return a[i] < b[i] ? (b[i] - a[i]): (a[i] - b[i]);
};

process.stdin.on('data', (data: object) => {
    const lines = data.toString().split('\n');
    const l1 = new Array<number>(lines.length);
    const l2 = new Array<number>(lines.length);
    lines.forEach((line: string, i: number) => {
        const parts = line.split('   ');
        l1[i] = Number.parseInt(parts[0]);
        l2[i] = Number.parseInt(parts[1]);
    });
    var s = l1.map(x1 => x1 * l2.filter(x2 => x1 == x2).length).reduce((a,b) => a+b);
    process.stdout.write(`s = ${s}`);
    process.stdout.write('\n');
})
