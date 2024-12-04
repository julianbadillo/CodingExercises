import * as process from 'node:process';


const solve = (data: object) => {
    const lines = data.toString().split('\n');
    const r = lines.map((line: string) => {
        const nums = line.split(' ').map(x => Number.parseInt(x));
        // console.log(`nums = ${nums}`);
        const inc = nums[0] < nums[1];
        // console.log(`inc = ${inc}`);
        for (let i = 0; i < nums.length - 1; i++) {
            if (inc && nums[i] < nums[i + 1] && nums[i] + 3 >= nums[i + 1]) continue;
            if (!inc && nums[i] > nums[i + 1] && nums[i] <= nums[i + 1] + 3) continue;
            return 0;
        }
        return 1;
    }).filter(x => x === 1).length;
    process.stdout.write(`r = ${r}`);
    process.stdout.write('\n');
}

if (require.main === module) {
    process.stdin.on('data', solve);
}


