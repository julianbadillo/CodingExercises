import * as process from 'node:process';

export type AggFunc = (a: number, b: number) => number;
export const add: AggFunc = (a, b) => a + b;
export const mul: AggFunc = (a, b) => a * b;

export const operations = (x: number, n: number): AggFunc[] => {
    const res: AggFunc[] = [];
    for (let i = 0; i < n; i++) {
        res.push((x & 1) === 1 ? add : mul);
        x = x >> 1;
    }
    return res;
}

export const canBeSolved = (eq: Array<number | number[]>): boolean => {
    const res = eq[0] as number;
    const nums = eq[1] as number[];
    const n = 1 << (nums.length - 1);
    // console.log(`n = ${n}`);
    for (let i = 0; i < n; i++) {
        const ops = operations(i, nums.length - 1);
        // console.log(`oos = ${ops}`);
        const res2 = nums.reduce((a, b, j) => ops[j - 1](a, b));
        // console.log(`res2 = ${res2}`);
        if (res === res2) return true;
    }
    return false;
}

export const solve = (data: string): number => {
    const equations = data.split('\n')
        .map(l => l.split(': '))
        .map(l => [
            Number.parseInt(l[0]),
            l[1].split(' ').map(x => Number.parseInt(x))
        ]);
    return equations.filter(eq => canBeSolved(eq))
        .map(eq => eq[0] as number)
        .reduce(add);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}