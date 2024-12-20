export class Computer {
    a: number = 0;
    b: number = 0;
    c: number = 0;
    prog: number[] = [];
    i: number = 0;
    out: number[] = [];

    cycle(): boolean {
        if (this.i >= this.prog.length - 1) return false;
        const op = this.prog[this.i];
        const lit_operand = this.prog[this.i + 1];
        let comb_operand = lit_operand;
        // combo operand
        if (comb_operand === 4) comb_operand = this.a;
        else if (comb_operand === 5) comb_operand = this.b;
        else if (comb_operand === 6) comb_operand = this.c;

        // adv
        if (op === 0) {
            // division
            this.a = this.a >> comb_operand;
        } else if (op === 6) {
            this.b = this.a >> comb_operand;
        } else if (op === 7) {
            this.c = this.a >> comb_operand;
        } else if (op === 1) {
            this.b = this.b ^ lit_operand;
        } else if (op === 2) {
            this.b = comb_operand % 8;
        } else if (op === 3 && this.a !== 0) {
            this.i = lit_operand;
            return true;
        } else if (op === 4) {
            this.b = this.b ^ this.c;
        } else if (op === 5) {
            this.out.push(comb_operand % 8);
        }
        this.i += 2;
        return true;
    }
    toString = (): string => `a=${this.a} b=${this.b} c=${this.c}\ni=${this.i}\nprog=${this.prog}\n${this.out}`;
}

export const solve = (data: string): string => {
    const c = new Computer();
    const parts = data.split('\n');
    c.a = Number.parseInt(parts[0].split(': ')[1]);
    c.b = Number.parseInt(parts[1].split(': ')[1]);
    c.c = Number.parseInt(parts[2].split(': ')[1]);

    c.prog = parts[4].split(': ')[1].split(',').map(x => Number.parseInt(x));
    // console.log(c);
    while (c.cycle());
    return c.out.join(',');
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}