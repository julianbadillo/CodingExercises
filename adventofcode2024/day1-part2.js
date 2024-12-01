"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var process = require("node:process");
var distance = function (a, b, i) {
    return a[i] < b[i] ? (b[i] - a[i]) : (a[i] - b[i]);
};
process.stdin.on('data', function (data) {
    var lines = data.toString().split('\n');
    var l1 = new Array(lines.length);
    var l2 = new Array(lines.length);
    lines.forEach(function (line, i) {
        var parts = line.split('   ');
        l1[i] = Number.parseInt(parts[0]);
        l2[i] = Number.parseInt(parts[1]);
    });
    var s = l1.map(function (x1) { return x1 * l2.filter(function (x2) { return x1 == x2; }).length; }).reduce(function (a, b) { return a + b; });
    process.stdout.write("s = ".concat(s));
    process.stdout.write('\n');
});
