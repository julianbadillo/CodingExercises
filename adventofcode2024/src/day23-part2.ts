import { parseGraph, Node } from "./day23-part1";

export const analyze = (graph: Map<string, Node>) => {
    const sizeCount = new Map<number, number>();
    sizeCount.set(0, 0);
    [...graph.values()].forEach(n => {
        if (sizeCount.has(n.edges.length)) sizeCount.set(n.edges.length, sizeCount.get(n.edges.length) as number + 1);
        else sizeCount.set(n.edges.length, 1);
    });
    [...sizeCount.entries()].forEach(en => {
        console.log(en);
    });
}

export const isComplete = (graph: Map<string, Node>, nodes: string[]): boolean => {
    for (let i = 0; i < nodes.length - 1; i++) {
        for (let j = i + 1; j < nodes.length; j++)
            if (!graph.get(nodes[i])?.isAdjc(nodes[j])) return false;
    }
    return true;
}

export const solve = (data: string): string => {
    const graph = parseGraph(data);
    // analyze(graph, adj);
    // all 520 nodes have 13 edges
    // so the maximal complete graph cannot have more than 14 nodes
    // 520 nodes in total
    // comb(520, 13) ~ 10^25 - intractable
    // lucky guess - complete 13 node graph
    for (const n1 of [...graph.values()]) {
        const subNodes = n1.edges;
        // try dropping one by one
        for (let i = 0; i <= subNodes.length; i++) {
            const nodes2 = [...subNodes];
            nodes2.push(n1.name);
            nodes2.splice(i, 1);
            if (isComplete(graph, nodes2)) {
                // console.log('Bingo!!!!', nodes2.sort().join());
                return nodes2.sort().join();
            }
        }
    }
    return '';
}



if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}