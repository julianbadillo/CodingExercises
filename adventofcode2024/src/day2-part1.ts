import * as process from 'node:process';


export const isSafe = (nums: number[]): boolean => {
    // console.log(`nums = ${nums}`);
    const inc = nums[0] < nums[1];
    // console.log(`inc = ${inc}`);
    for (let i = 0; i < nums.length - 1; i++) {
        if (inc && nums[i] < nums[i + 1] && nums[i] + 3 >= nums[i + 1]) continue;
        if (!inc && nums[i] > nums[i + 1] && nums[i] <= nums[i + 1] + 3) continue;
        return false;
    }
    return true;
}

export const solve = (data: string) => {
    const lines = data.split('\n');
    return lines.map((line: string) => {
        const nums = line.split(' ').map(x => Number.parseInt(x));
        return isSafe(nums);
    }).filter(x => x).length;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}


