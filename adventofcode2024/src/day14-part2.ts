import { Robot } from "./day14-part1";

const printRobots = (robots: Robot[], MX: number, MY: number) => {
    const pic: string[][] = new Array(MY);
    for (let i = 0; i < MY; i++) pic[i] = new Array<string>(MX).fill(' ');
    robots.forEach(r => pic[r.y][r.x] = '.');
    pic.forEach(line => {
        console.log(line.join(''));
    });
}

export const solve = (data: string, MX: number, MY: number, t: number) => {
    const robots = data.split('\n').map(line => Robot.fromLine(line));
    robots.forEach(r => r.move(t, MX, MY));
    console.log(t);
    printRobots(robots, MX, MY);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const args = process.argv.slice(2).map(x => Number.parseInt(x));
        const r = solve(data.toString(), args[0], args[1], args[2]);
        process.stdout.write(`r = ${r}\n`);
    });
}
