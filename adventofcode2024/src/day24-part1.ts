
export type Exp = {
    A: string,
    B: string,
    OP: string,
    R: string,
    V: boolean | undefined,
}

export const evalExp = (zv: string, vars: Map<string, boolean>, ops: Map<string, Exp>): boolean => {
    if (vars.has(zv)) return vars.get(zv) as boolean;
    const exp = ops.get(zv) as Exp;
    if (exp.V !== undefined) return exp.V;
    const a = evalExp(exp.A, vars, ops) as boolean;
    const b = evalExp(exp.B, vars, ops) as boolean;
    if (exp.OP === 'AND')
        exp.V = a && b;
    else if (exp.OP === 'OR')
        exp.V = a || b;
    else if (exp.OP === 'XOR')
        exp.V = a !== b;
    return exp.V as boolean;
}


export const evalZs = (vars: Map<string, boolean>, ops: Map<string, Exp>): bigint => {
    // only the ones with z
    const zvs = [...ops.keys()].filter(v => v[0] === 'z').sort().reverse();
    let n: bigint = 0n;
    zvs.forEach(zv => {
        n <<= 1n;
        if (evalExp(zv, vars, ops)) n++;
        // console.log(zv, ' = ', ops.get(zv)?.V, '  n = ', n);
    });
    return n;
}

export const solve = (data: string): bigint => {
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
    return evalZs(vars, ops);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}