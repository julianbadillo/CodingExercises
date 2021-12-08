
export function readFileToList(fileName) {
    return [];
}


export function nameScore(name){
    name = name.toUpperCase();
    let total = 0;
    for (let c in name) {
        total += c - 'A' + 1;
    }
    return total;
}

export function totalNameScore(nameList) {
    nameList.sort();
    let total = nameList.map((name, i) => (i+1) * nameScore(name))
                    .reduce((x, y) => x + y);
    return total;
}