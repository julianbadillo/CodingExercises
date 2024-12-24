

const M = 16777216 - 1;
// const M_AND = (1 << 24)-1;

export const step1 = (secret: number): number => {
    // multiply the secret number by 64. 
    // Then, mix this result into the secret number.
    // prune the secret number.
    return ((secret << 6) ^ secret) & M;
}

export const step2 = (secret: number): number => {
    // Calculate the result of dividing the secret number by 32. 
    // Round the result down to the nearest integer. 
    // Then, mix this result into the secret number. 
    // Finally, prune the secret number.
    return ((secret >> 5) ^ secret) & M;
}

export const step3 = (secret: number): number => {
    // Calculate the result of multiplying the secret number by 2048.
    // Then, mix this result into the secret number. 
    // Finally, prune the secret number.
    return ((secret << 11) ^ secret) & M;
}

export const nextSecret = (secret: number): number => step3(step2(step1(secret)));

export const getIthSecret = (secret: number, i: number): number => {
    for (let j = 0; j < i; j++) secret = nextSecret(secret);
    return secret;
}

export const solve = (data: string) => {
    return data.split('\n')
        .map(x => Number.parseInt(x))
        .map(secret => getIthSecret(secret, 2000))
        .reduce((a, b) => a + b);
}

if (require.main === module) {
    process.stdin.on('data', (data) => {
        const r = solve(data.toString());
        process.stdout.write(`r = ${r}\n`);
    });
}