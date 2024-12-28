import { Computer } from "./day17-part1";


export class HardCodedComputer {
    // Register A: 2
    // Register B: 0
    // Register C: 0
    // Program: 2,4,1,2,7,5,0,3,1,7,4,1,5,5,3,0
    a: bigint = 0n;
    b: number = 0;
    c: number = 0;
    prog: number[] = [];
    i: number = 0;
    out: number[] = [];
    constructor(a: bigint) {
        this.a = a;
        this.b = 0;
        this.c = 0;
        this.prog = [2, 4, 1, 2, 7, 5, 0, 3, 1, 7, 4, 1, 5, 5, 3, 0]
    //               2, 4, 1, 2, 7, 5, 3, 3, 0, 7, 4, 1, 5, 5, 3, 0
    }
    // 2,4
    // b = a % 8 (last 3 bits)
    // 1,2
    // b = b ^ 010xb
    // 7,5
    // c = a >> b (shift up to 7 bits)
    // 0,3
    // a = a >> 3
    // 1,7
    // b = b ^ 111xb
    // 4,1
    // b = b ^ c
    // 5,5
    // out b % 8 (last 3 bits)
    // 3,0
    // if a!= 0, jumpto 0 (the beginning)
    // b = b ^ c ^ 111xb
    // => x c ^ b === 


    cycle(): boolean {
        // console.log(this.a);
        if (this.a === 0n) return false;
        const x = Number(this.a & 7n);
        const shift = x ^ 2; // flip middle bit
        const y = (Number(this.a & BigInt("0b11111111111")) >> shift) & 7;
        const z = (x ^ y ^ 5) & 7;
        this.out.push(z) /// (last 3 bits)
        this.a = this.a >> 3n
        return true;
    }
}

export const solve = (): string => {
    let starta = 346n;
    starta <<= 3n;
    starta |= 402n;
    starta <<= 3n;
    starta |= 144n;
    starta <<= 3n;
    starta |= 129n;
    
    starta <<= 3n;
    
    starta <<= 3n;
    
    starta <<= 3n;
    
    starta <<= 3n;
    starta |= 318n;
    // starta |= 2546n;
    starta <<= 3n;
    starta |= 434n;
    starta <<= 3n;
    starta |= 339n;
    starta <<= 3n;
    starta |= 158n;
    starta <<= 3n;
    starta |= 240n;
    starta <<= 6n;
    starta |= 15n;
    console.log(starta);
    const comp = new HardCodedComputer(starta);//(2024);
    // console.log(c);
    while (comp.cycle());
    console.log(comp.out.join(','));
    const test = [5, 0,3,1];
    for (let i = 0; i < 100000; i++) {
        // const a = starta | i;
        const a = i;
        const c2 = new HardCodedComputer(BigInt(a));
        while (c2.cycle());
        // discard longer output
        if (c2.out.length > test.length) continue;
        if (test.map((x, i) => x === c2.out[i]).reduce((a, b) => a && b)) {
            console.log(`${a} [${a.toString(2)}] => ${c2.out.join(',')}`)
        }
    }
    return "";
}

if (require.main === module) {
    const r = solve();
    process.stdout.write(`r = ${r}\n`);

}

// 346 [101011010] => 5,3,0
// 378 [101111010] => 5,3,0

// 402 [110010010] => 5,5,3

// 144 [10010000] => 1,5,5
// 149 [10010101] => 1,5,5

// 129 [10000001] => 4,1,5

// 1032 [10000001000] => 7,4,1,5
// 28 [11100] => 1,7
// 318 [100111110] => 0,3,1
// (378 << 9) | (402 << 6) | (144 << 3) | 129

// 426 [110101010] => 5,0,3
// 434 [110110010] => 5,0,3

// 2546 [100111110010] => 5,0,3,1
// 2551 [100111110111] => 5,0,3,1


// 339 [101010011] => 7,5,0

// 158 [10011110] => 2,7,5

// 240 [11110000] => 1,2,7
// 245 [11110101] => 1,2,7
// 253 [11111101] => 1,2,7

// 15 [1111] => 2,4