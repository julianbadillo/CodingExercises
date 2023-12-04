package day2
import java.io.File
import java.io.BufferedReader


data class P(val r: Int, val c: Int)
class Gear(val symbol: Char, val parts: MutableList<Int> = mutableListOf()) {
    override fun toString() = "'$symbol' $parts\n"
}


fun main(args: Array<String>) {
    val reader: BufferedReader = File(args[0]).bufferedReader()
    val engine = reader.readLines()
    val gears = parseGears(engine)
    val total = gears.values.filter { it.parts.size == 2 } .map { it.parts.first() * it.parts.last() } .sum()
    println(total)
}

fun parseGears(engine: List<String>): MutableMap<P, Gear>{
    val R = engine.size
    val C = engine[0].length
    val gears = mutableMapOf<P, Gear>()
    for (r in 0..<R) {
        for (c in 0..<C) {
            val x = engine[r][c]
            if (x == '.') {
                continue
            } else if ('0' <= x && x <= '9') {
                getAdjacentGears(engine, r, c, gears)
            }
        }
    }
    //println(gears)
    return gears
}

/* Return the whole number value if adjacent to a symbol, or null if not. */
fun getAdjacentGears(engine: List<String>, r: Int, c: Int, gears: MutableMap<P, Gear>) {
    val R = engine.size
    val C = engine[0].length
    // if the one on the left is a digit, we already processed this number, so skip
    if (c > 0 && '0' <= engine[r][c-1] && engine[r][c-1] <= '9') {
        return
    }
    // length of the number
    var l = 1;
    while (c + l < C && '0' <= engine[r][c+l] && engine[r][c+l] <= '9') { l++ }
    
    // surrounding points
    var points = mutableListOf<P>(P(r-1, c-1), P(r, c-1), P(r+1, c-1))
    for (i in 0..<l) {
        points.add(P(r-1, c + i))
        points.add(P(r+1, c + i))
    }
    points.add(P(r-1, c + l))
    points.add(P(r, c + l))
    points.add(P(r+1, c + l))

    val num = engine[r].substring(c, c+l).toInt()
    // within bounds
    points.filter { 0 <= it.r && it.r < R && 0 <= it.c && it.c < C }
        // a symbol
        .filter { engine[it.r][it.c] != '.' && !('0' <= engine[it.r][it.c] && engine[it.r][it.c] <= '9') }
        .forEach {
            // link part number to gear
            val p = P(it.r, it.c)
            gears[p] = gears[p] ?: Gear(symbol=engine[p.r][p.c])
            gears[p]?.parts?.add(num)
        }
}
