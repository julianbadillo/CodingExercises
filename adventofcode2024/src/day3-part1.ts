import * as process from 'node:process';

const mult_re = /mul\((\d{1,3}),(\d{1,3})\)/g;

const solve = (data: object) => {
    let s = 0;
    data.toString()
        .split('\n')
        .filter(line => line.length > 0)
        .forEach(line => {
            s += [...line.matchAll(mult_re)]
                .map(m => Number.parseInt(m[1]) * Number.parseInt(m[2]))
                .reduce((a, b) => a + b);
        });
    process.stdout.write(`s = ${s}\n`);
}

if (require.main === module) {
    process.stdin.on('data', solve);
}