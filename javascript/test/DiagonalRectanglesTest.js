import { assert } from 'chai';
import { rotatedRectSum } from '../src/DiagonalRectangles.js';


describe('Diagonal Rectangles Sum', () =>{
    it('2x2 square vs 1x1 diagonal', () => {
        let m = [   [1,2],
                    [3,4]]
        let a = 1, b = 1;

        let r = rotatedRectSum(m, a, b);
        //console.log(r);
        assert.equal(r, 4);
    });
    it('3x3 square vs 2x2 diagonal', () => {
        let m = [   [1,2,3],
                    [4,5,6],
                    [7,8,9]]
        let a = 2, b = 2;
        let r = rotatedRectSum(m, a, b);
        assert.equal(r, 25);

    });
    
    it('3x3 square vs 3x1 diagonal', () => {
        let m = [   [1,2,3],
                    [4,5,6],
                    [7,8,9]]
        let a = 3, b = 1;
        let r = rotatedRectSum(m, a, b);
        assert.equal(r, 15);
    });

    it('4x3 square vs 2x2 diagonal', () => {
        let m = [   [1, 2,  3],
                    [4, 5,  6],
                    [7, 8,  9],
                    [10,11, 12]]
        let a = 2, b = 2;
        let r = rotatedRectSum(m, a, b);
        assert.equal(r, 40);
    });

    it('4x4 square vs 2x3 diagonal', () => {
        let m = [   [ 1, 2, 3, 4],
                    [-1,-2,-3,-4],
                    [ 1, 2, 3, 4],
                    [-1,-2,-3,-4],]
        let a = 2, b = 3;
        let r = rotatedRectSum(m, a, b);
        assert.equal(r, 2);
    });

});
