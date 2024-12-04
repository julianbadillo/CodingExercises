import * as process from 'node:process';

const mult_re = /(mul\((\d{1,3}),(\d{1,3})\))|(do\(\))|(don't\(\))/g;

const solve = (data: object) => {
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
    process.stdout.write(`s = ${s}\n`);
}

if (require.main === module) {
    process.stdin.on('data', solve);
}