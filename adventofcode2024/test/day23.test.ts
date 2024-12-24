
import assert from "assert";
import { parseGraph, solve } from "../src/day23-part1";
import { isComplete, solve as solve2 } from "../src/day23-part2";


describe('day23', () => {

    const s = `kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn`;

    it('parseGraph', () => {
        const graph = parseGraph(s);
        const ln = ['kh', 'tc', 'qp', 'de', 'cg', 'td'];
        for (const node of ln) {
            assert.equal(graph.has(node), true, node);
        }
        ['kh,tc', 'tc,kh', 'wh,qp', 'qp,wh', 'de,ta', 'ta,de']
            .map(ed => ed.split(','))
            .forEach(ed => {
                assert.equal(graph.get(ed[0])?.isAdjc(ed[1]), true, ed.join());
            });
    });

    it('solve', () => {
        assert.equal(solve(s), 7);
    });
    it('isComplete', () => {
        const graph = parseGraph(s);
        assert.equal(isComplete(graph, ['co', 'de', 'ta']), true);
        assert.equal(isComplete(graph, ['de', 'ka', 'ta']), true);
        assert.equal(isComplete(graph, ['co', 'de', 'ka', 'ta']), true);
    });
    it('solve-p2', () => {
        assert.equal(solve2(s), 'co,de,ka,ta');
    });
});