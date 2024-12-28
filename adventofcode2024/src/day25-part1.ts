
const H = 5;

export const parseLockOrKey = (lines: string[]): number[] => {
    const h: number[] = [];
    for (let i = 0; i < lines[0].length; i++) {
        h.push(lines.filter(l => l[i] === '#').length - 1);
    }
    return h;
}

export const fit = (lock: number[], key: number[]): boolean => {
    return lock.map((l, i) => l + key[i]).map(r => r <= H).reduce((a, b) => a && b);
}

export const solve = (data: string): number => {
    const parts = data.split('\n\n').map(p => p.split('\n'));
    const locks: number[][] = parts.filter(p => p[0] === '#####').map(p => parseLockOrKey(p));
    const keys: number[][] = parts.filter(p => p[0] !== '#####').map(p => parseLockOrKey(p));
    let n = 0;
    locks.forEach(lock => {
        keys.forEach(key => {
            if (fit(lock, key)) n++;
        });
    })
    return n;
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}