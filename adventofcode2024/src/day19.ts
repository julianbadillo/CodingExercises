
export const fitsPattern = (towels: Set<string>, pat: string): boolean => {
    if (pat.length == 0) return true;
    for (const towel of towels) {
        if (pat.startsWith(towel) && fitsPattern(towels, pat.substring(towel.length))) return true;
    }
    return false;
}

export const countPatterns = (towels: Set<string>, pat: string, countMap: Map<string, number>): number => {
    if (pat.length == 0) return 1;
    let ways = 0;
    for (const towel of towels) {
        if (pat.startsWith(towel)) {
            const pat2 = pat.substring(towel.length);
            if (countMap.has(pat2)) {
                ways += countMap.get(pat2) as number;
            } else {
                const ways2 = countPatterns(towels, pat2, countMap);
                countMap.set(pat2, ways2);
                ways += ways2;
            }
        }
    }
    return ways;
}

export const solve = (data: string): number[] => {
    const parts = data.split('\n\n');
    const towels = new Set<string>(parts[0].split(', '));
    const patterns = parts[1].split('\n');
    const countMap = new Map<string, number>(); // cache
    const counts = patterns.map(pat => countPatterns(towels, pat, countMap));

    return [counts.filter(c => c > 0).length, counts.reduce((a, b) => a + b)];
    ;
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}