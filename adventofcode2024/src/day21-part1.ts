
export const doorPad = [
    `789`,
    `456`,
    `123`,
    ` 0A`,
];



export const robotPad = [
    ` ^A`,
    `<v>`,
];

export type P = {
    r: number,
    c: number,
}

export const doorPadPos = new Map<string, P>();

doorPad.forEach((line, r) => line
    .split('')
    .forEach((key, c) => {
        doorPadPos.set(key, { r: r, c: c });
    })
);

export const robotPadPos = new Map<string, P>();
robotPad.forEach((line, r) => line
    .split('')
    .forEach((key, c) => {
        robotPadPos.set(key, { r: r, c: c });
    })
);
export class RobotChain {

    p1: P = { r: 3, c: 2 }; // pointing to A in the doorKeyPad
    p2: P = { r: 0, c: 2 }; // pointing to A in robotKeyPad
    p3: P = { r: 0, c: 2 }; // pointing to A in robotKeyPad
    out: string[] = [];

    shortestSeq1(key: string): string {
        let seq2 = '';
        for (const key2 of key.split('')) {
            // get to the given key
            const next = doorPadPos.get(key2) as P;
            // horizontal move
            while (next.c != this.p1.c) {
                seq2 += this.p1.c < next.c ? '>' : '<';
                this.p1.c += this.p1.c < next.c ? 1 : -1;
            }
            // vertical move
            while (next.r != this.p1.r) {
                seq2 += this.p1.r < next.r ? 'v' : '^';
                this.p1.r += this.p1.r < next.r ? 1 : -1;
            }
            // press A at the end of each button
            seq2 += 'A';
        }
        return seq2;
    }

    shortestSeq2(key: string): string {
        const seq = this.shortestSeq1(key);
        let seq2 = '';
        for (const key2 of seq.split('')) {
            // get to the given key
            const next = robotPadPos.get(key2) as P;
            // horizontal move
            while (next.c != this.p2.c) {
                seq2 += this.p2.c < next.c ? '>' : '<';
                this.p2.c += this.p2.c < next.c ? 1 : -1;
            }
            // vertical move
            while (next.r != this.p2.r) {
                seq2 += this.p2.r < next.r ? 'v' : '^';
                this.p2.r += this.p2.r < next.r ? 1 : -1;
            }
            // press A at the end of each button
            seq2 += 'A';
        }
        return seq2;
    }

    shortestSeq3(key: string): string {
        const seq = this.shortestSeq2(key);
        let seq2 = '';
        for (const key2 of seq.split('')) {
            // get to the given key
            const next = robotPadPos.get(key2) as P;
            // horizontal move
            while (next.c != this.p3.c) {
                seq2 += this.p3.c < next.c ? '>' : '<';
                this.p3.c += this.p3.c < next.c ? 1 : -1;
            }
            // vertical move
            while (next.r != this.p3.r) {
                seq2 += this.p3.r < next.r ? 'v' : '^';
                this.p3.r += this.p3.r < next.r ? 1 : -1;
            }
            // press A at the end of each button
            seq2 += 'A';
        }
        return seq2;
    }

    press1(key: string) {
        if (key == '<') this.p1.c--;
        if (key == '>') this.p1.c++;
        if (key == '^') this.p1.r--;
        if (key == 'v') this.p1.r++;
        if (key == 'A') this.out.push(doorPad[this.p1.r][this.p1.c]);
    }

    press2(key: string) {
        if (key == '<') this.p2.c--;
        if (key == '>') this.p2.c++;
        if (key == '^') this.p2.r--;
        if (key == 'v') this.p2.r++;
        if (key == 'A') this.press1(robotPad[this.p2.r][this.p2.c]);
    }

    press3(seq: string) {
        for (const key of seq.split('')) {
            if (key == '<') this.p3.c--;
            if (key == '>') this.p3.c++;
            if (key == '^') this.p3.r--;
            if (key == 'v') this.p3.r++;
            if (key == 'A') this.press2(robotPad[this.p3.r][this.p3.c]);
        }
    }

}