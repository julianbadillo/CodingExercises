import * as process from 'node:process';

export const mult_re = /(mul\((\d{1,3}),(\d{1,3})\))|(do\(\))|(don't\(\))/g;

export const solve = (data: string): number => {
    let s = 0;
    let doing = true;
    data.toString()
        .split('\n')
        .filter(line => line.length > 0)
        .forEach(line => {
            [...line.matchAll(mult_re)].forEach(m => {
                if (m[0] === 'do()') {
                    doing = true;
                } else if (m[0] === "don't()") {
                    doing = false;
                } else if (doing) {
                    s += Number.parseInt(m[2]) * Number.parseInt(m[3]);
                }
            });
        });
    return s;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}