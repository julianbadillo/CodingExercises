import * as process from 'node:process';

export const mult_re = /mul\((\d{1,3}),(\d{1,3})\)/g;

export const solve = (data: string): number => {
    return data.split('\n')
        .filter(line => line.length > 0)
        .map(line => {
            return [...line.matchAll(mult_re)]
                .map(m => Number.parseInt(m[1]) * Number.parseInt(m[2]))
                .reduce((a, b) => a + b);
        }).reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}