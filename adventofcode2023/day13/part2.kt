import java.io.File
import java.io.BufferedReader


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val lines = reader.readLines()
    val maps = mutableListOf<MutableList<String>>(mutableListOf<String>())
    lines.forEach {
        if (it == "") {
            maps.add(mutableListOf<String>())
        } else {
            maps.last().add(it)
        }
    }
    val total = maps.map{ findReflection(it) }.sum()
    println(total)
}

fun findReflection(map: List<String>): Int {
    // rows and cols
    val R = map.size
    val C = map.first().length
    // horizontal reflection
    var row = 0
    var col = 0
    for(c in 0..<C-1) {
        val d = (0..<R).map{ r -> horizontalReflection(map, c, r) }.sum()
        // println("c = $c, d = $d")
        if (d == 1) {
            col = c + 1
            break
        }
    }
    for(r in 0..<R-1) {
        val d = (0..<C).map{ c -> verticalReflection(map, r, c) }.sum()
        // println("r = $r, d = $d")
        if (d == 1) {
            row = r + 1
            break
        }
    }
    println("col = $col, row = $row")
    return col + 100 * row
}

fun horizontalReflection(map: List<String>, c: Int, r: Int): Int {
    val C = map.first().length
    var d = 0
    for (i in 0..Math.min(c, C - c - 2)) {
        if (map[r][c - i] != map[r][c + i + 1]) d++
    }
    return d
}

fun verticalReflection(map: List<String>, r: Int, c: Int): Int {
    val R = map.size
    var d = 0
    for (i in 0..Math.min(r, R - r - 2)) {
        if (map[r - i][c] != map[r + i + 1][c]) d++
    }
    return d
}
