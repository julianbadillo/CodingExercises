export class Node {
    name: string;
    edges: string[] = [];
    constructor(n: string) {
        this.name = n;
    }
    isAdjc(n2: string): boolean {
        return this.edges.indexOf(n2) != -1;
    }
}

export const parseGraph = (data: string): Map<string, Node> => {
    // make an adjacent list
    const graph = new Map<string, Node>();
    data.split('\n').forEach(line => {
        const p = line.split('-').map(n => graph.get(n) || new Node(n));
        p[0].edges.push(p[1].name);
        p[1].edges.push(p[0].name);
        graph.set(p[0].name, p[0]).set(p[1].name, p[1]);
    });
    return graph;
}

export const solve = (data: string): number => {
    const graph = parseGraph(data);

    const groups = new Set<string>();
    console.log(`nodes size = ${graph.size}`);
    // filter nodes with t
    [...graph.values()].filter(n1 => n1.name[0] === 't')
        .forEach(n1 => {
            // 1-edge neighbours
            n1.edges.forEach(n2 => {
                // 2-edge neighbours
                graph.get(n2)?.edges.forEach(n3 => {
                    // if node1 is neighbour of node3
                    if (n1.isAdjc(n3)) groups.add([n1.name, n2, n3].sort().join());
                });
            })
        });
    // [...groups].sort().forEach(g => console.log(g));
    // test result
    return groups.size;
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}