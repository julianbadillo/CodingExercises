import * as process from 'node:process';
import { add, mul, AggFunc } from './day7-part1';
export const conc: AggFunc = (a, b) => Number.parseInt(`${a}${b}`);

export const operations = (n: number[]): AggFunc[] => {
    return n.map(x => {
        if (x === 0) return add;
        else if (x === 1) return mul;
        else return conc;
    })
}

export const next = (n: number[]): boolean => {
    for (let i = 0; i < n.length; i++) {
        n[i]++;
        if (n[i] <= 2) return true;
        n.fill(0, 0, i + 1);
    }
    return false;
}

export const canBeSolved = (eq: Array<number | number[]>): boolean => {
    const res = eq[0] as number;
    const nums = eq[1] as number[];
    const n = (new Array(nums.length - 1)).fill(0);
    // console.log(`n = ${n}`);
    while (true) {
        const ops = operations(n);
        // console.log(`oos = ${ops}`);
        const res2 = nums.reduce((a, b, j) => ops[j - 1](a, b));
        // console.log(`res2 = ${res2}`);
        if (res === res2) return true;
        if (!next(n)) break;
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