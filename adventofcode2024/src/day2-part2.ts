import * as process from 'node:process';


const isSafe = (nums: number[]): boolean => {
    const inc: boolean = nums[0] < nums[1];
    let good: boolean = true;
    for (let i = 0; i < nums.length - 1; i++) {
        if (inc && nums[i] < nums[i + 1] && nums[i] + 3 >= nums[i + 1]) continue;
        if (!inc && nums[i] > nums[i + 1] && nums[i] <= nums[i + 1] + 3) continue;
        good = false;
        break;
    }
    return good;
}

const solve = (data: object) => {
    const lines: string[] = data.toString().split('\n');
    const r: number = lines.map((line: string) => {
        const nums: number[] = line.split(' ').map(x => Number.parseInt(x));
        if (isSafe(nums)) {
            // console.log('Safe without removing any level');
            return 1;
        }
        for (let i = 0; i < nums.length; i++) {
            const numsF = nums.filter((v, j) => j !== i);
            if (isSafe(numsF)) {
                // console.log(`Safe removing ${i}`);
                return 1;
            }
        }
        // console.log('Unsafe');
        return 0;
    }).filter(x => x == 1).length;
    process.stdout.write(`r = ${r}`);
    process.stdout.write('\n');
}

if (require.main === module) {
    process.stdin.on('data', solve);
}