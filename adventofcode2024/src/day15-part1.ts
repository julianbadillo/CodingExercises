export type Robot = { r: number, c: number };
export type D = { dr: number, dc: number };
export const DIRS = new Map<string, D>([
    ['^', { dr: -1, dc: 0 }],
    ['>', { dr: 0, dc: 1 }],
    ['v', { dr: 1, dc: 0 }],
    ['<', { dr: 0, dc: -1 }],
]);

export const move = (map: string[][], robot: Robot, instruction: string) => {
    const d = DIRS.get(instruction);
    if (d === undefined) {console.log('error', instruction, d); return;}
    const newPos: Robot = { r: robot.r + d.dr, c: robot.c + d.dc };
    // easy case, wall - do nothing
    if (map[newPos.r][newPos.c] === '#') return;
    // easy case, empty slot - move there
    if (map[newPos.r][newPos.c] === '.') {
        robot.r += d.dr;
        robot.c += d.dc;
        return;
    }
    // box
    if (map[newPos.r][newPos.c] === 'O') {
        let i = 0;
        // find what's after the boxes
        for (; map[newPos.r + i * d.dr][newPos.c + i * d.dc] === 'O'; i++);
        // if it's a wall - do nothing
        if (map[newPos.r + i * d.dr][newPos.c + i * d.dc] === '#') return;
        // if it's space, shift boxes
        for (; i > 0; i--) map[newPos.r + i * d.dr][newPos.c + i * d.dc] = map[newPos.r + (i - 1) * d.dr][newPos.c + (i - 1) * d.dc];
        // leave emtpy space
        map[newPos.r][newPos.c] = '.'
        // update robot
        robot.r += d.dr;
        robot.c += d.dc;
    }
}

export const printMap = (map: string[][], robot: Robot) => {
    for(let i =0; i< map.length; i++) {
        for(let j=0; j<map[i].length; j++) {
            if (i == robot.r && j == robot.c) process.stdout.write('@');
            else process.stdout.write(map[i][j]);
        }
        process.stdout.write('\n');
    }
}

export const sumGPS = (map: string[][], x: string): number => {
    return map.map((row, r) => row.map((cell, c) => cell === x ? 100 * r + c : 0))
        .flat()
        .reduce((a, b) => a + b);
}

export const solve = (data: string) => {
    const parts = data.split('\n\n');
    const map: string[][] = parts[0].split('\n').map(l => l.split(''));
    const instructions = parts[1].split('\n').map(l => l.split('')).flat();

    const robot: Robot = { r: 0, c: 0 };
    map.forEach((l, r) => {
        if (l.indexOf('@') !== -1) {
            robot.r = r;
            robot.c = l.indexOf('@');
            map[robot.r][robot.c] = '.';
        }
    });
    instructions.forEach(ins => move(map, robot, ins));
    // printMap(map, robot);
    return sumGPS(map, 'O');
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}