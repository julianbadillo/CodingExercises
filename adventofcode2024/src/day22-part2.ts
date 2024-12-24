import { nextSecret } from "./day22-part1";

export const getChangeSeq = (secret: number, n: number): number[][] => {
    const seq: number[][] = [];
    let price = secret % 10;
    let newPrice = price;
    for (let i = 0; i < n; i++) {
        secret = nextSecret(secret);
        newPrice = secret % 10;
        seq.push([newPrice, newPrice - price]);
        price = newPrice;
    }
    return seq;
}