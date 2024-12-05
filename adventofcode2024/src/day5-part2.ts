import * as process from 'node:process';

import {Rule, fitRules, parseRules} from './day5-part1';

export const sortByRules = (update: number[], rules: Map<number, Rule>): number[] => {
    update.sort((a, b) => rules.get(a)?.succ.has(b) ? -1 : 1);
    return update;
}

export const solve = (data: string): number => {

    const parts = data.split('\n\n');

    const rules_data = parts[0].split('\n')
        .map(l => l.split('|')
            .map(x => Number.parseInt(x)));
    const rules = parseRules(rules_data);
    // console.log(rules);
    const updates = parts[1].split('\n')
        .map(l => l.split(',')
            .map(x => Number.parseInt(x)));
    return updates.filter(u => !fitRules(u, rules))
        .map(u => sortByRules(u, rules))
        .map(u => u[(u.length-1) / 2])
        .reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}