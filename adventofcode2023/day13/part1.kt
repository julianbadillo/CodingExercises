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
        if ((0..<R).all{ r -> horizontalReflection(map, c, r) }) {
            col = c + 1
            break
        }
    }
    for(r in 0..<R-1) {
        if ((0..<C).all{ c -> verticalReflection(map, r, c) }) {
            row = r + 1
            break
        }
    }
    println("col = $col, row = $row")
    return col + 100 * row
}

fun horizontalReflection(map: List<String>, c: Int, r: Int): Boolean {
    val C = map.first().length
    for (i in 0..Math.min(c, C - c - 2)) {
        if (map[r][c - i] != map[r][c + i + 1]) return false
    }
    return true
}

fun verticalReflection(map: List<String>, r: Int, c: Int): Boolean {
    val R = map.size
    for (i in 0..Math.min(r, R - r - 2)) {
        if (map[r - i][c] != map[r + i + 1][c]) return false
    }
    return true
}
