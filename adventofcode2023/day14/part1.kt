import java.io.File
import java.io.BufferedReader

class Rock(val fixed: Boolean = false) {
    override fun toString() = if(fixed) "#" else "O"
}


fun main(args: Array<String>) {
    val reader = File(args[0]).bufferedReader()
    val map = reader.readLines()
    val total = rockLoad(map)
    println(total)
}

fun rockLoad(map: List<String>): Int {

    val R = map.size
    val C = map.first().length
    val rocks = Array(R) { arrayOfNulls<Rock?>(C) }
    for (r in 0..<R) {
        for (c in 0..<C) {
            rocks[r][c] = if (map[r][c] == 'O') {
                Rock(fixed = false)
            } else if (map[r][c] == '#') {
                Rock(fixed = true)
            } else  {
                null
            }
        }
    }
    tilt(rocks, N)
    printRocks(rocks)
    return northLoad(rocks)
}
class P(val dr: Int, val dc: Int)

val N = P(-1, 0)
val W = P(0, -1)
val S = P(1, 0)
val E = P(0, 1)

fun tilt(rocks: Array<Array<Rock?>>, dir: P) {
    val R = rocks.size
    val C = rocks[0].size
    // cycles depend on direction
    for (r in 0..<R) {
        for (c in 0..<C) {
            if (!(rocks[r][c]?.fixed?:true)) {
                // move the rock all the way to the top
                var i = 0
                // within bounds and doesn't find another obstacke
                var r2 = r
                var c2 = c
                while ( 0 <= r2 + dir.dr && r2 + dir.dr < R 
                        && 0 <= c2 + dir.dc && c2 + dir.dc < C 
                        && rocks[r2 + dir.dr][c2 + dir.dc] == null) {
                            r2 += dir.dr
                            c2 += dir.dc
                        }
                if (r2 != r || c2 != c) {
                    rocks[r2][c2] = rocks[r][c]
                    rocks[r][c] = null
                }
            }

        }
    }
}

fun northLoad(rocks: Array<Array<Rock?>>): Int {
    val R = rocks.size
    val C = rocks[0].size
    var total = 0
    for (r in 0..<R) {
        for (c in 0..<C) {
            total += if (!(rocks[r][c]?.fixed?:true)) R - r else 0
        }
    }
    return total
}
fun printRocks(rocks: Array<Array<Rock?>>) {
    for (l in rocks) {
        for (r in l) {
            print(r ?: ".")
        }
        println()
    }
}
