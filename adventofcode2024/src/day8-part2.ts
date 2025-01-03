import * as process from 'node:process';
import { parseMap, Node } from './day8-part1';

export const allAntinodes = (n1: Node, n2: Node, R: number, C: number): Node[] => {
    const dr = n1.r - n2.r;
    const dc = n1.c - n2.c;
    const res = [];
    let i = 0;
    while (true) {
        const n = new Node(n1.r + i * dr, n1.c + i * dc, '#');
        if (!n.inBounds(R, C)) break;
        res.push(n);
        i++;
    }
    i = 1;
    while (true) {
        const n = new Node(n1.r - i * dr, n1.c - i * dc, '#');
        if (!n.inBounds(R, C)) break;
        res.push(n);
        i++;
    }
    return res;
}


export const solve = (data: string): number => {
    const nodeMap = parseMap(data);
    const s = data.split('\n');
    const R = s.length;
    const C = s[0].length;
    const antinodes = new Map<string, Node>();
    for (const [, nodes] of nodeMap) {
        // all nodes of same frequency
        nodes.forEach((n1, i) => {
            for (let j = i + 1; j < nodes.length; j++) {
                allAntinodes(n1, nodes[j], R, C)
                    .forEach(n => {
                        antinodes.set(n.key(), n);
                    });
            }
        })
    }
    return antinodes.size;
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}