import * as process from 'node:process';


export class Node {
    r: number;
    c: number;
    f: string;
    constructor(r: number, c: number, f: string) {
        this.r = r;
        this.c = c;
        this.f = f;
    }
    key(): string {
        return `${this.r},${this.c}:${this.f}`;
    }
    // for the firs tpart
    antinodes(n2: Node): Node[] {
        const dr = this.r - n2.r;
        const dc = this.c - n2.c;
        return [
            new Node(this.r + dr, this.c + dc, '#'),
            new Node(n2.r - dr, n2.c - dc, '#'),
        ]
    }
    inBounds = (R: number, C: number): boolean => 0 <= this.r && this.r < R && 0 <= this.c && this.c < C;
}

export const parseMap = (data: string): Map<string, Node[]> => {
    const result = new Map<string, Node[]>();
    data.split('\n').forEach((row, r) => {
        row.split('').forEach((f, c) => {
            if (f !== '.') {
                const nodes = result.get(f) ?? [];
                nodes.push(new Node(r, c, f))
                result.set(f, nodes);
            }
        });
    });
    return result;
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
                n1.antinodes(nodes[j])
                    .filter(n => n.inBounds(R, C))
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