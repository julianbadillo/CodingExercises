

function sumMarked(matrix, mark) {
    let rows = matrix.length;
    let cols = matrix[0].length;
    let sum = 0;
    for(let i = 0; i < rows; i++) {
        for(let j = 0; j < cols; j++) {
            if (mark[i][j]) sum += matrix[i][j]
        }
    }
    return sum;
}

function markDiagonal(mark, i, j, di, dj, length){
    for (let k = 0; k < length; k++) {
        mark[i + k * di][j + k * dj] = true;
    }
}

function sumRectangle(matrix, a, b, i, j) {
    let rows = matrix.length;
    let cols = matrix[0].length;
    
    let mark = new Array(rows);    
    for (let k = 0; k < rows; k++) {
        mark[k] = new Array(cols);
    }   
    // validate not out of bounds
    console.log('one')
    if(i + a + b - 1 > rows) return undefined;
    console.log('two')
    if(j - a + 1 < 0) return undefined;
    console.log('three')
    if(j + b > cols) return undefined;
    console.log('four')
    
    // mark all diagonals and semi-diagonals
    for(let k = 0; k < a; k++) {
        markDiagonal(mark, i + k, j - k, 1, 1, b);
        // semi-diagonal
        if (k < a - 1) {
            markDiagonal(mark, i + k + 1, j - k, 1, 1, b - 1);
        }
    }
    return sumMarked(matrix, mark);
}


export function rotatedRectSum(matrix, a, b) {
    
    let rows = matrix.length;
    let cols = matrix[0].length;
    let maxSum = undefined;
    for(let i = 0; i < rows; i++) {
        for(let j = 0; j < cols; j++) {
            let sum1 = sumRectangle(matrix, a, b, i, j);
            console.log(`[${i} ${j}] = ${sum1}`)
            if(sum1 > maxSum || maxSum == undefined) {
                maxSum = sum1;
            }
            if (a != b) {
                let sum2 = sumRectangle(matrix, b, a, i, j);
                console.log(`[${i} ${j}] = ${sum2}`)
                if(sum2 > maxSum || maxSum == undefined) {
                    maxSum = sum2;
                }
            }
        }
    }
    
    return maxSum;
}
