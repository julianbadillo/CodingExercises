import { Robot, printMap, D, DIRS, sumGPS } from "./day15-part1";

export const findBoxes = (map: string[][], robot: Robot, d: D): Robot[] | undefined => {
    const boxes: Robot[] = [];
    const pos: Robot = { r: robot.r + d.dr, c: robot.c + d.dc };

    // easy case - horizontal - same as part 1
    if (d.dr === 0) {
        let i = 0;
        for (; map[pos.r][pos.c + i * d.dc] === '[' || map[pos.r][pos.c + i * d.dc] === ']'; i++)
            boxes.push({ r: pos.r, c: pos.c + i * d.dc });
        // if hit a wall
        if (map[pos.r][pos.c + i * d.dc] === '#') return undefined;
        return boxes;
    }
    // vertical
    const queue: Robot[] = [];
    const visited = new Set<string>();
    // add pos and adjacent pos also (depending if it's the right or left side of the box)
    queue.push(pos); 
    queue.push({ r: pos.r, c: pos.c + (map[pos.r][pos.c] === '[' ? 1 : -1) })
    while (queue.length > 0) {
        const pos2 = queue.shift() as Robot;
        // hit a wall
        if (map[pos2.r][pos2.c] === '#') return undefined;
        // empty space
        if (map[pos2.r][pos2.c] === '.') continue;
        // a box
        if (visited.has(`${pos2.r},${pos2.c}`)) continue;
        boxes.push(pos2);
        visited.add(`${pos2.r},${pos2.c}`);
        // next one and adjacent
        const pos3 = { r: pos2.r + d.dr, c: pos2.c };
        queue.push(pos3);
        if (map[pos3.r][pos3.c] === '[') queue.push({ r: pos2.r + d.dr, c: pos2.c + 1 });
        if (map[pos3.r][pos3.c] === ']') queue.push({ r: pos2.r + d.dr, c: pos2.c - 1 });    
    }
    return boxes;

}

export const moveBetter = (map: string[][], robot: Robot, instruction: string) => {
    const d = DIRS.get(instruction);
    if (d === undefined) { console.log('error', instruction, d); return; }
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
    if (map[newPos.r][newPos.c] === '[' || map[newPos.r][newPos.c] === ']') {
        // find what's after the boxes
        const boxes = findBoxes(map, robot, d);
        // if null - we hit a wall - do nothing
        if (!boxes) return;
        // move boxes - from farther to robot to closer
        boxes.reverse().map(box => {
            map[box.r + d.dr][box.c + d.dc] = map[box.r][box.c];
            map[box.r][box.c] = '.'; // leave empty space
        });
        // update robot
        robot.r += d.dr;
        robot.c += d.dc;
    }
}

export const solve = (data: string) => {
    const parts = data.split('\n\n');
    const map: string[][] = parts[0].split('\n').map(
        l => l.split('').map(cell => {
            if (cell === '#') return ['#', '#'];
            if (cell === '.') return ['.', '.'];
            if (cell === '@') return ['@', '.'];
            return ['[', ']'];
        }).flat()
    );
    const instructions = parts[1].split('\n').map(l => l.split('')).flat();
    const robot: Robot = { r: 0, c: 0 };
    map.forEach((l, r) => {
        if (l.indexOf('@') !== -1) {
            robot.r = r;
            robot.c = l.indexOf('@');
            map[robot.r][robot.c] = '.';
        }
    });
    instructions.forEach(ins => moveBetter(map, robot, ins));
    // print
    // printMap(map, robot);
    return sumGPS(map, '[');
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}