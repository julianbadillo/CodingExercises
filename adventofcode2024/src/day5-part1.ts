import * as process from 'node:process';


export class Rule {
    pred: number;
    succ: Set<number>;

    constructor(pred: number, succ: number | number[]) {
        this.pred = pred;
        if (typeof succ === 'number') this.succ = new Set([succ]);
        else this.succ = new Set(succ);
    }
}

export const parseRules = (data: number[][]): Map<number, Rule> => {
    const res = new Map<number, Rule>();
    data.forEach(l => {
        if (res.get(l[0])) {
            res.get(l[0])?.succ.add(l[1]);
        } else {
            res.set(l[0], new Rule(l[0], l[1]));
        }
    });
    return res;
}

export const fitRules = (update: number[], rules: Map<number, Rule>): boolean => {
    for (let i = 0; i < update.length - 1; i++) {
        const rule = rules.get(update[i]);
        if (!rule) {
            // console.log(`No rule for ${update[i]}`)
            return false;
        }
        if (!rule.succ.has(update[i + 1])) {
            // console.log(`No rule for ${update[i]} -> ${update[i + 1]}`);
            return false;
        }
    }
    return true;
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
    return updates.filter(u => fitRules(u, rules))
        .map(u => u[(u.length-1) / 2])
        .reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data: object) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}