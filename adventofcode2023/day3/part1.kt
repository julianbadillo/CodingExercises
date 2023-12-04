package day2
import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val engine = reader.readLines()
    val R = engine.size
    val C = engine[0].length
    var total = 0

    for (r in 0..<R) {
        for (c in 0..<C) {
            val x = engine[r][c]
            if (x == '.') {
                continue
            } else if ('0' <= x && x <= '9') {
                val num = adjacentToSymbol(engine, r, c)
                if (num != null) {
                    //println(num)
                    total += num
                }
            }
        }
    }
    println(total)
}

/* Return the whole number value if adjacent to a symbol, or null if not. */
fun adjacentToSymbol(engine: List<String>, r: Int, c: Int): Int? {
    val R = engine.size
    val C = engine[0].length
    // if the one on the left is a digit, we already processed this number, so skip
    if (c > 0 && '0' <= engine[r][c-1] && engine[r][c-1] <= '9') {
        return null
    }
    // length of the number
    var l = 1;
    while (c + l < C && '0' <= engine[r][c+l] && engine[r][c+l] <= '9') { l++ }
    
    // surrounding points
    data class P(val r: Int, val c: Int)
    var points = mutableListOf<P>(P(r-1, c-1), P(r, c-1), P(r+1, c-1))
    for (i in 0..<l) {
        points.add(P(r-1, c + i))
        points.add(P(r+1, c + i))
    }
    points.add(P(r-1, c + l))
    points.add(P(r, c + l))
    points.add(P(r+1, c + l))

    if (points.filter { 0 <= it.r && it.r < R && 0 <= it.c && it.c < C }
        .any { engine[it.r][it.c] != '.' && !('0' <= engine[it.r][it.c] && engine[it.r][it.c] <= '9') }) {
        return engine[r].substring(c, c+l).toInt()
    }
    return null
}
