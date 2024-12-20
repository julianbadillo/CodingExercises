import { solve as solvePrev } from "./day18-part1";

export const solve = (data: string, R: number, C: number): string => {

    const coords = data.split('\n');
    const N = coords.length;
    for (let i = 1; i <= N; i++) {
        if (solvePrev(data, R, C, i) == -1) return coords[i - 1];
    }
    return "NOT FOUND";
}


if (require.main === module) {
    process.stdin.on('data', (data) => {
        const args = process.argv.slice(2).map(x => Number.parseInt(x));
        const r = solve(data.toString(), args[0], args[1]);
        process.stdout.write(`r = ${r}\n`);
    });
}