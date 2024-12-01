import * as process from 'node:process';

const distance = (a: Array<number>, b: Array<number>, i: number) => {
    return a[i] < b[i] ? (b[i] - a[i]): (a[i] - b[i]);
};

process.stdin.on('data', (data: object) => {
    const lines = data.toString().split('\n');
    const l1 = new Array(lines.length);
    const l2 = new Array(lines.length);
    lines.forEach((line: string, i: number) => {
        const parts = line.split('   ');
        l1[i] = Number.parseInt(parts[0]);
        l2[i] = Number.parseInt(parts[1]);
    });
    l1.sort();
    l2.sort();
    var s = 0;
    for (var i = 0; i < l1.length; i++) {
        s += distance(l1, l2, i);
    }
    process.stdout.write(`s = ${s}`);
    process.stdout.write('\n');
})
