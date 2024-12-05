import assert from 'assert';
import { solve as solve1, Rule, parseRules, fitRules } from '../src/day5-part1';
import { solve as solve2, sortByRules } from '../src/day5-part2';

describe('day5', () => {
    it('parseRules', () => {
        const data = [[1, 2], [2, 3], [1, 3]];
        const rules = parseRules(data);
        assert.ok(rules.has(1));
        assert.ok(rules.has(2));
        assert.ok(rules.get(1)?.succ.has(2));
        assert.ok(rules.get(1)?.succ.has(3));
        assert.ok(rules.get(2)?.succ.has(3));
    });
    it('fitRules-1', () => {
        const rules = new Map([
            [1, new Rule(1, [2, 3, 4])],
            [2, new Rule(2, [3, 4])],
            [3, new Rule(3, [4])],
        ]);
        assert.equal(true, fitRules([1, 2, 3, 4], rules));
        assert.equal(true, fitRules([2, 3, 4], rules));
        assert.equal(true, fitRules([1, 4], rules));
        assert.equal(false, fitRules([2, 1], rules));
        assert.equal(false, fitRules([3, 2], rules));
        assert.equal(false, fitRules([1, 2, 4, 3], rules));
    });
    it('fitRules-2', () => {
        const rules = new Map([
            [47, new Rule(47, [53, 13, 61, 29])],
            [97, new Rule(97, [13, 61, 47, 29, 53, 75])],
            [75, new Rule(75, [29, 53, 47, 61, 13])],
            [61, new Rule(61, [13, 53, 29])],
            [29, new Rule(29, [13])],
            [53, new Rule(53, [29, 13])],
        ]);
        assert.equal(true, fitRules([75, 47, 61, 53, 29], rules));
        assert.equal(true, fitRules([97, 61, 53, 29, 13], rules));
        assert.equal(true, fitRules([75, 29, 13], rules));
        assert.equal(false, fitRules([75, 97, 47, 61, 53], rules));
        assert.equal(false, fitRules([61, 13, 29], rules));
        assert.equal(false, fitRules([97, 13, 75, 29, 47], rules));
    });
    it('sortByRules', () => {
        const rules = new Map([
            [47, new Rule(47, [53, 13, 61, 29])],
            [97, new Rule(97, [13, 61, 47, 29, 53, 75])],
            [75, new Rule(75, [29, 53, 47, 61, 13])],
            [61, new Rule(61, [13, 53, 29])],
            [29, new Rule(29, [13])],
            [53, new Rule(53, [29, 13])],
        ]);
        assert.deepEqual(sortByRules([75,97,47,61,53], rules), [97,75,47,61,53]);
        assert.deepEqual(sortByRules([61,13,29], rules), [61,29,13]);
        assert.deepEqual(sortByRules([97,13,75,29,47], rules), [97,75,47,29,13]);
    });

    const data = `47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47`;
    it('solve-part1', () => {
        assert.equal(solve1(data), 143);
    });
    it('solve-part2', () => {
        assert.equal(solve2(data), 123);
    });
});