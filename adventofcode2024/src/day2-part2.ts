import * as process from 'node:process';
import { isSafe } from './day2-part1';

export const isSafeRemoving = (nums: number[]): boolean => {
    if (isSafe(nums)) {
        // console.log('Safe without removing any level');
        return true;
    }
    for (let i = 0; i < nums.length; i++) {
        const numsF = nums.filter((v, j) => j !== i);
        if (isSafe(numsF)) {
            // console.log(`Safe removing ${i}`);
            return true;
        }
    }
    // console.log('Unsafe');
    return false;
} 

export const solve = (data: string): number => {
    const lines: string[] = data.split('\n');
    return lines.map((line: string) => {
        const nums: number[] = line.split(' ').map(x => Number.parseInt(x));
        return isSafeRemoving(nums);
    }).filter(x => x).length;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}