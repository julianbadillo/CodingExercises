
import assert from "assert";
import { getIthSecret, nextSecret, solve, step1, step2, step3 } from "../src/day22-part1";
import { getChangeSeq } from "../src/day22-part2";

describe('day22', () => {
    it('steps', () => {
        let secret = 123;
        secret = step1(secret);
        secret = step2(secret);
        secret = step3(secret);
        assert.equal(secret, 15887950);
    });

    it('nextSecret', () => {
        let secret = 123;
        secret = nextSecret(secret);
        assert.equal(secret, 15887950);
        secret = nextSecret(secret);
        assert.equal(secret, 16495136);
        secret = nextSecret(secret);
        assert.equal(secret, 527345);
        secret = nextSecret(secret);
        assert.equal(secret, 704524);
        secret = nextSecret(secret);
        assert.equal(secret, 1553684);
        secret = nextSecret(secret);
        assert.equal(secret, 12683156);
        secret = nextSecret(secret);
        assert.equal(secret, 11100544);
    });

    it('getIthSecret', () => {
        let secret = 1;
        secret = getIthSecret(secret, 2000);
        assert.equal(secret, 8685429);
        secret = 10;
        secret = getIthSecret(secret, 2000);
        assert.equal(secret, 4700978);
        secret = 100;
        secret = getIthSecret(secret, 2000);
        assert.equal(secret, 15273692);
        secret = 2024;
        secret = getIthSecret(secret, 2000);
        assert.equal(secret, 8667524);
    });

    const s = `1
10
100
2024`;

    it('solve-p1', () => {
        assert.equal(solve(s), 37327623);
    });

    it('getChangeSeq', () => {
        const seq = getChangeSeq(123, 9);
        assert.equal(seq.length, 9);
        console.log(seq)
        assert.deepStrictEqual(seq, [[0, -3], [6, 6], [5, -1], [4, -1], [4, 0], [6, 2], [4, -2], [4, 0], [2, -2]])
    });
})