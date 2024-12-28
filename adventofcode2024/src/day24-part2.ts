import { Exp } from "./day24-part1";


export const toBits = (keys: string[], vars: Map<string, boolean>): bigint => {
    let n: bigint = 0n;
    keys.forEach(v => {
        n <<= 1n;
        if (vars.get(v) as boolean) n++;
    });
    return n;
}

export const printExp = (zv: string, vars: Map<string, boolean>, ops: Map<string, Exp>): string => {
    
    if (vars.has(zv)){
        return zv;
    } 
    const exp = ops.get(zv) as Exp;
    return `(${printExp(exp.A, vars, ops)} ${exp.OP} ${printExp(exp.B, vars, ops)})`;
}

export const findSwaps = (vars: Map<string, boolean>, ops: Map<string, Exp>): string => {
    // only the ones with z
    const zvs = [...ops.keys()].filter(v => v[0] === 'z').sort().reverse();
    zvs.forEach(zv => {
        console.log(`${zv} = ${printExp(zv, vars, ops)}`);
    });
    return '';
}

export const solve = (data: string): string => {
    const parts = data.split('\n\n');
    const vars = new Map<string, boolean>(
        parts[0].split('\n')
            .map(l => l.split(': '))
            .map(p => [p[0], p[1] === '1'])
    );
    // console.log('vars', vars);
    const ops = new Map<string, Exp>(parts[1].split('\n')
        .map(l => l.split(' -> '))
        .map(p => {
            const ops = p[0].split(' ');
            return [p[1], { A: ops[0], OP: ops[1], B: ops[2], R: p[1], V: undefined }]
        }));
    // console.log('ops', ops);
    return findSwaps(vars, ops);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}